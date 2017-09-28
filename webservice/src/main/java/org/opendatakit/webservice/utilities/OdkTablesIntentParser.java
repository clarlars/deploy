package org.opendatakit.webservice.utilities;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.opendatakit.consts.IntentConsts;
import org.opendatakit.database.data.OrderedColumns;
import org.opendatakit.database.service.DbHandle;
import org.opendatakit.database.service.UserDbInterface;
import org.opendatakit.database.service.UserDbInterfaceImpl;
import org.opendatakit.exception.ServicesAvailabilityException;
import org.opendatakit.properties.CommonToolProperties;
import org.opendatakit.services.database.AndroidConnectFactory;
import org.opendatakit.services.database.service.OdkDatabaseServiceImpl;
import org.opendatakit.tables.data.PossibleTableViewTypes;
import org.opendatakit.tables.data.ViewFragmentType;
import org.opendatakit.tables.utils.Constants;
import org.opendatakit.utilities.ODKFileUtils;
import org.opendatakit.views.OdkData;
import org.opendatakit.views.ViewDataQueryParams;
import org.opendatakit.webservice.configuration.OdkUserContext;

import android.os.Bundle;

/**
 * Attempt to interpret an intent description as a tables intent, and return the
 * url that should be loaded by the webpage in response.
 * 
 * @author mitchellsundt@gmail.com
 *
 */
public class OdkTablesIntentParser {

  private static final String QUERY_START_PARAM = "?";

  private final OdkUserContext mUserContext;
  private final String mAppNameUrlPrefix;

  public OdkTablesIntentParser(OdkUserContext thisContext, String appNameUrlPrefix) {
    mUserContext = thisContext;
    mAppNameUrlPrefix = appNameUrlPrefix;
  }

  /**
   * Return the file name from the bundle. Convenience method for calling
   * {@link Bundle#getString(String)} with
   * {@link Constants.IntentKeys#FILE_NAME}.
   * 
   * @param bundle
   * @return the file name, null if it does not exist or if bundle is null
   */
  public static String retrieveFileNameFromBundle(Map<String, Object> bundle) {
    if (bundle == null) {
      return null;
    }
    String fileName = (String) bundle.get(Constants.IntentKeys.FILE_NAME);
    return fileName;
  }

  private String[] checkForQueryParameter(File webFile) {
    String webFileToDisplayPath = webFile.getPath();
    String[] webFileStrs = webFileToDisplayPath.split("[" + QUERY_START_PARAM + "]", 2);
    return webFileStrs;
  }

  /**
   * Retrieve the app-relative file name from either the saved instance state or
   * the {@link Intent} that was used to create the activity.
   * 
   * If none supplied, then return the default home screen if we are configured
   * to show it. If we are configured to show it and it is not present, clear
   * that flag.
   * 
   * @return
   */
  protected File getHomeScreen(Map<String, Object> extras) {
    Boolean setting = mUserContext.getPropertiesSingleton()
        .getBooleanProperty(CommonToolProperties.KEY_USE_HOME_SCREEN);
    String relativeFileName = retrieveFileNameFromBundle(extras);

    File userHomeScreen = null;
    if (relativeFileName != null) {
      userHomeScreen = ODKFileUtils.asAppFile(mUserContext.getAppName(), relativeFileName);
    } else {
      userHomeScreen = new File(ODKFileUtils.getTablesHomeScreenFile(mUserContext.getAppName()));
    }

    // Make sure that query parameters are still passed through
    String[] userHomeScreenUrlParts = checkForQueryParameter(userHomeScreen);
    File userHomeScreenFile = userHomeScreen;
    if (userHomeScreenUrlParts.length > 1) {
      userHomeScreenFile = new File(userHomeScreenUrlParts[0]);
    }

    if ((relativeFileName != null) || (setting == null ? false : setting)
        || (userHomeScreenFile.exists() && userHomeScreenFile.isFile())) {
      return userHomeScreen;
    } else {
      if ((setting == null || setting == Boolean.TRUE) && relativeFileName == null) {
        // the home screen doesn't exist but we are requesting to show it --
        // clear the setting
        mUserContext.getPropertiesSingleton()
          .setProperties(Collections.singletonMap(CommonToolProperties.KEY_USE_HOME_SCREEN,
            Boolean.toString(false)));
      }
      return null;
    }
  }

  public String getUrlBaseLocation(File webFileToDisplay) {
    // Split off query parameter if it exists
    String[] webFileStrs = checkForQueryParameter(webFileToDisplay);
    String filename = null;
    if (webFileStrs.length > 1) {
      File webFile = new File(webFileStrs[0]);
      filename = ODKFileUtils.asUriFragment(mUserContext.getAppName(), webFile);
    } else {
      filename = ODKFileUtils.asUriFragment(mUserContext.getAppName(), webFileToDisplay);
    }

    if (filename != null) {
      if (webFileStrs.length > 1) {
        return filename.concat(QUERY_START_PARAM).concat(webFileStrs[1]);
      } else {
        return filename;
      }
    }
    return null;
  }

  public Map<String, Object> getMainActivityUrl(String action, Map<String, Object> intent) {
    if (!action.equals("org.opendatakit.tables.activities.MainActivity")) {
      return null;
    }

    File webFileToDisplay = getHomeScreen((Map<String, Object>) intent.get("extras"));
    if (webFileToDisplay != null) {
      String url = getUrlBaseLocation(webFileToDisplay);

      Map<String, Object> retVal = new HashMap<String, Object>();
      retVal.put("tool", "tables");
      retVal.put("url", mAppNameUrlPrefix + mUserContext.getAppName() + "/" + url);
      retVal.put("appName", mUserContext.getAppName());
      return retVal;
    }
    return null;
  }

  /**
   * Retrieve the table id from the intent. Returns null if not present.
   * 
   * @return
   */
  String retrieveTableIdFromIntent(Map<String, Object> intent) {
    Map<String, Object> extras = (Map<String, Object>) intent.get("extras");
    return (String) extras.get(IntentConsts.INTENT_KEY_TABLE_ID);
  }

  private ViewDataQueryParams readQueryFromIntent(Map<String, Object> intent) {
    Map<String, Object> extras = (Map<String, Object>) intent.get("extras");

    ViewDataQueryParams queryParams = ViewDataQueryParamsHelper
        .readQueryFromIntentExtrasSubset(extras);
    return queryParams;
  }

  private String getDefaultFileNameForViewFragmentType(ViewFragmentType fragmentType) {
    if (mPossibleTableViewTypes == null || fragmentType == null) {
      return null;
    }
    switch (fragmentType) {
    case SPREADSHEET:
      return null;
    case LIST:
      return mPossibleTableViewTypes.getDefaultListViewFileName();
    case MAP:
      return mPossibleTableViewTypes.getDefaultMapListViewFileName();
    case DETAIL:
      return mPossibleTableViewTypes.getDefaultDetailFileName();
    case DETAIL_WITH_LIST:
    case SUB_LIST:
      return null; // TODO: For now there is no default detail with list. Do we
                   // need one?
    default:
      return null;
    }
  }

  ViewFragmentType mOriginalFragmentType;
  ViewFragmentType mCurrentFragmentType;
  String mOriginalFileName;
  String mCurrentFileName;
  String mCurrentSubFileName;

  /** Cached data from database */
  private PossibleTableViewTypes mPossibleTableViewTypes = null;

  private void possiblySupplyDefaults(UserDbInterface dbInterface,
      String tableId) throws ServicesAvailabilityException {

    if (mPossibleTableViewTypes == null) {
      DbHandle db = null;
      try {
        db = dbInterface.openDatabase(mUserContext.getAppName());
        OrderedColumns columnDefinitions = dbInterface.getUserDefinedColumns(mUserContext.getAppName(), db,
            tableId);

        mPossibleTableViewTypes = new PossibleTableViewTypes(dbInterface, mUserContext.getAppName(), db, tableId,
            columnDefinitions);
      } finally {
        if (db != null) {
          try {
            dbInterface.closeDatabase(mUserContext.getAppName(), db);
          } catch (ServicesAvailabilityException e) {
            e.printStackTrace();
          }
        }
      }
    }

    if (mOriginalFragmentType == null && mPossibleTableViewTypes != null) {
      // recover the default view for this table from the database...
      mOriginalFragmentType = mPossibleTableViewTypes.getDefaultViewType();
    }

    ViewFragmentType original = mOriginalFragmentType;
    if (mOriginalFragmentType == null) {
      // and if that isn't set, use spreadsheet
      original = ViewFragmentType.SPREADSHEET;
    }

    if (mOriginalFileName == null) {
      mOriginalFileName = getDefaultFileNameForViewFragmentType(mOriginalFragmentType);
    }

    if (mCurrentFragmentType == null) {
      mCurrentFragmentType = original;
      mCurrentFileName = mOriginalFileName;
    }

    if (mCurrentFileName == null) {
      mCurrentFileName = getDefaultFileNameForViewFragmentType(mCurrentFragmentType);
    }

    if ((mCurrentFragmentType == ViewFragmentType.DETAIL_WITH_LIST
        || mCurrentFragmentType == ViewFragmentType.SUB_LIST) && mCurrentSubFileName == null) {
      mCurrentSubFileName = getDefaultFileNameForViewFragmentType(ViewFragmentType.SUB_LIST);
    }
  }

  public String getUrlBaseLocation(boolean ifChanged, String fragmentID) {
    // TODO: do we need to track the ifChanged status?

    String filename;
    if (fragmentID != null && fragmentID.equals(Constants.FragmentTags.DETAIL_WITH_LIST_LIST)) {
      filename = mCurrentSubFileName;
    } else {
      filename = mCurrentFileName;
    }
    if (filename != null) {
      // we need to escape the segments.
      String pathPart;
      String queryPart;
      String hashPart;
      int idxQ = filename.indexOf("?");
      int idxH = filename.indexOf("#");
      if (idxQ != -1) {
        if (idxH != -1) {
          if (idxH < idxQ) {
            pathPart = filename.substring(0, idxH);
            queryPart = "";
            hashPart = filename.substring(idxH);
          } else {
            pathPart = filename.substring(0, idxQ);
            queryPart = filename.substring(idxQ, idxH);
            hashPart = filename.substring(idxH);
          }
        } else {
          pathPart = filename.substring(0, idxQ);
          queryPart = filename.substring(idxQ);
          hashPart = "";
        }
      } else if (idxH != -1) {
        pathPart = filename.substring(0, idxH);
        queryPart = "";
        hashPart = filename.substring(idxH);
      } else {
        pathPart = filename;
        queryPart = "";
        hashPart = "";
      }

      String webFragment = ODKFileUtils.asUriFragment(mUserContext.getAppName(),
          new File(ODKFileUtils.getAppFolder(mUserContext.getAppName()), pathPart));
      // unclear what escaping is needed on query and hash parts...
      return webFragment + queryPart + hashPart;
    }
    return null;
  }

  public Map<String, Object> getTableActivityUrl(String action,
      Map<String, Object> intent) {
    if (!action.equals("org.opendatakit.tables.activities.TableDisplayActivity")) {
      return null;
    }
    Map<String, Object> extras = (Map<String, Object>) intent.get("extras");

    String mTableId = retrieveTableIdFromIntent(intent);
    if (mTableId == null) {
      return null;
    }

    ODKFileUtils.assertDirectoryStructure(mUserContext.getAppName());
    AndroidConnectFactory.configure();
    UserDbInterface dbInterface = new UserDbInterfaceImpl(
        new OdkDatabaseServiceImpl(mUserContext.getContext()));

    /*
     * If we are restoring from a saved state, the fleshed-out original view
     * type and filename will be in the savedInstance bundle. Otherwise, we will
     * need to extract it from the Intent.
     *
     * Once they are extracted, the original values may be fleshed out from the
     * database and configuration settings. The fleshed-out values will be
     * stored in the savedInstanceState so that recovery can proceed more
     * quickly
     */

    if (mOriginalFragmentType == null) {
      // get the information from the Intent
      String viewType = (extras != null
          && extras.containsKey(Constants.IntentKeys.TABLE_DISPLAY_VIEW_TYPE))
              ? ((String) extras.get(Constants.IntentKeys.TABLE_DISPLAY_VIEW_TYPE)) : null;

      if (viewType != null) {
        mOriginalFragmentType = ViewFragmentType.valueOf(viewType);
      }
    }
    if (mOriginalFileName == null) {
      // get the information from the Intent
      mOriginalFileName = (extras != null && extras.containsKey(Constants.IntentKeys.FILE_NAME))
          ? ((String) extras.get(Constants.IntentKeys.FILE_NAME)) : null;
    }

    ViewDataQueryParams queryParams = readQueryFromIntent(intent);

    // onResume actions
    try {
      possiblySupplyDefaults(dbInterface, mTableId);
    } catch (ServicesAvailabilityException e) {
      throw new IllegalStateException("unexpected ", e);
    }

    ////////////////////////
    // process fragment and view rendering...
    String baseUrl;
    switch (mCurrentFragmentType) {
    case SPREADSHEET:
      throw new IllegalArgumentException("Unsupported display (SPREADSHEET)");
      // break;
    case DETAIL:
      baseUrl = getUrlBaseLocation(false, null);
      break;
    case SUB_LIST:
      baseUrl = getUrlBaseLocation(false, null);
      break;
    case DETAIL_WITH_LIST:
      baseUrl = getUrlBaseLocation(false, null);
      break;
    case LIST:
      baseUrl = getUrlBaseLocation(false, null);
      break;
    case MAP:
      throw new IllegalArgumentException("Unsupported display (MAP)");
      // TODO: protected int mSelectedItemIndex;
      // baseUrl = getUrlBaseLocation(thisAppName, false, null);
      // break;
    default:
      throw new IllegalArgumentException("Unsupported display");
      // break;
    }

    Map<String, Object> retVal = new HashMap<String, Object>();
    retVal.put("tool", "tables");
    retVal.put("url", mAppNameUrlPrefix + mUserContext.getAppName() + "/" + baseUrl);
    retVal.put("tableDisplayViewType", mCurrentFragmentType.name());

    retVal.put(OdkData.IntentKeys.TABLE_ID, queryParams.tableId);
    retVal.put(IntentConsts.INTENT_KEY_INSTANCE_ID, queryParams.rowId);
    retVal.put(OdkData.IntentKeys.SQL_WHERE, queryParams.whereClause);
    retVal.put(OdkData.IntentKeys.SQL_SELECTION_ARGS, queryParams.selectionArgs.asJSON());
    retVal.put(OdkData.IntentKeys.SQL_GROUP_BY_ARGS, queryParams.groupBy);
    retVal.put(OdkData.IntentKeys.SQL_HAVING, queryParams.having);
    retVal.put(OdkData.IntentKeys.SQL_ORDER_BY_ELEMENT_KEY, queryParams.orderByElemKey);
    retVal.put(OdkData.IntentKeys.SQL_ORDER_BY_DIRECTION, queryParams.orderByDir);
    retVal.put(IntentConsts.INTENT_KEY_APP_NAME, mUserContext.getAppName());
    return retVal;
  }

}
