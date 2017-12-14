package org.opendatakit.webservice.rest.fileio;

import java.io.IOException;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.opendatakit.webservice.configuration.OdkUserContext;

/**
 * Retrieve the files under the config tree of this user. The config files are
 * under the /app/opendatakit/* path whereas the data files are under the
 * /scratch/* path.
 * 
 * This could enable shared configuration on the server (managed by the site
 * admin), while having separate data spaces for the individual users.
 * 
 * @author mitchellsundt@gmail.com
 *
 */
@WebServlet(urlPatterns = "/app/opendatakit/*", asyncSupported = true)
public class ContentFileServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  static final String TAG = "ContentFileServlet";

  /**
   * @see HttpServlet#HttpServlet()
   */
  public ContentFileServlet() {
    super();
    // TODO Auto-generated constructor stub
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    // TODO: deal with "default" appName
    OdkUserContext.establishOdkUserContext(request, OdkUserContext.DEFAULT_APP_NAME);

    AsyncContext asyncCtx = request.startAsync();

    String requestUrl = request.getRequestURL().toString();
    int idx = requestUrl.indexOf("/app/opendatakit/");
    String appNameUrlPrefix = requestUrl.substring(0, idx) + "/app/opendatakit/";

    ContentFileAsyncListener action = new ContentFileAsyncListener(asyncCtx,
        appNameUrlPrefix);

    asyncCtx.addListener(action);
    asyncCtx.setTimeout(90000L);
  }

}
