package org.opendatakit.webservice.rest.configure;

import java.io.IOException;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Rest API to direct the server to destroy the data tree within the scratch
 * space for this session and recreate it by running the initialization logic.
 * 
 * @author mitchellsundt@gmail.com
 *
 */
@WebServlet(urlPatterns = "/OdkReInitialize", asyncSupported = true)
public class ReInitializeServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  static final String TAG = "ReInitializeServlet";
  String appName = "default";

  /**
   * @see HttpServlet#HttpServlet()
   */
  public ReInitializeServlet() {
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

    ReInitializeAsyncListener action = new ReInitializeAsyncListener(asyncCtx, appName);

    asyncCtx.addListener(action);
    asyncCtx.setTimeout(240000L);
  }

}
