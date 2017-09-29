package org.opendatakit.webservice.utilities;

import java.util.List;
import java.util.Map;

import org.opendatakit.consts.IntentConsts;
import org.opendatakit.database.queries.ArbitraryQuery;
import org.opendatakit.database.queries.BindArgs;
import org.opendatakit.database.queries.ResumableQuery;
import org.opendatakit.database.queries.SimpleQuery;
import org.opendatakit.database.queries.SingleRowQuery;
import org.opendatakit.database.utilities.QueryUtil;
import org.opendatakit.views.OdkData;

/**
 * Construct a ResumableQuery struct from a JSON request body.
 * This is called to resolve the subList view
 *  
 * @author mitchellsundt@gmail.com
 *
 */
public class ViewDataQueryHelper {

  public static ResumableQuery readQueryFromIntentExtrasSubset(Map<String,Object> extrasSubset) {

    String queryType =  (extrasSubset != null && extrasSubset.containsKey(OdkData.IntentKeys.QUERY_TYPE)) ?
        ((String) extrasSubset.get(OdkData.IntentKeys.QUERY_TYPE)) : null;
    String tableId = (extrasSubset != null && extrasSubset.containsKey(IntentConsts.INTENT_KEY_TABLE_ID)) ?
        ((String) extrasSubset.get(IntentConsts.INTENT_KEY_TABLE_ID)) : null;
    String rowId = (extrasSubset != null && extrasSubset.containsKey(IntentConsts.INTENT_KEY_INSTANCE_ID)) ?
        ((String) extrasSubset.get(IntentConsts.INTENT_KEY_INSTANCE_ID)) : null;
    String sqlCommand = (extrasSubset != null && extrasSubset.containsKey(OdkData.IntentKeys.SQL_COMMAND)) ?
        ((String) extrasSubset.get(OdkData.IntentKeys.SQL_COMMAND)) : null;
    String whereClause = (extrasSubset != null && extrasSubset.containsKey(OdkData.IntentKeys.SQL_WHERE)) ?
        ((String) extrasSubset.get(OdkData.IntentKeys.SQL_WHERE)) : null;
    String selArgsListJSON =  (extrasSubset != null && extrasSubset.containsKey(OdkData.IntentKeys
        .SQL_SELECTION_ARGS)) ? ((String) extrasSubset.get(OdkData.IntentKeys
        .SQL_SELECTION_ARGS)) : null;
    BindArgs selArgs = new BindArgs(selArgsListJSON);
    List<String> groupByList = (extrasSubset != null && extrasSubset.containsKey(OdkData.IntentKeys
        .SQL_GROUP_BY_ARGS)) ? ((List<String>) extrasSubset.get(OdkData.IntentKeys
        .SQL_GROUP_BY_ARGS)) : null;
    String[] groupBy = (groupByList == null) ? null : groupByList.toArray(new String[groupByList.size()]);
    String having = (extrasSubset != null && extrasSubset.containsKey(OdkData.IntentKeys.SQL_HAVING)) ?
        ((String) extrasSubset.get(OdkData.IntentKeys.SQL_HAVING)) : null;
    String orderByElemKey = (extrasSubset != null && extrasSubset.containsKey(OdkData.IntentKeys
        .SQL_ORDER_BY_ELEMENT_KEY)) ? ((String) extrasSubset.get(OdkData.IntentKeys
        .SQL_ORDER_BY_ELEMENT_KEY)) : null;
    String orderByDir = (extrasSubset != null && extrasSubset.containsKey(OdkData.IntentKeys
        .SQL_ORDER_BY_DIRECTION)) ? ((String) extrasSubset.get(OdkData.IntentKeys
        .SQL_ORDER_BY_DIRECTION)) : null;

    // TODO: limit and offset?

    if ( rowId != null ) {
    
      return new SingleRowQuery(tableId, rowId, selArgs, whereClause,
        groupBy, having, 
        QueryUtil.convertStringToArray(orderByElemKey),
        QueryUtil.convertStringToArray(orderByDir), null, null);
      
    } else if (OdkData.QueryTypes.ARBITRARY_QUERY.equals(queryType)) {
      
      return new ArbitraryQuery(tableId, selArgs, sqlCommand, null, null);
      
    } else if ( OdkData.QueryTypes.SIMPLE_QUERY.equals(queryType)) {
     
      return new SimpleQuery(tableId, selArgs, whereClause,
          groupBy, having, 
          QueryUtil.convertStringToArray(orderByElemKey),
          QueryUtil.convertStringToArray(orderByDir), null, null);
    }
    
    throw new IllegalStateException("unsupported query type");
  }
  
  public static void populateQueryFields(Map<String, Object> retVal, ResumableQuery queryParams) {
    if ( queryParams instanceof SingleRowQuery ) {
      populateSingleRowQueryFields(retVal, (SingleRowQuery) queryParams);
      
    } else if ( queryParams instanceof SimpleQuery ) {
      retVal.put(OdkData.IntentKeys.QUERY_TYPE, OdkData.QueryTypes.SIMPLE_QUERY);
      populateSimpleQueryFields(retVal, (SimpleQuery) queryParams);
        
    } else if ( queryParams instanceof ArbitraryQuery ) {
      retVal.put(OdkData.IntentKeys.QUERY_TYPE, OdkData.QueryTypes.ARBITRARY_QUERY);
      populateArbitraryQueryFields(retVal, (ArbitraryQuery) queryParams);
      
    }
  }
  
  private static void populateArbitraryQueryFields(Map<String, Object> retVal, ArbitraryQuery queryParams) {
    retVal.put(OdkData.IntentKeys.TABLE_ID, queryParams.getTableId());
    retVal.put(OdkData.IntentKeys.SQL_COMMAND, queryParams.getSqlCommand());
    retVal.put(OdkData.IntentKeys.SQL_SELECTION_ARGS, queryParams.getSqlBindArgs().asJSON());

  }
  
  private static void populateSingleRowQueryFields(Map<String, Object> retVal, SingleRowQuery queryParams) {
    retVal.put(IntentConsts.INTENT_KEY_INSTANCE_ID, queryParams.getRowId());
    populateSimpleQueryFields(retVal, queryParams);
  }
  
  private static void populateSimpleQueryFields(Map<String, Object> retVal, SimpleQuery queryParams) {
    retVal.put(OdkData.IntentKeys.TABLE_ID, queryParams.getTableId());
    retVal.put(OdkData.IntentKeys.SQL_WHERE, queryParams.getWhereClause());
    retVal.put(OdkData.IntentKeys.SQL_SELECTION_ARGS, queryParams.getSqlBindArgs().asJSON());
    retVal.put(OdkData.IntentKeys.SQL_GROUP_BY_ARGS, queryParams.getGroupByArgs());
    retVal.put(OdkData.IntentKeys.SQL_HAVING, queryParams.getHavingClause());
    String[] orderCols = queryParams.getOrderByColNames();
    retVal.put(OdkData.IntentKeys.SQL_ORDER_BY_ELEMENT_KEY, 
        (orderCols == null || orderCols.length == 0) ? null : orderCols[0]);
    String[] orderDir = queryParams.getOrderByDirections();
    retVal.put(OdkData.IntentKeys.SQL_ORDER_BY_DIRECTION, 
        (orderDir == null || orderDir.length == 0) ? null : orderDir[0]);
  }

}
