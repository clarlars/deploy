package org.opendatakit.webservice.rest.fileio;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.opendatakit.webservice.configuration.OdkUserContext;

@WebServlet(urlPatterns = "/OdkFilesIf/*", asyncSupported = true)
public class UploadFileServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  static final String TAG = "UploadFileServlet";

  /**
   * @see HttpServlet#HttpServlet()
   */
  public UploadFileServlet() {
    super();
  }

  /**
   * Upload file
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    
	// TODO: deal with "default" appName
    OdkUserContext.establishOdkUserContext(request, OdkUserContext.DEFAULT_APP_NAME);
    
    AsyncContext asyncCtxt = request.startAsync();
    
    UploadFileAsyncListener action = new UploadFileAsyncListener(asyncCtxt);
    
    asyncCtxt.addListener(action);
    asyncCtxt.setTimeout(90000L);
  }
}
