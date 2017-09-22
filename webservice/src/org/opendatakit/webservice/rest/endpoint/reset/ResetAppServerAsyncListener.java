package org.opendatakit.webservice.rest.endpoint.reset;

import java.io.IOException;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.Response;
import org.opendatakit.builder.InitializationOutcome;
import org.opendatakit.builder.InitializationSupervisor;
import org.opendatakit.builder.InitializationUtil;
import org.opendatakit.consts.CharsetConsts;
import org.opendatakit.database.service.UserDbInterface;
import org.opendatakit.database.service.UserDbInterfaceImpl;
import org.opendatakit.logging.WebLogger;
import org.opendatakit.services.database.AndroidConnectFactory;
import org.opendatakit.services.database.service.OdkDatabaseServiceImpl;
import org.opendatakit.services.sync.service.AppSynchronizer;
import org.opendatakit.services.sync.service.GlobalSyncNotificationManagerImpl;
import org.opendatakit.sync.service.SyncAttachmentState;

import android.content.Context;

public class ResetAppServerAsyncListener implements AsyncListener {
  private static final String TAG = "ResetAppServerAsyncListener";

  private final String appName;
  private final AsyncContext asyncContext;

  @Override
  public void onComplete(AsyncEvent arg0) throws IOException {
    // TODO Auto-generated method stub

  }

  @Override
  public void onError(AsyncEvent arg0) throws IOException {
    // TODO Auto-generated method stub

  }

  @Override
  public void onStartAsync(AsyncEvent arg0) throws IOException {
    // TODO Auto-generated method stub

  }

  @Override
  public void onTimeout(AsyncEvent arg0) throws IOException {

    HttpServletResponse response = (HttpServletResponse) asyncContext.getResponse();
    try {
      String responseJSON = "{\"error\":\"Timed out\"}";
      response.setContentType("application/json");
      response.setCharacterEncoding("utf-8");
      response.getOutputStream().write(responseJSON.getBytes(CharsetConsts.UTF_8));
      response.setStatus(Response.SC_REQUEST_TIMEOUT);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    asyncContext.complete();
  }

  ResetAppServerAsyncListener(AsyncContext asyncContext, String appName) {
    this.asyncContext = asyncContext;
    this.appName = appName;

    Runnable r = new Runnable() {

      @Override
      public void run() {
        HttpServletRequest request = null;
        HttpServletResponse response = null;
        try {
          response = (HttpServletResponse) asyncContext.getResponse();
          request = (HttpServletRequest) asyncContext.getRequest();
        } catch (Exception e) {
          WebLogger.getLogger(appName).i(TAG, "async context is no longer valid");
          return;
        }

        final Context appContext = new Context();

        final GlobalSyncNotificationManagerImpl notificationManager = new GlobalSyncNotificationManagerImpl(
            appContext);
        AndroidConnectFactory.configure();
        final UserDbInterface impl = new UserDbInterfaceImpl(
            new OdkDatabaseServiceImpl(appContext));

        // Sync goes here
        // version code is the numeric portion.
        // The 100's digit and higher identify incompatible
        // versions of the configuration files. This should
        // remain 2xx until there is a breaking change in
        // behavior, at which we should probably go to 3.0
        // on the tools.
        AppSynchronizer sync = new AppSynchronizer(appContext, "214", appName, notificationManager);

        try {
          sync.directSynchronize(true, SyncAttachmentState.SYNC);
        } catch (Throwable t) {
          WebLogger.getLogger(appName).e(TAG, "error during sync");
          WebLogger.getLogger(appName).printStackTrace(t);
        }

        InitializationUtil util = new InitializationUtil(appContext, appName,
            new InitializationSupervisor() {
              @Override
              public UserDbInterface getDatabase() {
                return impl;
              }

              @Override
              public void publishProgress(String progress, String detail) {
                WebLogger.getLogger(appName).i(TAG, progress + ": " + detail);
              }

              @Override
              public boolean isCancelled() {
                return false;
              }

              @Override
              public String getToolName() {
                // when null, we don't attempt to unzip the system and config
                // resources
                return null;
              }

              @Override
              public String getVersionCodeString() {
                // this is only reached if getToolName() returns non-null.
                throw new IllegalStateException("should never be reached");
              }

              @Override
              public int getSystemZipResourceId() {
                return -1;
              }

              @Override
              public int getConfigZipResourceId() {
                return -1;
              }
            });

        InitializationOutcome pendingOutcome = util.initialize();

        try {
          String responseJSON = "{\"success\":\"Reset completed\"}";
          response.setContentType("application/json");
          response.setCharacterEncoding("utf-8");
          response.getOutputStream().write(responseJSON.getBytes(CharsetConsts.UTF_8));
          response.setStatus(Response.SC_OK);
        } catch (IOException e) {
          WebLogger.getLogger(appName).e(TAG, "ResetAppServerAsyncListener");
          WebLogger.getLogger(appName).printStackTrace(e);
          try {
            response.sendError(Response.SC_INTERNAL_SERVER_ERROR, e.toString());
          } catch (IOException ex) {
            WebLogger.getLogger(appName).printStackTrace(ex);
          }
        }
        // complete the processing
        asyncContext.complete();
      }
    };

    asyncContext.start(r);
  }

  public String getAppName() {
    return appName;
  }

  public String getActiveUser() {
    // TODO Auto-generated method stub
    return null;
  }
}
