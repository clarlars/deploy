package org.opendatakit.webservice.rest.endpoint.sync;

import java.io.IOException;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.opendatakit.webservice.configuration.OdkUserContext;

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

  /**
   * @see HttpServlet#HttpServlet()
   */
  public SyncFromAppServerServlet() {
    super();
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    OdkUserContext.establishOdkUserContext(request);

    AsyncContext asyncCtx = request.startAsync();

    SyncFromAppServerAsyncListener action = new SyncFromAppServerAsyncListener(asyncCtx);

    asyncCtx.addListener(action);
    asyncCtx.setTimeout(240000L);
  }

}
