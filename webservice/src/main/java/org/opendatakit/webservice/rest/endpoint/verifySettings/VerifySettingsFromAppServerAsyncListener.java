package org.opendatakit.webservice.rest.endpoint.verifySettings;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.Response;
import org.opendatakit.consts.CharsetConsts;
import org.opendatakit.database.service.UserDbInterface;
import org.opendatakit.database.service.UserDbInterfaceImpl;
import org.opendatakit.logging.WebLogger;
import org.opendatakit.properties.CommonToolProperties;
import org.opendatakit.properties.PropertiesSingleton;
import org.opendatakit.services.database.AndroidConnectFactory;
import org.opendatakit.services.database.service.OdkDatabaseServiceImpl;
import org.opendatakit.webservice.configuration.OdkUserContext;
import org.opendatakit.services.sync.service.AppSynchronizer;
import org.opendatakit.services.sync.service.GlobalSyncNotificationManagerImpl;
import org.opendatakit.services.utilities.ODKServicesPropertyUtils;
import org.opendatakit.sync.service.SyncOutcome;
import org.opendatakit.sync.service.SyncOverallResult;
import org.opendatakit.utilities.ODKFileUtils;

import android.content.Context;

public class VerifySettingsFromAppServerAsyncListener implements AsyncListener {
  private static final String TAG = "VerifySettingsFromAppServerAsyncListener";

  private final AsyncContext asyncContext;

  @Override
  public void onComplete(AsyncEvent event) throws IOException {
    // TODO: Should a result be passed back here??
//    HttpServletResponse response = (HttpServletResponse) asyncContext.getResponse();
//    OdkUserContext odkUserContext = OdkUserContext.getOdkUserContext(asyncContext);
//    String appName = odkUserContext.getAppName();
//
//    try {            
//      PropertiesSingleton props = odkUserContext.getPropertiesSingleton();
//
//      Map<String,String> responseMap = new HashMap<String,String>();
//      responseMap.put("appName", odkUserContext.getAppName());
//      responseMap.put("activeUser", ODKServicesPropertyUtils.getActiveUser(props));
//      responseMap.put("rolesList", props.getProperty(CommonToolProperties.KEY_ROLES_LIST));
//      responseMap.put("defaultGroup", props.getProperty(CommonToolProperties.KEY_DEFAULT_GROUP));
//      responseMap.put("usersList", props.getProperty(CommonToolProperties.KEY_USERS_LIST));
//      responseMap.put("serverUrl", props.getProperty(CommonToolProperties.KEY_SYNC_SERVER_URL));
//      responseMap.put("status", "success");
//      String responseString = ODKFileUtils.mapper.writeValueAsString(responseMap);
//      
//      response.setContentType("application/json");
//      response.setCharacterEncoding("utf-8");
//      response.getOutputStream().write(responseString.getBytes(CharsetConsts.UTF_8));
//      response.setStatus(Response.SC_OK);
//      
//    } catch (IOException e) {
//      WebLogger.getLogger(appName).e(TAG, "VerifySettingsFromAppServerAsyncListener");
//      WebLogger.getLogger(appName).printStackTrace(e);
//      try {
//        response.sendError(Response.SC_INTERNAL_SERVER_ERROR, e.toString());
//      } catch (IOException ex) {
//        WebLogger.getLogger(appName).printStackTrace(ex);
//      }
//    }
  }

  @Override
  public void onTimeout(AsyncEvent event) throws IOException {
    HttpServletResponse response = (HttpServletResponse) asyncContext.getResponse();
    try {
      String responseJSON = "{\"error\":\"Timed out\"}";
      response.setContentType("application/json");
      response.setCharacterEncoding("utf-8");
      response.getOutputStream().write(responseJSON.getBytes(CharsetConsts.UTF_8));
      response.setStatus(Response.SC_REQUEST_TIMEOUT);
    } catch (IOException e) {
      e.printStackTrace();
    }
    asyncContext.complete();
  }

  @Override
  public void onError(AsyncEvent event) throws IOException {
    HttpServletResponse response = (HttpServletResponse) asyncContext.getResponse();
    OdkUserContext odkUserContext = OdkUserContext.getOdkUserContext(asyncContext);
    String appName = odkUserContext.getAppName();
    
    try {
      Map<String,String> responseMap = new HashMap<String,String>();
      responseMap.put("status", "fail");
      String responseString = ODKFileUtils.mapper.writeValueAsString(responseMap);
      response.setContentType("application/json");
      response.setCharacterEncoding("utf-8");
      response.getOutputStream().write(responseString.getBytes(CharsetConsts.UTF_8));
      response.setStatus(Response.SC_OK);
    } catch (IOException e) {
      WebLogger.getLogger(appName).e(TAG, "VerifySettingsFromAppServerAsyncListener");
      WebLogger.getLogger(appName).printStackTrace(e);
      try {
        response.sendError(Response.SC_INTERNAL_SERVER_ERROR, e.toString());
      } catch (IOException ex) {
        WebLogger.getLogger(appName).printStackTrace(ex);
      }
    }
  }

  @Override
  public void onStartAsync(AsyncEvent event) throws IOException {
    // TODO Auto-generated method stub

  }
  
  VerifySettingsFromAppServerAsyncListener(AsyncContext asyncContext) {
    this.asyncContext = asyncContext;
    
    Runnable r = new Runnable() {
      @Override
      public void run() {
        OdkUserContext odkUserContext = null;
        String theAppName = null;
        HttpServletResponse response = null;
        try  {
          odkUserContext = OdkUserContext.getOdkUserContext(asyncContext);
          theAppName = odkUserContext.getAppName();
          response = (HttpServletResponse) asyncContext.getResponse();
        } catch (Exception e) {
          WebLogger.getLogger(theAppName).i(TAG, "asyncContext is not valid");
          return;
        }
        
        final String appName = theAppName;
        
        final Context appContext = odkUserContext.getContext();
        
        final GlobalSyncNotificationManagerImpl notificationManager = 
            new GlobalSyncNotificationManagerImpl(appContext);
        
        AndroidConnectFactory.configure();
        final UserDbInterface impl = new UserDbInterfaceImpl(new OdkDatabaseServiceImpl(appContext));
        
        // TODO: AppSynchronizer = Need to find a better way of getting the 
        // version code
        AppSynchronizer sync = new AppSynchronizer(appContext, "214", appName, notificationManager);
        
        try {
          sync.verifyServerSettings();
        } catch (Throwable t) {
          WebLogger.getLogger(appName).e(TAG, "error while trying to verify settings");
          WebLogger.getLogger(appName).printStackTrace(t);
        }
        
        // complete processing
        asyncContext.complete();
      }
    };
    
    asyncContext.start(r);
  }

}
