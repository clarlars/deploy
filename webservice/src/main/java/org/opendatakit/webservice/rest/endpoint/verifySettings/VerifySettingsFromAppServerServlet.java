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
  
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    
    StringBuffer buffer = new StringBuffer();
    String line = null;
    try {
      BufferedReader reader = request.getReader();
      while ((line = reader.readLine()) != null)
        buffer.append(line);
    } catch (Exception e) { 
      e.printStackTrace();
    }
    
    TypeReference<Map<String, String>> reference = new TypeReference<Map<String, String>>(){};
    Map<String, String> verifyParams = ODKFileUtils.mapper.readValue(buffer.toString(), reference);
    String username = verifyParams.get("username");
    String password = verifyParams.get("password");
    String appName = verifyParams.get("appName");
    String serverUrl = verifyParams.get("serverUrl");
    
    // This should be used to make the Deploy more user friendly
    String scratchDir = verifyParams.get("scratchDir");

    // We want to replace this call with the appropriate calls to 
    // get the properties instead
    OdkUserContext.establishOdkUserContext(request, appName, username, password);
    
    AsyncContext asyncContext = request.startAsync();
    
    OdkUserContext odkUserContext = OdkUserContext.getOdkUserContext(asyncContext);
    PropertiesSingleton props = odkUserContext.getPropertiesSingleton();
    
    if (serverUrl != null && serverUrl.length() != 0) {
      HashMap<String, String> serverUrlProperty = new HashMap<String, String>();
      serverUrlProperty.put(CommonToolProperties.KEY_SYNC_SERVER_URL, serverUrl);
      props.setProperties(serverUrlProperty);
    }
    
    VerifySettingsFromAppServerAsyncListener action = new VerifySettingsFromAppServerAsyncListener(asyncContext);
    
    asyncContext.addListener(action);
    asyncContext.setTimeout(240000L);
  }
}
