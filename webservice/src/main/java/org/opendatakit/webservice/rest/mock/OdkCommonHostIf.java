package org.opendatakit.webservice.rest.mock;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.Response;
import org.opendatakit.consts.CharsetConsts;
import org.opendatakit.database.RoleConsts;
import org.opendatakit.logging.WebLogger;
import org.opendatakit.properties.CommonToolProperties;
import org.opendatakit.properties.PropertiesSingleton;
import org.opendatakit.services.utilities.ODKServicesPropertyUtils;
import org.opendatakit.utilities.ODKFileUtils;
import org.opendatakit.webservice.configuration.OdkTool;
import org.opendatakit.webservice.configuration.OdkUserContext;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import android.content.Context;

/**
 * Mock implementation of odkCommonIf injection to get 
 * appName, user identity, and roles.
 * 
 * Allows us to vend a static odkCommon.js and dynamically 
 * report different user identities.
 * 
 * @author mitchellsundt@gmail.com
 */
@WebServlet(urlPatterns = "/OdkCommonHostIf/*", asyncSupported = false)
public class OdkCommonHostIf extends HttpServlet {
  private static final long serialVersionUID = 1L;
  static final String TAG = "OdkCommonHostIf";
  static final String END_KNOWN_PATH_PART = "/OdkCommonHostIf/";

  // request types ('*' suffix of urlPattern)
  static final String COMMON = "common";
  static final String PROPERTY = "property";

  /**
   * @see HttpServlet#HttpServlet()
   */
  public OdkCommonHostIf() {
    super();
    // TODO Auto-generated constructor stub
  }
  
  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    
    ObjectMapper mapper = new ObjectMapper();
    TypeReference ref = new TypeReference<HashMap<String, Object>>() {};

    HashMap<String, Object> requestParams = null;
    try {
      requestParams = mapper.readValue(request.getInputStream(), ref);
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    String appName = (String) requestParams.get("appName");
    String activeUser = (String) requestParams.get("activeUser");
    String serverURL = (String) requestParams.get("serverUrl");
    String useInsecureAuth = (String) requestParams.get("useInsecureAuth");
    String password = (String) requestParams.get("password");
    
    OdkUserContext odkCtxt;
    
    PropertiesSingleton props;
    
    if (appName != null) {
      odkCtxt = OdkUserContext.establishOdkUserContext(request, appName);
      props = CommonToolProperties.get(odkCtxt.getContext(), appName);
    } else {
      odkCtxt = OdkUserContext.establishOdkUserContext(request, OdkUserContext.DEFAULT_APP_NAME);
      props = CommonToolProperties.get(odkCtxt.getContext(), OdkUserContext.DEFAULT_APP_NAME);
    }

    Map<String,String> properties = new HashMap<String,String>();
    
    if ( activeUser != null  && !activeUser.equals(CommonToolProperties.ANONYMOUS_USER)) {
      properties.put(CommonToolProperties.KEY_USERNAME, activeUser);
      properties.put(CommonToolProperties.KEY_AUTHENTICATION_TYPE, props.CREDENTIAL_TYPE_USERNAME_PASSWORD);
    } else {
      // We assume you are anonymous
      // TODO: Should we worry about putting gmail back in?
      properties.put(CommonToolProperties.KEY_USERNAME, CommonToolProperties.ANONYMOUS_USER);
      properties.put(CommonToolProperties.KEY_AUTHENTICATION_TYPE, props.CREDENTIAL_TYPE_NONE);
    }
    
    if (serverURL != null) {
      properties.put(CommonToolProperties.KEY_SYNC_SERVER_URL, serverURL);
    }
    
    if (useInsecureAuth != null) {
      properties.put(CommonToolProperties.KEY_ALLOW_NON_SECURE_AUTHENTICATION, useInsecureAuth);
    }

    if (password != null) {
      properties.put(CommonToolProperties.KEY_PASSWORD, password);
    }

    props.setProperties(properties);
    
    response.setContentType("application/json");
    response.setCharacterEncoding("utf-8");
    response.setStatus(Response.SC_OK);
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
   
    
    // We should figure this out from the url?? 
    // TODO: Deal with "default"
    OdkUserContext odkUserContext = OdkUserContext.establishOdkUserContext(request, OdkUserContext.DEFAULT_APP_NAME);

    ObjectMapper mapper = new ObjectMapper();

    String requestUrl = request.getRequestURL().toString();
    int idx = requestUrl.indexOf(END_KNOWN_PATH_PART);
    if (idx == -1) {
      response.sendError(Response.SC_BAD_REQUEST);
      return;
    }

    // and run the initialization logic
    final Context appContext = odkUserContext.getContext();

    String responseString;
    String requestPart = requestUrl.substring(idx + END_KNOWN_PATH_PART.length());
    String[] parts = requestPart.split("/");
    String requestName = parts[0];
    PropertiesSingleton props = odkUserContext.getPropertiesSingleton();
    if (COMMON.equals(requestPart)) {
      Map<String,String> responseMap = new HashMap<String,String>();
      responseMap.put("appName", odkUserContext.getAppName());
      // TODO: should we use ODKServicesPropertyUtils.getActiveUser()???
      // Maybe both should be returned??
      responseMap.put("activeUser", props.getProperty(CommonToolProperties.KEY_USERNAME));
      responseMap.put("rolesList", props.getProperty(CommonToolProperties.KEY_ROLES_LIST));
      responseMap.put("defaultGroup", props.getProperty(CommonToolProperties.KEY_DEFAULT_GROUP));
      responseMap.put("usersList", props.getProperty(CommonToolProperties.KEY_USERS_LIST));
      responseMap.put("serverUrl", props.getProperty(CommonToolProperties.KEY_SYNC_SERVER_URL));
      responseMap.put("useInsecureAuth", props.getProperty(CommonToolProperties.KEY_ALLOW_NON_SECURE_AUTHENTICATION));
      responseString = ODKFileUtils.mapper.writeValueAsString(responseMap);
    } else if (PROPERTY.equals(requestName)) {
      if ( parts.length != 2) {
        response.sendError(Response.SC_BAD_REQUEST);
        return;
      }
      responseString = props.getProperty(parts[1]);
    } else {
      response.sendError(Response.SC_NOT_FOUND);
      return;
    }

    try {
      response.setContentType("application/json");
      response.setCharacterEncoding("utf-8");
      response.getOutputStream().write(responseString.getBytes(CharsetConsts.UTF_8));
      response.setStatus(Response.SC_OK);
    } catch (IOException e) {
      WebLogger.getLogger(odkUserContext.getAppName()).e(TAG, e.toString());
      WebLogger.getLogger(odkUserContext.getAppName()).printStackTrace(e);
      response.sendError(Response.SC_INTERNAL_SERVER_ERROR, e.toString());
    }
  }

}
