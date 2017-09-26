package org.opendatakit.webservice.bridge;

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
import org.opendatakit.services.utilities.ODKServicesPropertyUtils;
import org.opendatakit.survey.logic.SurveyDataExecutorProcessor;
import org.opendatakit.tables.activities.IOdkTablesActivity;
import org.opendatakit.tables.views.webkits.TableDataExecutorProcessor;
import org.opendatakit.utilities.ODKFileUtils;
import org.opendatakit.views.ExecutorContext;
import org.opendatakit.views.ExecutorProcessor;
import org.opendatakit.views.ViewDataQueryParams;
import org.opendatakit.webservice.configuration.OdkTool;
import org.opendatakit.webservice.configuration.OdkUserContext;

import android.content.Context;
import android.os.Bundle;

/**
 * Class that bridges the gulf between the Android activity and its management
 * of the ExecutorContext and the management of that context in the website.
 * 
 * @author mitchellsundt@gmail.com
 *
 */
public class OdkDataActivityImpl
    implements IOdkAsyncDataActivity, IOdkTablesActivity, AsyncListener {
  private static final String TAG = "OdkDataActivityImpl";
  final OdkTool tool;
  final OdkUserContext odkUserContext;
  final ViewDataQueryParams params;
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
      String responseJSON = "{\"error\":\"Timed out\"}";
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

  public OdkDataActivityImpl(AsyncContext asyncContext, OdkTool tool, ViewDataQueryParams params) {
    this.asyncContext = asyncContext;
    this.odkUserContext = OdkUserContext.getOdkUserContext(asyncContext);
    this.tool = tool;
    this.params = params;
    ODKFileUtils.assertDirectoryStructure(odkUserContext.getAppName());
    AndroidConnectFactory.configure();
    impl = new UserDbInterfaceImpl(new OdkDatabaseServiceImpl(odkUserContext.getContext()));
  }

  @Override
  public void signalResponseAvailable(String responseJSON, String fragmentID) {
    WebLogger.getLogger(odkUserContext.getAppName()).i(TAG, responseJSON);

    HttpServletResponse response = null;
    try {
      response = (HttpServletResponse) asyncContext.getResponse();
    } catch (Exception e) {
      WebLogger.getLogger(odkUserContext.getAppName()).i(TAG, "async context is no longer valid");
      return;
    }

    try {
      response.setContentType("application/json");
      response.setCharacterEncoding("utf-8");
      response.getOutputStream().write(responseJSON.getBytes(CharsetConsts.UTF_8));
      response.setStatus(Response.SC_OK);
    } catch (IOException e) {
      WebLogger.getLogger(odkUserContext.getAppName()).e(TAG, responseJSON);
      WebLogger.getLogger(odkUserContext.getAppName()).printStackTrace(e);
      try {
        response.sendError(Response.SC_INTERNAL_SERVER_ERROR, e.toString());
      } catch (IOException ex) {
        WebLogger.getLogger(odkUserContext.getAppName()).printStackTrace(ex);
      }
    }
    // complete the processing
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
    if (tool == OdkTool.SURVEY) {
      return new SurveyDataExecutorProcessor(context);
    } else {
      return new TableDataExecutorProcessor(context, this);
    }
  }

  @Override
  public void registerDatabaseConnectionBackgroundListener(DatabaseConnectionListener listener) {
    this.listener = listener;
  }

  @Override
  public UserDbInterface getDatabase() {
    return impl;
  }

  @Override
  public String getAppName() {
    return odkUserContext.getAppName();
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
    return ODKServicesPropertyUtils.getActiveUser(odkUserContext.getPropertiesSingleton());
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
    return odkUserContext.getPropertiesSingleton().getProperty(propertyId);
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
    return odkUserContext.getContext();
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