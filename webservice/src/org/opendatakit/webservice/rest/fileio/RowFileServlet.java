package org.opendatakit.webservice.rest.fileio;

import java.io.IOException;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Retrieve the files under the data tree of this user. The data files are under
 * the /scratch/* space, rather than under the static configuration's
 * /app/opendatakit/* path.
 * 
 * This could enable shared configuration on the server (managed by the site
 * admin), while having separate data spaces for the individual users.
 * 
 * @author mitchellsundt@gmail.com
 *
 */
@WebServlet(urlPatterns = "/scratch/*", asyncSupported = true)
public class RowFileServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  static final String TAG = "RowFileServlet";
  String appName = "default";

  /**
   * @see HttpServlet#HttpServlet()
   */
  public RowFileServlet() {
    super();
    // TODO Auto-generated constructor stub
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    AsyncContext asyncCtx = request.startAsync();

    String requestUrl = request.getRequestURL().toString();
    int idx = requestUrl.indexOf("/app/opendatakit/");
    String appNameUrlPrefix = requestUrl.substring(0, idx) + "/app/opendatakit/";

    RowFileAsyncListener action = new RowFileAsyncListener(asyncCtx, appName, appNameUrlPrefix);

    asyncCtx.addListener(action);
    asyncCtx.setTimeout(90000L);
  }

}
