package org.opendatakit.webservice.configuration;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;

import org.opendatakit.database.RoleConsts;
import org.opendatakit.properties.CommonToolProperties;
import org.opendatakit.properties.PropertiesSingleton;

import android.content.Context;

public class OdkUserContext {
  public static final String ATTRIBUTE_NAME = "OdkUserContext";
  public static final String DEFAULT_APP_NAME = "default";

  public String getAppName() {
    return props.getAppName();
  }

  public Context getContext() {
    return context;
  }
  
  public PropertiesSingleton getPropertiesSingleton() {
    return props;
  }
  
  public HttpServletRequest getHttpServletRequest() {
    return request;
  }
  
  private Context context;
  private PropertiesSingleton props;
  private HttpServletRequest request;
  
  public static OdkUserContext establishOdkUserContext(HttpServletRequest request) {
    OdkUserContext ctxt;
    // ctxt = new OdkUserContext(DEFAULT_APP_NAME, null, null, null, null, null);
    
    ctxt = new OdkUserContext(DEFAULT_APP_NAME, CommonToolProperties.ANONYMOUS_USER, "",
        RoleConsts.ANONYMOUS_ROLES_LIST, null, null);
    
    request.getServletContext().setAttribute(ATTRIBUTE_NAME, ctxt);
    ctxt.request = request;
    return ctxt;
  }
  
  public static OdkUserContext establishOdkUserContext(HttpServletRequest request, String appName) {
    OdkUserContext ctxt;
    // ctxt = new OdkUserContext(DEFAULT_APP_NAME, null, null, null, null, null);
    if (appName == null || appName.length() == 0) {
      appName = DEFAULT_APP_NAME;
    }
    
    ctxt = new OdkUserContext(appName);
    
    request.getServletContext().setAttribute(ATTRIBUTE_NAME, ctxt);
    ctxt.request = request;
    return ctxt;
  }
  
  public static OdkUserContext establishOdkUserContext(HttpServletRequest request, 
      String appNameToUse, String usernameToUse, String passwordToUse) {
    OdkUserContext ctxt;
    // ctxt = new OdkUserContext("default", null, null, null, null, null);
    
    String appName  = (appNameToUse == null || appNameToUse.length() == 0) ? DEFAULT_APP_NAME : appNameToUse;
    String username = (usernameToUse == null || usernameToUse.length() == 0) ? 
        CommonToolProperties.ANONYMOUS_USER : usernameToUse;
    String password = (passwordToUse == null || passwordToUse.length() == 0) ? "" : passwordToUse;

    ctxt = new OdkUserContext(appName, username, password, RoleConsts.ANONYMOUS_ROLES_LIST, null, null);
    
    request.getServletContext().setAttribute(ATTRIBUTE_NAME, ctxt);
    ctxt.request = request;
    return ctxt;
  }
  
  public static OdkUserContext getOdkUserContext(AsyncContext asyncContext) {
    OdkUserContext ctxt = (OdkUserContext) asyncContext.getRequest().getServletContext().getAttribute(ATTRIBUTE_NAME);
    if ( ctxt == null ) {
      throw new IllegalStateException("no odkUserContext assigned to servlet context!");
    }
    return ctxt;
  }
  
  // This call assumes that everything is already set and we shouldn't be changing things
  OdkUserContext(String appName) {

    this.context = new Context();
    this.props = CommonToolProperties.get(context, appName);
  }

  
  OdkUserContext(String appName, String username,
      String password, String rolesList,
      String defaultGroup, String usersList) {

    this.context = new Context();
    this.props = CommonToolProperties.get(context, appName);

    Map<String,String> properties = new HashMap<String,String>();
    if ( username == null || username.equals(CommonToolProperties.ANONYMOUS_USER) ) {
      properties.put(CommonToolProperties.KEY_AUTHENTICATION_TYPE, 
          props.CREDENTIAL_TYPE_NONE);
      properties.put(CommonToolProperties.KEY_AUTHENTICATED_USER_ID, "");
      properties.put(CommonToolProperties.KEY_PASSWORD, "");

      properties.put(CommonToolProperties.KEY_ROLES_LIST, RoleConsts.ANONYMOUS_ROLES_LIST);
      properties.put(CommonToolProperties.KEY_DEFAULT_GROUP, "");
      properties.put(CommonToolProperties.KEY_USERS_LIST, "");
    } else {
      properties.put(CommonToolProperties.KEY_AUTHENTICATION_TYPE, 
          props.CREDENTIAL_TYPE_USERNAME_PASSWORD);
      properties.put(CommonToolProperties.KEY_USERNAME, username);
      properties.put(CommonToolProperties.KEY_AUTHENTICATED_USER_ID, "username:" + username);
      properties.put(CommonToolProperties.KEY_PASSWORD, password);
      
      properties.put(CommonToolProperties.KEY_ROLES_LIST, rolesList);
      properties.put(CommonToolProperties.KEY_DEFAULT_GROUP, defaultGroup);
      properties.put(CommonToolProperties.KEY_USERS_LIST, usersList);
    }
    
    props.setProperties(properties);
  }

}
