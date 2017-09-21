/**
 * This is the file that will be creating the list view.
 */
/* global $, odkTables, odkData, odkCommon */
'use strict';

// Use chunked list view for larger tables: We want to chunk the displays so
// that there is less load time.
var crumbResultSet = {};   
var idxStart = -1;
    
function cbFailure(error) {

    console.log('follow_list: cbFailure failed with error: ' + error);
}
     
           
/**
 * Called when page loads. idxStart is the index of the row that should be
 * displayed at this iteration through the loop.
 */
var resumeFn = function(fidxStart) {

    idxStart = fidxStart;
    console.log('resumeFn called. idxStart: ' + idxStart);
    // The first time through construct any constants you need to refer to
    // and set the click handler on the list elements.
    if (fidxStart === 0) {
        // This add a click handler on the wrapper ul that will handle all of
        // the clicks on its children.
        $('#list').click(function(e) {
            var tableId = crumbResultSet.getTableId();
            // We have set the rowId while as the li id. However, we may have
            // clicked on the li or anything in the li. Thus we need to get
            // the original li, which we'll do with jQuery's closest()
            // method. First, however, we need to wrap up the target
            // element in a jquery object.
            // wrap up the object so we can call closest()
            var jqueryObject = $(e.target);
            // we want the closest thing with class item_space, which we
            // have set up to have the row id
            var containingDiv = jqueryObject.closest('.item_space');
            var rowId = containingDiv.attr('rowId');
            console.log('clicked with rowId: ' + rowId);
            // make sure we retrieved the rowId
            if (rowId !== null && rowId !== undefined) {
                // we'll pass null as the relative path to use the default file
                odkTables.openDetailView(null,
                    tableId,
                    rowId,
                    'config/tables/follow/html/follow_detail.html');
            }
        });

		odkData.getViewData(function(result) {
				crumbResultSet = result;

				// we know this is the first time - so displayGroup argument idxStart === 0.
				displayGroup(0);
		}, cbFailure);
		
    } else {
		displayGroup(fidxStart);
	}
};
            
/**
 * Displays the list view in chunks. The number of list entries per chunk
 * can be modified. The list view is designed so that each row in the table is
 * represented as a list item. If you click a particular list item, it will
 * call the click handler defined in resumeFn. In the example case it opens
 * a detail view on the clicked row.
 */
var displayGroup = function(idxStart) {
    // Number of rows displayed per chunk
    var chunk = 50;
    for (var i = idxStart; i < idxStart + chunk; i++) {
        if (i >= crumbResultSet.getCount()) {
            break;
        }

        // Creates the space for a single element in the list. We add rowId as
        // an attribute so that the click handler set in resumeFn knows which
        // row was clicked.
        var item = $('<li>');
        item.attr('rowId', crumbResultSet.getRowId(i));
        item.attr('class', 'item_space');
        item.text(crumbResultSet.getData(i, 'FOL_B_AnimID'));
                
        
        /* Creates arrow icon (Nothing to edit here) */
        var chevron = $('<img>');
        chevron.attr('src', odkCommon.getFileAsUrl('config/assets/img/little_arrow.png'));
        chevron.attr('class', 'chevron');
        item.append(chevron);

        /*var addrUriRelative = data.getData(i, 'address_image_0.uriFragment');
        var addrSrc = '';
        if (addrUriRelative !== null && addrUriRelative !== "") {
            var addrUriAbsolute = odkCommon.getRowFileAsUrl(data.getTableId(), data.getRowId(i), addrUriRelative);
            addrSrc = addrUriAbsolute;
        }

        var imgDiv = $('<div>');
        var imgItem = $('<img>');
        imgItem.attr('src', addrSrc);
        imgItem.attr('class', 'thumbnail');
        imgDiv.append(imgItem);
        item.append(imgDiv);*/

        // Add any other details in your list item here.

        /**
         * Adds other data/details in item space.
         * Replace COLUMN_NAME with the column whose data you want to display
         * as an extra detail etc. Duplicate the following block of code for
         * different details you want to add. You may replace occurrences of
         * 'field1' with new, specific label that are more meaningful to you
         */
        var field1 = $('<li>');
        field1.attr('class', 'detail');
        var followDate = crumbResultSet.getData(i, 'FOL_date');
        field1.text('Date of follow: ' + followDate);
        item.append(field1);

        /*var field2 = $('<li>');
        field2.attr('class', 'detail');
        field2.text(crumbResultSet.getData(i, 'District') + ' ' +
            crumbResultSet.getData(i, 'Neighborhood'));
        item.append(field2);*/
                
        $('#list').append(item);
    }
    if (i < crumbResultSet.getCount()) {
        setTimeout(resumeFn, 0, i);
    }
};
