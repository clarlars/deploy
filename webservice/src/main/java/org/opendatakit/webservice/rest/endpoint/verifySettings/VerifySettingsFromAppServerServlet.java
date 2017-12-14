package org.opendatakit.webservice.rest.endpoint.verifySettings;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.AsyncContext;

import org.json.JSONException;
import org.json.JSONObject;
import org.opendatakit.httpclientandroidlib.protocol.HTTP;
import org.opendatakit.properties.CommonToolProperties;
import org.opendatakit.properties.PropertiesSingleton;
import org.opendatakit.utilities.ODKFileUtils;
import org.opendatakit.webservice.configuration.OdkUserContext;

import com.fasterxml.jackson.core.type.TypeReference;

/**
 * Verify server settings to the cloud endpoint
 */
@WebServlet(urlPatterns = "/OdkVerifySettingsFromAppServer", asyncSupported = true)
public class VerifySettingsFromAppServerServlet extends HttpServlet{
  private static final long serialVersionUID = 1L;
  static final String TAG = "VerifySettingsFromAppServerServlet";
  static final String END_KNOWN_PATH_PART = "/OdkVerifySettingsFromAppServer/";
  
  static final String LOGIN = "login";
  
  public VerifySettingsFromAppServerServlet() {
    super();
  }
  
//  protected void doGet(HttpServletRequest request, HttpServletResponse response)
//    throws ServletException, IOException {
//    
//    OdkUserContext.establishOdkUserContext(request);
//    
//    AsyncContext asyncContext = request.startAsync();
//    
//    VerifySettingsFromAppServerAsyncListener action = new VerifySettingsFromAppServerAsyncListener(asyncContext);
//    
//    asyncContext.addListener(action);
//    asyncContext.setTimeout(240000L);
//  }
  
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    
    // TBD: Should we worry about the scratch directory??
    // TBD: Deal with this "default" appName
    OdkUserContext.establishOdkUserContext(request, "default");
    
    AsyncContext asyncContext = request.startAsync();
    
    VerifySettingsFromAppServerAsyncListener action = new VerifySettingsFromAppServerAsyncListener(asyncContext);
    
    asyncContext.addListener(action);
    asyncContext.setTimeout(240000L);
  }
}
