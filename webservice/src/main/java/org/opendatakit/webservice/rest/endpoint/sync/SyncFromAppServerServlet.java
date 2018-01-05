package org.opendatakit.webservice.rest.endpoint.sync;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.Response;
import org.opendatakit.consts.CharsetConsts;
import org.opendatakit.logging.WebLogger;
import org.opendatakit.utilities.ODKFileUtils;
import org.opendatakit.webservice.configuration.OdkUserContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import android.content.Context;

/**
 * Performs a sync to the cloud endpoint.
 * 
 * @author mitchellsundt@gmail.com
 *
 */
@WebServlet(urlPatterns = "/OdkSyncFromAppServer", asyncSupported = true)
public class SyncFromAppServerServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  static final String TAG = "SyncFromAppServerServlet";
  
  // TODO: This will need a '/' once sync id's are passed back and forth
  static final String END_KNOWN_PATH_PART = "/OdkSyncFromAppServer";
  
  private static Map<String, DesktopSyncNotificationManagerImpl> listOfNotificationManagers = 
      new ConcurrentHashMap<String, DesktopSyncNotificationManagerImpl>();

  /**
   * @see HttpServlet#HttpServlet()
   */
  public SyncFromAppServerServlet() {
    super();
  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    // TODO: Deal with "default" appName
    OdkUserContext.establishOdkUserContext(request, OdkUserContext.DEFAULT_APP_NAME);

    AsyncContext asyncCtx = request.startAsync();

    //TODO: Have this return an id back to the client
    SyncFromAppServerAsyncListener action = new SyncFromAppServerAsyncListener(asyncCtx, listOfNotificationManagers);

    asyncCtx.addListener(action);
    asyncCtx.setTimeout(240000L);
  }
  
  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    
    // We should figure this out from the url?? 
    // TODO: Deal with "default"
    OdkUserContext odkUserContext = OdkUserContext.establishOdkUserContext(request, OdkUserContext.DEFAULT_APP_NAME);

    ObjectMapper mapper = new ObjectMapper();

    String requestUrl = request.getRequestURL().toString();
    String queryString = request.getQueryString();
    int idx = requestUrl.indexOf(END_KNOWN_PATH_PART);
    if (idx == -1) {
      response.sendError(Response.SC_BAD_REQUEST);
      return;
    }

    // and run the initialization logic
    final Context appContext = odkUserContext.getContext();

    String responseString;
    String[] parts = queryString.split("uuid=");
    String syncId = null;
    
    if (parts.length >= 2) {
      syncId = parts[1];
    }

    if (syncId != null && listOfNotificationManagers.containsKey(syncId)) {
      responseString = getNotifcationStatusForId(syncId);
      System.out.println("CLARICE: Notification manager found syncId:" + syncId + " response:" + responseString);
    } else {
      response.sendError(Response.SC_NOT_FOUND);
      return;
    }

    try {
      response.setContentType("text/html");
      response.setCharacterEncoding("utf-8");
      response.getOutputStream().write(responseString.getBytes(CharsetConsts.UTF_8));
      response.setStatus(Response.SC_OK);
    } catch (IOException e) {
      WebLogger.getLogger(odkUserContext.getAppName()).e(TAG, e.toString());
      WebLogger.getLogger(odkUserContext.getAppName()).printStackTrace(e);
      response.sendError(Response.SC_INTERNAL_SERVER_ERROR, e.toString());
    }
  }

  private String getNotifcationStatusForId(String syncId) throws JsonProcessingException {
    String responseString;
    DesktopSyncNotificationManagerImpl desktopNotificationManager = listOfNotificationManagers.get(syncId);
    String status = desktopNotificationManager.getStatus();
    Map<String, String> responseMap = new HashMap<String, String>();
    responseMap.put("status", status);
    responseString = ODKFileUtils.mapper.writeValueAsString(responseMap);
    return responseString;
  }

}
