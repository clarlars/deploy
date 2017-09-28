package org.opendatakit.webservice.rest.mock;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.opendatakit.views.IOdkWebView;
import org.opendatakit.views.OdkData;
import org.opendatakit.views.ViewDataQueryParams;
import org.opendatakit.webservice.bridge.OdkDataActivityImpl;
import org.opendatakit.webservice.configuration.OdkTool;
import org.opendatakit.webservice.configuration.OdkUserContext;
import org.opendatakit.webservice.utilities.ViewDataQueryParamsHelper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Mock implementation for the odkDataIf injection.
 * This processes AJAX requests that mimic that injection.
 * 
 * @author mitchellsundt@gmail.com
 */
@WebServlet(urlPatterns = "/OdkDataHostIf", asyncSupported = true)
public class OdkDataHostIf extends HttpServlet {
  private static final long serialVersionUID = 1L;
  static final String TAG = "OdkDataHostIf";

  /**
   * @see HttpServlet#HttpServlet()
   */
  public OdkDataHostIf() {
    super();
    // TODO Auto-generated constructor stub
  }

  private static class OdkViewImpl implements IOdkWebView {
    @Override
    public boolean isInactive() {
      return false;
    }

    @Override
    public String getContainerFragmentID() {
      return null;
    }
  }

  private static OdkViewImpl theViewImpl = new OdkViewImpl();

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    OdkUserContext ctxt = OdkUserContext.establishOdkUserContext(request);

    AsyncContext asyncCtx = request.startAsync();

    ObjectMapper mapper = new ObjectMapper();
    TypeReference ref = new TypeReference<HashMap<String, Object>>() {
    };

    HashMap<String, Object> theRequest = null;
    try {
      theRequest = mapper.readValue(request.getInputStream(), ref);
    } catch (Exception e) {
      e.printStackTrace();
    }

    String requestName = (String) theRequest.get("request");
    String callbackJSON = theRequest.get("callbackId").toString();

    Map<String, Object> url = (Map<String, Object>) theRequest.get("url");
    String href = (String) url.get("href");
    OdkTool tool = (href.contains("system/index.html") ? OdkTool.SURVEY : OdkTool.TABLES);
    ViewDataQueryParams params = null;
    if (tool == OdkTool.TABLES && theRequest.containsKey("viewDataParams")) {
      Map<String, Object> viewDataParams = (Map<String, Object>) theRequest.get("viewDataParams");
      params = ViewDataQueryParamsHelper.readQueryFromIntentExtrasSubset(viewDataParams);
    }

    OdkDataActivityImpl action = new OdkDataActivityImpl(asyncCtx, tool, params);

    asyncCtx.addListener(action);
    asyncCtx.setTimeout(90000L);
    OdkData theData = new OdkData(action, theViewImpl);

    if ("getViewData".equals(requestName)) {
      Integer limit = (Integer) theRequest.get("limit");
      Integer offset = (Integer) theRequest.get("offset");
      theData.getViewData(callbackJSON, limit, offset);
    } else if ("getRoles".equals(requestName)) {
      theData.getRoles(callbackJSON);
    } else if ("getDefaultGroup".equals(requestName)) {
      theData.getDefaultGroup(callbackJSON);
    } else if ("getUsers".equals(requestName)) {
      theData.getUsers(callbackJSON);
    } else if ("getAllTableIds".equals(requestName)) {
      theData.getAllTableIds(callbackJSON);
    } else if ("query".equals(requestName)) {
      String tableId = (String) theRequest.get(OdkData.IntentKeys.TABLE_ID);
      String whereClause = (String) theRequest.get(OdkData.IntentKeys.SQL_WHERE);
      String sqlBindParamsJSON = (String) theRequest.get(OdkData.IntentKeys.SQL_SELECTION_ARGS);
      List<String> groupByList = (List<String>) theRequest
          .get(OdkData.IntentKeys.SQL_GROUP_BY_ARGS);
      String[] groupBy = ((groupByList == null) || groupByList.isEmpty()) ? null
          : (String[]) groupByList.toArray(new String[groupByList.size()]);
      String having = (String) theRequest.get(OdkData.IntentKeys.SQL_HAVING);
      String orderByElementKey = (String) theRequest
          .get(OdkData.IntentKeys.SQL_ORDER_BY_ELEMENT_KEY);
      String orderByDirection = (String) theRequest.get(OdkData.IntentKeys.SQL_ORDER_BY_DIRECTION);
      Integer limit = (Integer) theRequest.get("limit");
      Integer offset = (Integer) theRequest.get("offset");
      String metaDataRev = (String) theRequest.get(OdkData.IntentKeys.META_DATA_REV);
      Boolean includeKeyValueStoreMap = (Boolean) theRequest.get("includeKVS");
      theData.query(tableId, whereClause, sqlBindParamsJSON, groupBy, having, orderByElementKey,
          orderByDirection, limit, offset, includeKeyValueStoreMap, metaDataRev, callbackJSON);
    } else if ("arbitraryQuery".equals(requestName)) {
      String tableId = (String) theRequest.get(OdkData.IntentKeys.TABLE_ID);
      String sqlCommand = (String) theRequest.get("sqlCommand");
      String sqlBindParamsJSON = (String) theRequest.get(OdkData.IntentKeys.SQL_SELECTION_ARGS);
      Integer limit = (Integer) theRequest.get("limit");
      Integer offset = (Integer) theRequest.get("offset");
      String metaDataRev = (String) theRequest.get(OdkData.IntentKeys.META_DATA_REV);
      theData.arbitraryQuery(tableId, sqlCommand, sqlBindParamsJSON, limit, offset, metaDataRev,
          callbackJSON);
    } else if ("getRows".equals(requestName)) {
      String tableId = (String) theRequest.get(OdkData.IntentKeys.TABLE_ID);
      String rowId = (String) theRequest.get(OdkData.IntentKeys.ROW_ID);
      String metaDataRev = (String) theRequest.get(OdkData.IntentKeys.META_DATA_REV);
      theData.getRows(tableId, rowId, metaDataRev, callbackJSON);
    } else if ("getMostRecentRow".equals(requestName)) {
      String tableId = (String) theRequest.get(OdkData.IntentKeys.TABLE_ID);
      String rowId = (String) theRequest.get(OdkData.IntentKeys.ROW_ID);
      String metaDataRev = (String) theRequest.get(OdkData.IntentKeys.META_DATA_REV);
      theData.getMostRecentRow(tableId, rowId, metaDataRev, callbackJSON);
    } else if ("changeAccessFilterOfRow".equals(requestName)) {
      String tableId = (String) theRequest.get(OdkData.IntentKeys.TABLE_ID);
      String defaultAccess = (String) theRequest.get("defaultAccess");
      String rowOwner = (String) theRequest.get("rowOwner");
      String groupReadOnly = (String) theRequest.get("groupReadOnly");
      String groupModify = (String) theRequest.get("groupModify");
      String groupPrivileged = (String) theRequest.get("groupPrivileged");
      String rowId = (String) theRequest.get(OdkData.IntentKeys.ROW_ID);
      String metaDataRev = (String) theRequest.get(OdkData.IntentKeys.META_DATA_REV);
      theData.changeAccessFilterOfRow(tableId, defaultAccess, rowOwner, groupReadOnly, groupModify,
          groupPrivileged, rowId, metaDataRev, callbackJSON);
    } else if ("updateRow".equals(requestName)) {
      String tableId = (String) theRequest.get(OdkData.IntentKeys.TABLE_ID);
      String stringifiedJSON = (String) theRequest.get("stringifiedJSON");
      String rowId = (String) theRequest.get(OdkData.IntentKeys.ROW_ID);
      String metaDataRev = (String) theRequest.get(OdkData.IntentKeys.META_DATA_REV);
      theData.updateRow(tableId, stringifiedJSON, rowId, metaDataRev, callbackJSON);
    } else if ("deleteRow".equals(requestName)) {
      String tableId = (String) theRequest.get(OdkData.IntentKeys.TABLE_ID);
      String stringifiedJSON = (String) theRequest.get("stringifiedJSON");
      String rowId = (String) theRequest.get(OdkData.IntentKeys.ROW_ID);
      String metaDataRev = (String) theRequest.get(OdkData.IntentKeys.META_DATA_REV);
      theData.deleteRow(tableId, stringifiedJSON, rowId, metaDataRev, callbackJSON);
    } else if ("addRow".equals(requestName)) {
      String tableId = (String) theRequest.get(OdkData.IntentKeys.TABLE_ID);
      String stringifiedJSON = (String) theRequest.get("stringifiedJSON");
      String rowId = (String) theRequest.get(OdkData.IntentKeys.ROW_ID);
      String metaDataRev = (String) theRequest.get(OdkData.IntentKeys.META_DATA_REV);
      theData.addRow(tableId, stringifiedJSON, rowId, metaDataRev, callbackJSON);
    } else if ("addCheckpoint".equals(requestName)) {
      String tableId = (String) theRequest.get(OdkData.IntentKeys.TABLE_ID);
      String stringifiedJSON = (String) theRequest.get("stringifiedJSON");
      String rowId = (String) theRequest.get(OdkData.IntentKeys.ROW_ID);
      String metaDataRev = (String) theRequest.get(OdkData.IntentKeys.META_DATA_REV);
      theData.addCheckpoint(tableId, stringifiedJSON, rowId, metaDataRev, callbackJSON);
    } else if ("saveCheckpointAsIncomplete".equals(requestName)) {
      String tableId = (String) theRequest.get(OdkData.IntentKeys.TABLE_ID);
      String stringifiedJSON = (String) theRequest.get("stringifiedJSON");
      String rowId = (String) theRequest.get(OdkData.IntentKeys.ROW_ID);
      String metaDataRev = (String) theRequest.get(OdkData.IntentKeys.META_DATA_REV);
      theData.saveCheckpointAsIncomplete(tableId, stringifiedJSON, rowId, metaDataRev,
          callbackJSON);
    } else if ("saveCheckpointAsComplete".equals(requestName)) {
      String tableId = (String) theRequest.get(OdkData.IntentKeys.TABLE_ID);
      String stringifiedJSON = (String) theRequest.get("stringifiedJSON");
      String rowId = (String) theRequest.get(OdkData.IntentKeys.ROW_ID);
      String metaDataRev = (String) theRequest.get(OdkData.IntentKeys.META_DATA_REV);
      theData.saveCheckpointAsComplete(tableId, stringifiedJSON, rowId, metaDataRev, callbackJSON);
    } else if ("deleteAllCheckpoints".equals(requestName)) {
      String tableId = (String) theRequest.get(OdkData.IntentKeys.TABLE_ID);
      String rowId = (String) theRequest.get(OdkData.IntentKeys.ROW_ID);
      String metaDataRev = (String) theRequest.get(OdkData.IntentKeys.META_DATA_REV);
      theData.deleteAllCheckpoints(tableId, rowId, metaDataRev, callbackJSON);
    } else if ("deleteLastCheckpoint".equals(requestName)) {
      String tableId = (String) theRequest.get(OdkData.IntentKeys.TABLE_ID);
      String rowId = (String) theRequest.get(OdkData.IntentKeys.ROW_ID);
      String metaDataRev = (String) theRequest.get(OdkData.IntentKeys.META_DATA_REV);
      theData.deleteLastCheckpoint(tableId, rowId, metaDataRev, callbackJSON);
    } else {
      throw new IllegalStateException("unexpected request name: " + requestName);
    }
  }

}
