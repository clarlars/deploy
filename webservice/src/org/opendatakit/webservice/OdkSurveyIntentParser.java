package org.opendatakit.webservice;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.opendatakit.database.DatabaseConstants;
import org.opendatakit.database.LocalKeyValueStoreConstants;
import org.opendatakit.database.data.TableMetaDataEntries;
import org.opendatakit.database.service.DbHandle;
import org.opendatakit.database.utilities.CursorUtils;
import org.opendatakit.provider.FormsColumns;
import org.opendatakit.provider.FormsProviderAPI;
import org.opendatakit.provider.FormsProviderUtils;
import org.opendatakit.services.database.AndroidConnectFactory;
import org.opendatakit.services.database.OdkConnectionFactorySingleton;
import org.opendatakit.services.database.OdkConnectionInterface;
import org.opendatakit.services.database.utlities.ODKDatabaseImplUtils;
import org.opendatakit.utilities.ODKFileUtils;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.TextUtils;

public class OdkSurveyIntentParser {
  private final String appNameUrlPrefix;
  
  public OdkSurveyIntentParser(String appName, String appNameUrlPrefix) {
    this.appNameUrlPrefix = appNameUrlPrefix;
  }
  
  class PatchedFilter {
    String appName;
    String tableId;
    String formId;
    String numericFormId;
    boolean isNumericFormId;
    boolean requiresBlankFormIdPatch;
    String whereId;
    String[] whereIdArgs;
  }

  /**
   * Parse the URI for the form. This is either of the form:
   *
   *  /appName/_ID/
   *       use the _ID column to retrieve the referenced form
   *       this is a numeric row id.
   *
   *  or
   *
   *  /appName/tableId/
   *       this will return all forms for a given tableId.
   *
   *  or
   *
   *  /appName/tableId//
   *       this requires a call to updatePatchedFilter(...) to
   *       retrieve and use the default formId for this tableId.
   *       If there is no configured default formId, use the
   *       tableId as the formId.
   *
   *  or
   *
   *  /appName/tableId/formId/
   *
   * @param uri
   * @param segments
   * @param where
   * @param whereArgs
   * @return
   */
  private PatchedFilter extractUriFeatures( Uri uri, List<String> segments, String where, String[] whereArgs ) {

    PatchedFilter pf = new PatchedFilter();

    if (segments.size() < 1 || segments.size() > 3) {
      throw new IllegalArgumentException("Unknown URI (incorrect number of segments!) " + uri);
    }

    pf.appName = segments.get(0);
    ODKFileUtils.verifyExternalStorageAvailability();
    ODKFileUtils.assertDirectoryStructure(pf.appName);
    AndroidConnectFactory.configure();

    pf.tableId = null;
    pf.formId = null;
    pf.numericFormId = null;
    // assume that we are not dealing with _ID values...
    pf.tableId = ((segments.size() >= 2) ? segments.get(1) : null);
    pf.isNumericFormId = StringUtils.isNumeric(pf.tableId);
    if ( pf.isNumericFormId ) {
      pf.numericFormId = pf.tableId;
      pf.tableId = null;
      if ( segments.size() == 3 ) {
        // user is trying to mix a /_ID uri with a /tableId/formId uri.
        throw new IllegalArgumentException("Unknown URI ( _ID cannot be combined with other segments!) " + uri);
      }
    }
    // and handle formId
    pf.formId = ((segments.size() == 3) ? segments.get(2) : null);

    // Modify the where clause to account for the presence of any additional segments
    if (segments.size() == 1) {
      // no segments -- directly use whatever filter the user specified
      pf.whereId = where;
      pf.whereIdArgs = whereArgs;
    } else if ( segments.size() == 2) {
      // either a tableId or a numericFormId is specified.
      // combine this filter with the where clause the user supplied.
      if (TextUtils.isEmpty(where)) {
        pf.whereId = (pf.isNumericFormId ? BaseColumns._ID : FormsColumns.TABLE_ID) + "=?";
        pf.whereIdArgs = new String[1];
        pf.whereIdArgs[0] = (pf.isNumericFormId ? pf.numericFormId : pf.tableId);
      } else {
        pf.whereId = (pf.isNumericFormId ? BaseColumns._ID : FormsColumns.TABLE_ID) + "=? AND (" + where
            + ")";
        pf.whereIdArgs = new String[whereArgs.length + 1];
        pf.whereIdArgs[0] =  (pf.isNumericFormId ? pf.numericFormId : pf.tableId);
        System.arraycopy(whereArgs, 0, pf.whereIdArgs, 1, whereArgs.length);
      }
    } else {
      // we have both a tableId and a formId.
      pf.requiresBlankFormIdPatch = ( pf.formId == null || pf.formId.length() == 0 );

      // combine with the filter clause the user supplied.
      if (TextUtils.isEmpty(where)) {
        pf.whereId = FormsColumns.TABLE_ID + "=? AND " + FormsColumns.FORM_ID + "=?";
        pf.whereIdArgs = new String[2];
        pf.whereIdArgs[0] = pf.tableId;
        pf.whereIdArgs[1] = pf.formId;
      } else {
        pf.whereId =  FormsColumns.TABLE_ID + "=? AND " + FormsColumns.FORM_ID + "=? AND (" + where
            + ")";
        pf.whereIdArgs = new String[whereArgs.length + 2];
        pf.whereIdArgs[0] = pf.tableId;
        pf.whereIdArgs[1] = pf.formId;
        System.arraycopy(whereArgs, 0, pf.whereIdArgs, 2, whereArgs.length);
      }
    }

    return pf;
  }

  /**
   * If the URL did not include a formId, update the patched filter to use the
   * default formId for this tableId. If no default formId is specified in the
   * table's properties, use the tableId form as the default form.
   *
   * @param db
   * @param pf
   */
  private void updatePatchedFilter(OdkConnectionInterface db, PatchedFilter pf) {
    if ( pf.requiresBlankFormIdPatch ) {
      TableMetaDataEntries values = ODKDatabaseImplUtils.get()
          .getTableMetadata(db, pf.tableId,
              LocalKeyValueStoreConstants.DefaultSurveyForm.PARTITION,
              LocalKeyValueStoreConstants.DefaultSurveyForm.ASPECT,
              LocalKeyValueStoreConstants.DefaultSurveyForm.KEY_FORM_ID);
      if (values.getEntries() == null || values.getEntries().size() != 1) {
        // use the tableId as the default formId
        pf.formId = pf.tableId;
      } else {
        pf.formId = values.getEntries().get(0).value;
      }
      pf.whereIdArgs[1] = pf.formId;
    }
  }

  private final String retrieveFormPath(String appName, Uri formUri) {
    if (formUri == null) {
      return null;
    }

    List<String> segments = formUri.getPathSegments();

    PatchedFilter pf = extractUriFeatures( formUri, segments, null, null );

    boolean success = false;
    DbHandle dbHandleName = OdkConnectionFactorySingleton.getOdkConnectionFactoryInterface().generateInternalUseDbHandle();
    OdkConnectionInterface db = null;
    Cursor c = null;
    try {


      // Get the database and run the query
      // +1 referenceCount if db is returned (non-null)
      db = OdkConnectionFactorySingleton.getOdkConnectionFactoryInterface().getConnection(pf.appName, dbHandleName);
      updatePatchedFilter(db, pf);

      c = db.query(DatabaseConstants.FORMS_TABLE_NAME, null, pf.whereId, pf.whereIdArgs,
          null, null, null, null);

      if (c == null) {
        return null;
      }
      success = true;

      if (c != null && c.getCount() == 1) {
        int idxTableId = c.getColumnIndex(FormsColumns.TABLE_ID);
        int idxFormId = c.getColumnIndex(FormsColumns.FORM_ID);

        c.moveToFirst();

        String tableId = CursorUtils.getIndexAsString(c, idxTableId);
        String formId = CursorUtils.getIndexAsString(c, idxFormId);
        
        File formDirectory = new File( ODKFileUtils.getFormFolder(appName, tableId, formId) );
        // File formDefJsonFile = new File(formDirectory, ODKFileUtils.FORMDEF_JSON_FILENAME);

        return "../" + ODKFileUtils.asUriFragment(appName, formDirectory);
      }
    } finally {
      if (c != null) {
        c.close();
      }
      if ( db != null ) {
        try {
          db.releaseReference();
        } finally {
          if ( !success ) {
            // this closes the connection
            // if it was successful, then the InvalidateMonitor will close the connection
            OdkConnectionFactorySingleton.getOdkConnectionFactoryInterface().removeConnection(
                pf.appName, dbHandleName);
          }
        }
      }
    }
    return null;
  }

  public Map<String,Object> getUrl(String thisAppName, String action, Map<String,Object> intent) {

    // must be at the beginning of any activity that can be called from an
    // external intent
    String data;
    if ( intent.containsKey("data") ) {
      data = (String) intent.get("data");
    } else if ( intent.containsKey("uri") ) {
      data = (String) intent.get("uri");
    } else {
      data = null;
    }
    Uri uri = (data != null) ? Uri.parse(data) : null;
    Uri formUri = null;

    if (uri != null) {
      // initialize to the URI, then we will customize further based upon the
      // savedInstanceState...
      final Uri uriFormsProvider = FormsProviderAPI.CONTENT_URI;
      if (uri.getScheme().equalsIgnoreCase(uriFormsProvider.getScheme()) && uri.getAuthority().equalsIgnoreCase(uriFormsProvider.getAuthority())) {
        List<String> segments = uri.getPathSegments();
        if (segments != null && segments.size() >= 2) {
          String appName = segments.get(0);
          if ( !appName.equals(thisAppName) ) {
            throw new IllegalArgumentException("appName does not match expected value: " + thisAppName);
          }
          String tableId = segments.get(1);
          String formId = (segments.size() > 2) ? segments.get(2) : "";
          formUri = Uri.withAppendedPath(
              Uri.withAppendedPath(Uri.withAppendedPath(FormsProviderAPI.CONTENT_URI, appName),
                  tableId), formId);
          // request specifies a specific formUri -- try to open that
          String formPath = retrieveFormPath(appName, formUri);
          if ( formPath != null) {
            FormsProviderUtils.ParsedFragment pf;
            try {
              pf = FormsProviderUtils.parseUri(uri);
            } catch ( UnsupportedEncodingException e ) {
              throw new IllegalStateException("unexpected", e);
            }
            String refId = UUID.randomUUID().toString();
            try {
                  String hashUrl = "#formPath=" +
                      FormsProviderUtils.encodeFragmentUnquotedStringValue(formPath)
                      + ((pf.instanceId == null) ? "" : "&instanceId=" +
                        FormsProviderUtils.encodeFragmentUnquotedStringValue(pf.instanceId))
                      + ((pf.screenPath == null) ? "" : "&screenPath="
                        + FormsProviderUtils.encodeFragmentUnquotedStringValue(pf.screenPath))
                      + ("&refId=" + FormsProviderUtils.encodeFragmentUnquotedStringValue(refId))
                      + ((pf.auxillaryHash == null) ? "" : "&" + pf.auxillaryHash);

                Map<String,Object> retVal = new HashMap<String,Object>();
                retVal.put("tool", "survey");
                retVal.put("url", appNameUrlPrefix + appName + "/" +
                    ODKFileUtils.asUriFragment(appName, new File(
                        ODKFileUtils.getSystemFolder(appName), "index.html")) + hashUrl);
                retVal.put("tableId", tableId);
                retVal.put("formId", formId);
                if ( pf.instanceId != null ) {
                  retVal.put("instanceId", pf.instanceId);
                }
                if ( pf.screenPath != null ) {
                  retVal.put("screenPath", pf.screenPath);
                }
                retVal.put("refId", refId);
                retVal.put("hashUrl", hashUrl);
                retVal.put("appName", appName);
                return retVal;
            } catch ( UnsupportedEncodingException e ) {
              throw new IllegalStateException("Unexpected");
            }
          }
        }
      }
    }
    return null;
  }
  
  public Map<String,Object> getFrameworkUrl(String appName) {
    // can't find it -- display framework form (list of forms)
    String hashUrl = "#formPath=..%2Fconfig%2Fassets%2Fframework%2Fforms%2Fframework%2F&instanceId=invariant%3A0";
    /*
                // we want framework...
                File frameworkFormDef = new File( ODKFileUtils.getFormFolder(appName,
                    FormsColumns.COMMON_BASE_FORM_ID, FormsColumns.COMMON_BASE_FORM_ID), "formDef.json");

                String hashUrl = "#formPath="
                  + FormsProviderUtils.encodeFragmentUnquotedStringValue(ODKFileUtils.getRelativeFormPath(appName, frameworkFormDef))
                  + ((instanceId == null) ? "" : "&instanceId=" +
                      FormsProviderUtils.encodeFragmentUnquotedStringValue(instanceId))
                  + ((getScreenPath() == null) ? "" : "&screenPath="
                      + FormsProviderUtils.encodeFragmentUnquotedStringValue(getScreenPath()))
                  + ("&refId=" + FormsProviderUtils.encodeFragmentUnquotedStringValue(refId))
                  + ((auxillaryHash == null) ? "" : "&" + auxillaryHash);
                return hashUrl;
     * 
     */
    
    Map<String,Object> retVal = new HashMap<String,Object>();
    retVal.put("tool", "survey");
    retVal.put("url", appNameUrlPrefix + appName + "/" +
        ODKFileUtils.asUriFragment(appName, new File(
            ODKFileUtils.getSystemFolder(appName), "index.html")) + hashUrl);
    retVal.put("tableId", "framework");
    retVal.put("formId", "framework");
    retVal.put("instanceId", "invariant:0");
    retVal.put("hashUrl", hashUrl);
    retVal.put("appName", appName);
    return retVal;
  }
}
