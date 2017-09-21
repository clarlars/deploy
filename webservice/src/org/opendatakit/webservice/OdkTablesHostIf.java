package org.opendatakit.webservice;

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
import org.opendatakit.logging.WebLogger;
import org.opendatakit.utilities.ODKFileUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet(urlPatterns = "/OdkTablesHostIf", asyncSupported = true)
public class OdkTablesHostIf extends HttpServlet {
  private static final long serialVersionUID = 1L;
  static final String TAG = "OdkTablesHostIf";
  
  /**
   * @see HttpServlet#HttpServlet()
   */
  public OdkTablesHostIf() {
     super();
     // TODO Auto-generated constructor stub
  }

 /**
  * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
  */
 protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
   
   ObjectMapper mapper = new ObjectMapper();
   TypeReference ref = new TypeReference<HashMap<String,Object>>() { };
  
   HashMap<String, Object> theRequest = null;
   try {
     theRequest = mapper.readValue(request.getInputStream(), ref);
   } catch ( Exception e ) {
     e.printStackTrace();
   }
   
   String requestUrl = request.getRequestURL().toString();
   int idx = requestUrl.indexOf("/OdkTablesHostIf");
   String appNameUrlPrefix = requestUrl.substring(0,idx) + "/app/opendatakit/"; 
   
   String requestAction = (String) theRequest.get("action");
   Map<String,Object> intent = (Map<String,Object>) theRequest.get("intent");
   Map<String,Object> url = (Map<String,Object>) theRequest.get("url");
   String href = (String) url.get("href");
   // get current tool
   OdkTool tool = ( href.contains("system/index.html") ? OdkTool.SURVEY : OdkTool.TABLES );
   

   String appName = "default";
   Map<String,Object> responseJSON = null;
   OdkSurveyIntentParser surveyParser = new OdkSurveyIntentParser(appName, appNameUrlPrefix);
   OdkTablesIntentParser tablesParser = new OdkTablesIntentParser(appName, appNameUrlPrefix);

   responseJSON = surveyParser.getUrl(appName, requestAction, intent);
   if ( responseJSON == null ) {
     responseJSON = tablesParser.getMainActivityUrl(appName, requestAction, intent);
   }
   if ( responseJSON == null ) {
     responseJSON = tablesParser.getTableActivityUrl(appName, requestAction, intent);
   }
   if ( responseJSON == null ) {
     responseJSON = surveyParser.getFrameworkUrl(appName);
   }
   
   try {
     String responseString = ODKFileUtils.mapper.writeValueAsString(responseJSON);
     response.setContentType("application/json");
     response.setCharacterEncoding("utf-8");
     response.getOutputStream().write(responseString.getBytes(CharsetConsts.UTF_8));
     response.setStatus(Response.SC_OK);
   } catch (IOException e) {
     WebLogger.getLogger(appName).e(OdkTablesHostIf.TAG,  e.toString());
     WebLogger.getLogger(appName).printStackTrace(e);
     response.sendError(Response.SC_INTERNAL_SERVER_ERROR, e.toString());
   }
  }

}
