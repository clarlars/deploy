package org.opendatakit.webservice.rest.configure;

import java.io.File;
import java.io.IOException;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.Response;
import org.apache.commons.io.FileUtils;
import org.opendatakit.builder.InitializationOutcome;
import org.opendatakit.builder.InitializationSupervisor;
import org.opendatakit.builder.InitializationUtil;
import org.opendatakit.consts.CharsetConsts;
import org.opendatakit.database.service.UserDbInterface;
import org.opendatakit.database.service.UserDbInterfaceImpl;
import org.opendatakit.logging.WebLogger;
import org.opendatakit.services.database.AndroidConnectFactory;
import org.opendatakit.services.database.service.OdkDatabaseServiceImpl;
import org.opendatakit.utilities.ODKFileUtils;
import org.opendatakit.webservice.configuration.OdkUserContext;

import android.content.Context;

public class ReInitializeAsyncListener implements AsyncListener {
  private static final String TAG = "ReInitializeAsyncListener";

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

  ReInitializeAsyncListener(AsyncContext asyncContext) {
    this.asyncContext = asyncContext;

    asyncContext.start(new Runnable() {

      @Override
      public void run() {
        OdkUserContext odkUserContext = null;
        String theAppName = null;
        HttpServletRequest request = null;
        HttpServletResponse response = null;
        try {
          odkUserContext = OdkUserContext.getOdkUserContext(asyncContext);
          theAppName = odkUserContext.getAppName();
          response = (HttpServletResponse) asyncContext.getResponse();
          request = (HttpServletRequest) asyncContext.getRequest();
        } catch (Exception e) {
          WebLogger.getLogger(theAppName).i(TAG, "async context is no longer valid");
          return;
        }
        final String appName = theAppName;

        // we don't need any info on the request.
        // just destroy and re-create the content of the
        // data tree under the application.

        File dataDir = new File(ODKFileUtils.getDataFolder(appName));
        if (dataDir.exists()) {
          try {
            try {
              FileUtils.deleteDirectory(dataDir);
            } catch (Throwable t) {
              WebLogger.getLogger(appName).e(TAG, "unable to delete data directory");
              WebLogger.getLogger(appName).printStackTrace(t);
            }
            FileUtils.deleteQuietly(dataDir);
            // ensure the needed directories and files are recreated...
            ODKFileUtils.verifyExternalStorageAvailability();
            ODKFileUtils.assertDirectoryStructure(appName);
          } catch (Exception ignored) {
            throw new IllegalArgumentException("External storage not available");
          }
        }

        // and run the initialization logic
        final Context appContext = odkUserContext.getContext();

        AndroidConnectFactory.configure();
        final UserDbInterface impl = new UserDbInterfaceImpl(
            new OdkDatabaseServiceImpl(appContext));

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
          String responseJSON = "{\"success\":\"Reinitialized\"}";
          response.setContentType("application/json");
          response.setCharacterEncoding("utf-8");
          response.getOutputStream().write(responseJSON.getBytes(CharsetConsts.UTF_8));
          response.setStatus(Response.SC_OK);
        } catch (IOException e) {
          WebLogger.getLogger(appName).e(TAG, "OdkReInitialize");
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
    });
  }
}
