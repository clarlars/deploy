package org.opendatakit.webservice.rest.fileio;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.Response;
import org.opendatakit.consts.CharsetConsts;
import org.opendatakit.logging.WebLogger;
import org.opendatakit.utilities.ODKFileUtils;
import org.opendatakit.webservice.configuration.OdkUserContext;

public class UploadFileAsyncListener implements AsyncListener {
  private static final String TAG = "UploadFileAsyncListener";
  private static final String URL_PATTERN = "/OdkFilesIf/";
  private static final String CONFIG_URL_PATTERN = "/config/";

  private final AsyncContext asyncContext;

  @Override
  public void onComplete(AsyncEvent arg0) throws IOException {

  }

  @Override
  public void onError(AsyncEvent arg0) throws IOException {

  }

  @Override
  public void onStartAsync(AsyncEvent arg0) throws IOException {

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

  UploadFileAsyncListener(AsyncContext asyncContext) {
    this.asyncContext = asyncContext;

    this.asyncContext.start(new Runnable() {

      @Override
      public void run() {
        OdkUserContext odkUserContext = null;        
        String appName = null;
        
        HttpServletRequest request = null;
        HttpServletResponse response = null;

        InputStream is = null;
        BufferedOutputStream os = null;

        try {
          odkUserContext = OdkUserContext.getOdkUserContext(asyncContext);
          appName = odkUserContext.getAppName();
          
          request = (HttpServletRequest) asyncContext.getRequest();
          response = (HttpServletResponse) asyncContext.getResponse();

          String requestUrl = request.getRequestURL().toString();

          int idx = requestUrl.indexOf(URL_PATTERN);

          int filePathIdx = idx + URL_PATTERN.length();
          String saveFilePath = requestUrl.substring(filePathIdx, requestUrl.length());

          // Actually - we should just look from the config folder due to
          // framework
          int configIdx = saveFilePath.indexOf(CONFIG_URL_PATTERN) + CONFIG_URL_PATTERN.length();
          String relativeToConfigPath = saveFilePath.substring(configIdx);

          File saveFile = ODKFileUtils.asConfigFile(appName, relativeToConfigPath);

          // Need to create all the underlying directories
          if (!saveFile.getParentFile().exists()) {
            saveFile.getParentFile().mkdirs();
          }

          saveFile.createNewFile();

          is = request.getInputStream();

          os = new BufferedOutputStream(new FileOutputStream(saveFile, false));

          // Write out file to file system
          byte buf[] = new byte[8192];
          int len;
          while ((len = is.read(buf, 0, buf.length)) >= 0) {
            if (len != 0) {
              os.write(buf, 0, len);
            }
            response.setStatus(Response.SC_OK);
          }
        } catch (IOException ioe) {
          ioe.printStackTrace();
          WebLogger.getLogger(appName).printStackTrace(ioe);
          try {
            response.sendError(Response.SC_INTERNAL_SERVER_ERROR, ioe.toString());
          } catch (IOException e) {
            e.printStackTrace();
            WebLogger.getLogger(appName).printStackTrace(e);
          }
        } finally {
          try {
            if (is != null) {
              is.close();
            }
          } catch (IOException e) {
            e.printStackTrace();
            WebLogger.getLogger(appName).printStackTrace(e);
          }

          try {
            if (os != null) {
              os.flush();
              os.close();
            }
          } catch (IOException e) {
            e.printStackTrace();
            WebLogger.getLogger(appName).printStackTrace(e);
          }
        }

        asyncContext.complete();
      }
    });
  }

}
