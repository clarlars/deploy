package org.opendatakit.webservice.rest.configure;

import java.io.IOException;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.opendatakit.webservice.configuration.OdkUserContext;

/**
 * Rest API to direct the server to destroy the scratch space for this session
 * and recreate it from the clean copy under META-INF. Then run the initialize
 * logic on this clean workspace.
 * 
 * @author mitchellsundt@gmail.com
 *
 */
@WebServlet(urlPatterns = "/OdkClean", asyncSupported = true)
public class CleanServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  static final String TAG = "CleanServlet";

  /**
   * @see HttpServlet#HttpServlet()
   */
  public CleanServlet() {
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

    CleanAsyncListener action = new CleanAsyncListener(asyncCtx);

    asyncCtx.addListener(action);
    asyncCtx.setTimeout(240000L);
  }

}
