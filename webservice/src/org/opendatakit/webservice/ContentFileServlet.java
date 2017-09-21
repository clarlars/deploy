package org.opendatakit.webservice;

import java.io.IOException;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// TODO: async support
@WebServlet(urlPatterns = "/app/opendatakit/*", asyncSupported = true)
public class ContentFileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static final String TAG = "ContentFileServlet";
    String appName = "default";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ContentFileServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	  
     AsyncContext asyncCtx = request.startAsync();
     
     String requestUrl = request.getRequestURL().toString();
     int idx = requestUrl.indexOf("/app/opendatakit/");
     String appNameUrlPrefix = requestUrl.substring(0,idx) + "/app/opendatakit/"; 

     ContentFileAsyncListener action = new ContentFileAsyncListener(asyncCtx, appName, appNameUrlPrefix);

     asyncCtx.addListener(action);
     asyncCtx.setTimeout(90000L);
	}

}
