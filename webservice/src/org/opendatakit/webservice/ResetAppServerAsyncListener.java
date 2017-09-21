package org.opendatakit.webservice;

import java.io.IOException;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.Response;
import org.opendatakit.consts.CharsetConsts;
import org.opendatakit.database.service.UserDbInterface;
import org.opendatakit.database.service.UserDbInterfaceImpl;
import org.opendatakit.logging.WebLogger;
import org.opendatakit.services.database.AndroidConnectFactory;
import org.opendatakit.services.database.service.OdkDatabaseServiceImpl;

import android.content.Context;

public class ResetAppServerAsyncListener implements AsyncListener {
	private static final String TAG = "ResetAppServerAsyncListener";

	  private final String appName;
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
	      String responseJSON ="{\"error\":\"Timed out\"}";
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
	  
	  ResetAppServerAsyncListener(AsyncContext  asyncContext, String appName) {
	    this.asyncContext = asyncContext;
	    this.appName = appName;

	    Runnable r = new Runnable() {

			@Override
			public void run() {
				HttpServletRequest request = null;
			    HttpServletResponse response = null;
			    try {
			      response = (HttpServletResponse) asyncContext.getResponse();
			      request = (HttpServletRequest) asyncContext.getRequest();
			    } catch ( Exception e ) {
			      WebLogger.getLogger(appName).i(TAG,  "async context is no longer valid");
			      return;
			    }
			    
			      final Context appContext = new Context();
			      
			      AndroidConnectFactory.configure();
			      final UserDbInterface impl = new UserDbInterfaceImpl(new OdkDatabaseServiceImpl(appContext));

			      // Sync goes here

			    try {
			      String responseJSON ="{\"success\":\"Reset completed\"}";
			      response.setContentType("application/json");
			      response.setCharacterEncoding("utf-8");
			      response.getOutputStream().write(responseJSON.getBytes(CharsetConsts.UTF_8));
			      response.setStatus(Response.SC_OK);
			    } catch (IOException e) {
			      WebLogger.getLogger(appName).e(TAG, "ResetAppServerAsyncListener");
			      WebLogger.getLogger(appName).printStackTrace(e);
			      try {
			        response.sendError(Response.SC_INTERNAL_SERVER_ERROR, e.toString());
			      } catch (IOException ex) {
			        WebLogger.getLogger(appName).printStackTrace(ex);
			      }
			    }
			    //complete the processing
			    asyncContext.complete();
			}};
			
			asyncContext.start(r);
	  }

	  public String getAppName() {
	    return appName;
	  }

	  public String getActiveUser() {
	    // TODO Auto-generated method stub
	    return null;
	  }
}
