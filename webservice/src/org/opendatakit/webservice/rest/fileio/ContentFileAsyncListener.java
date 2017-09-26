package org.opendatakit.webservice.rest.fileio;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.Response;
import org.opendatakit.consts.CharsetConsts;
import org.opendatakit.logging.WebLogger;
import org.opendatakit.utilities.ODKFileUtils;
import org.opendatakit.webservice.configuration.OdkUserContext;

public class ContentFileAsyncListener implements AsyncListener {
  private static final String TAG = "ContentFileAsyncListener";
  private static final Set<String> textTypes;
  private static final Set<String> imageTypes;
  private static final Set<String> videoTypes;
  private static final Set<String> audioTypes;

  static {
    textTypes = new HashSet<String>();
    textTypes.add("html");
    textTypes.add("css");
    textTypes.add("js");
    textTypes.add("json");
    textTypes.add("csv");
    textTypes.add("xml");
    textTypes.add("init");
    textTypes.add("properties");
    imageTypes = new HashSet<String>();
    imageTypes.add("jpg");
    imageTypes.add("jpeg");
    imageTypes.add("gif");
    imageTypes.add("png");
    videoTypes = new HashSet<String>();
    videoTypes.add("mpeg");
    videoTypes.add("mpg");
    videoTypes.add("wav");
    videoTypes.add("wmv");
    audioTypes = new HashSet<String>();
    audioTypes.add("mp4");
  }

  private final String appNameUrlPrefix;
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

  ContentFileAsyncListener(AsyncContext asyncContext, String appNameUrlPrefixIn) {
    this.asyncContext = asyncContext;
    this.appNameUrlPrefix = appNameUrlPrefixIn;

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

        StringBuffer b = request.getRequestURL();

        String path = b.toString();
        String basePath = appNameUrlPrefix + appName + "/";
        if (path.startsWith(basePath)) {
          path = path.substring(basePath.length());
        }

        File file = null;
        try {
          file = ODKFileUtils.getAsFile(appName, path);
        } catch (Throwable t) {
          WebLogger.getLogger(appName).e(TAG, "failed to identify file");
          WebLogger.getLogger(appName).printStackTrace(t);
        }

        String name = null;
        String extension = null;
        if (file != null) {
          name = file.getName();
          int idx = name.lastIndexOf('.');
          extension = (idx != -1) ? name.substring(idx + 1) : null;
        }

        try {
          if (extension == null || extension.length() == 0) {
            response.setContentType("application/binary");
          } else if (textTypes.contains(extension)) {
            response.setContentType("text/" + extension + "; charset=utf-8");
          } else if (imageTypes.contains(extension)) {
            response.setContentType("image/" + extension);
          } else if (videoTypes.contains(extension)) {
            response.setContentType("video/" + extension);
          } else if (audioTypes.contains(extension)) {
            response.setContentType("audio/" + extension);
          }
          if (file.exists()) {
            ServletOutputStream out = response.getOutputStream();
            FileInputStream fin = new FileInputStream(file);
            BufferedInputStream in = new BufferedInputStream(fin);
            byte[] buffer = new byte[4096];
            int len;
            while ((len = in.read(buffer)) != -1) {
              out.write(buffer, 0, len);
            }
            in.close();
            response.setStatus(Response.SC_OK);
          } else {
            response.setStatus(Response.SC_NOT_FOUND);
          }
        } catch (IOException e) {
          WebLogger.getLogger(appName).e(TAG, name);
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
