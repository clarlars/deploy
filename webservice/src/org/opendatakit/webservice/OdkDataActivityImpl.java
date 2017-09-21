package org.opendatakit.webservice;

import java.io.IOException;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.Response;
import org.json.JSONObject;
import org.opendatakit.activities.IOdkAsyncDataActivity;
import org.opendatakit.consts.CharsetConsts;
import org.opendatakit.database.service.UserDbInterface;
import org.opendatakit.database.service.UserDbInterfaceImpl;
import org.opendatakit.listener.DatabaseConnectionListener;
import org.opendatakit.logging.WebLogger;
import org.opendatakit.services.database.AndroidConnectFactory;
import org.opendatakit.services.database.service.OdkDatabaseServiceImpl;
import org.opendatakit.survey.logic.SurveyDataExecutorProcessor;
import org.opendatakit.tables.activities.IOdkTablesActivity;
import org.opendatakit.tables.views.webkits.TableDataExecutorProcessor;
import org.opendatakit.utilities.ODKFileUtils;
import org.opendatakit.views.ExecutorContext;
import org.opendatakit.views.ExecutorProcessor;
import org.opendatakit.views.ViewDataQueryParams;

import android.content.Context;
import android.os.Bundle;

class OdkDataActivityImpl implements IOdkAsyncDataActivity, IOdkTablesActivity, AsyncListener {

  final OdkTool tool;
  String appName = "default";
  final ViewDataQueryParams params;
  Context context = new Context();
  UserDbInterface impl;
  DatabaseConnectionListener listener = null;
  AsyncContext asyncContext = null;

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
  
  OdkDataActivityImpl(AsyncContext  asyncContext, OdkTool tool, ViewDataQueryParams params) {
    this.asyncContext = asyncContext;
    this.tool = tool;
    this.params = params;
    ODKFileUtils.assertDirectoryStructure(appName);
    AndroidConnectFactory.configure();
    impl = new UserDbInterfaceImpl(new OdkDatabaseServiceImpl(context));
  }

  @Override
  public void signalResponseAvailable(String responseJSON, String fragmentID) {
    WebLogger.getLogger(appName).i(OdkDataHostIf.TAG,  responseJSON);
    
    HttpServletResponse response = null;
    try {
      response = (HttpServletResponse) asyncContext.getResponse();
    } catch ( Exception e ) {
      WebLogger.getLogger(appName).i(OdkDataHostIf.TAG,  "async context is no longer valid");
      return;
    }
    
    try {
      response.setContentType("application/json");
      response.setCharacterEncoding("utf-8");
      response.getOutputStream().write(responseJSON.getBytes(CharsetConsts.UTF_8));
      response.setStatus(Response.SC_OK);
    } catch (IOException e) {
      WebLogger.getLogger(appName).e(OdkDataHostIf.TAG,  responseJSON);
      WebLogger.getLogger(appName).printStackTrace(e);
      try {
        response.sendError(Response.SC_INTERNAL_SERVER_ERROR, e.toString());
      } catch (IOException ex) {
        WebLogger.getLogger(appName).printStackTrace(ex);
      }
    }
    //complete the processing
    asyncContext.complete();
  }

  @Override
  public String getResponseJSON(String fragmentID) {
    // TODO Auto-generated method stub
    return null;
  }
  
  public void start(ExecutorProcessor work) {
    asyncContext.start(work);
    // getExecutorContextWorker().execute(work);
  }
  
  @Override
  public ExecutorProcessor newExecutorProcessor(ExecutorContext context) {
    if ( tool == OdkTool.SURVEY ) {
      return new SurveyDataExecutorProcessor(context);
    } else {
      return new TableDataExecutorProcessor(context, this);
    }
  }

  @Override
  public void registerDatabaseConnectionBackgroundListener(
      DatabaseConnectionListener listener) {
    this.listener = listener; 
  }

  @Override
  public UserDbInterface getDatabase() {
    return impl;
  }

  @Override
  public String getAppName() {
    return appName;
  }

  @Override
  public Bundle getIntentExtras() {
    return null;
  }

  @Override
  public ViewDataQueryParams getViewQueryParams(String viewID) throws IllegalArgumentException {
    return params;
  }

  @Override
  public String getActiveUser() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getTableId() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getInstanceId() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getProperty(String propertyId) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getWebViewContentUri() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void setSessionVariable(String elementPath, String jsonValue) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public String getSessionVariable(String elementPath) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String doAction(String dispatchStructAsJSONstring, String action,
      JSONObject intentObject) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void queueActionOutcome(String outcome) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void queueUrlChange(String hash) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public String viewFirstQueuedAction() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void removeFirstQueuedAction() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void runOnUiThread(Runnable r) {
    // TODO: may need to run as an executor?
    r.run();
  }

  @Override
  public Context getApplicationContext() {
    return context;
  }

  @Override
  public void initializationCompleted() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public String getUrlBaseLocation(boolean ifChanged, String fragmentID) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Integer getIndexOfSelectedItem() {
    // TODO Auto-generated method stub
    return null;
  }      
}