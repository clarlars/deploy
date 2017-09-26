package org.opendatakit.webservice.rest.endpoint.reset;

import java.io.IOException;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.opendatakit.webservice.configuration.OdkUserContext;

/**
 * Performs a Reset of the application server.
 * 
 * @author mitchellsundt@gmail.com
 *
 */
@WebServlet(urlPatterns = "/OdkResetAppServer", asyncSupported = true)
public class ResetAppServerServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  static final String TAG = "ResetAppServerServlet";

  /**
   * @see HttpServlet#HttpServlet()
   */
  public ResetAppServerServlet() {
    super();
    // TODO Auto-generated constructor stub
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    OdkUserContext.establishOdkUserContext(request);

    AsyncContext asyncCtx = request.startAsync();

    ResetAppServerAsyncListener action = new ResetAppServerAsyncListener(asyncCtx);

    asyncCtx.addListener(action);
    asyncCtx.setTimeout(240000L);
  }

}
