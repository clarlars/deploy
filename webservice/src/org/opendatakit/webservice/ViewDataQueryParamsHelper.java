package org.opendatakit.webservice;

import java.util.List;
import java.util.Map;

import org.opendatakit.consts.IntentConsts;
import org.opendatakit.database.queries.BindArgs;
import org.opendatakit.tables.utils.Constants;
import org.opendatakit.views.ViewDataQueryParams;

public class ViewDataQueryParamsHelper {

  public static ViewDataQueryParams readQueryFromIntentExtrasSubset(Map<String,Object> extrasSubset) {

    String tableId = (extrasSubset != null && extrasSubset.containsKey(IntentConsts.INTENT_KEY_TABLE_ID)) ?
        ((String) extrasSubset.get(IntentConsts.INTENT_KEY_TABLE_ID)) : null;
    String rowId = (extrasSubset != null && extrasSubset.containsKey(IntentConsts.INTENT_KEY_INSTANCE_ID)) ?
        ((String) extrasSubset.get(IntentConsts.INTENT_KEY_INSTANCE_ID)) : null;
    String whereClause = (extrasSubset != null && extrasSubset.containsKey(Constants.IntentKeys.SQL_WHERE)) ?
        ((String) extrasSubset.get(Constants.IntentKeys.SQL_WHERE)) : null;
    String selArgsListJSON =  (extrasSubset != null && extrasSubset.containsKey(Constants.IntentKeys
        .SQL_SELECTION_ARGS)) ? ((String) extrasSubset.get(Constants.IntentKeys
        .SQL_SELECTION_ARGS)) : null;
    BindArgs selArgs = new BindArgs(selArgsListJSON);
    List<String> groupByList = (extrasSubset != null && extrasSubset.containsKey(Constants.IntentKeys
        .SQL_GROUP_BY_ARGS)) ? ((List<String>) extrasSubset.get(Constants.IntentKeys
        .SQL_GROUP_BY_ARGS)) : null;
    String[] groupBy = (groupByList == null) ? null : groupByList.toArray(new String[groupByList.size()]);
    String having = (extrasSubset != null && extrasSubset.containsKey(Constants.IntentKeys.SQL_HAVING)) ?
        ((String) extrasSubset.get(Constants.IntentKeys.SQL_HAVING)) : null;
    String orderByElemKey = (extrasSubset != null && extrasSubset.containsKey(Constants.IntentKeys
        .SQL_ORDER_BY_ELEMENT_KEY)) ? ((String) extrasSubset.get(Constants.IntentKeys
        .SQL_ORDER_BY_ELEMENT_KEY)) : null;
    String orderByDir = (extrasSubset != null && extrasSubset.containsKey(Constants.IntentKeys
        .SQL_ORDER_BY_DIRECTION)) ? ((String) extrasSubset.get(Constants.IntentKeys
        .SQL_ORDER_BY_DIRECTION)) : null;

    ViewDataQueryParams queryParams = new ViewDataQueryParams(tableId, rowId, whereClause,
        selArgs, groupBy, having, orderByElemKey, orderByDir);
    return queryParams;
  }

}
