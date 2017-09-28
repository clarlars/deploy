package org.opendatakit.webservice.configuration;

/**
 * Enum to track which tool the requesting page represents. This is detected
 * based upon whether Survey's system/index.html is the page that is loaded.
 * 
 * This enum is primarily used to determine whether or not the ODK Tables
 * extended metadata should be returned on the odkData responses.
 * 
 * @author mitchellsundt@gmail.com
 *
 */
public enum OdkTool {
  SURVEY, TABLES
}