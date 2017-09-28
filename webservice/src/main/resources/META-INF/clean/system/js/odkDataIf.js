define([],function() {
/* global odkCommon */
'use strict';
if ( window.odkDataIf === null || window.odkDataIf === undefined ) {
  window.odkDataIf = {
    _responseQueue: [],

    _queueResponse: function(resp) {
        this._responseQueue.push(resp);
        setTimeout( function() { window.odkData.responseAvailable(); }, 10 );
    },

    /**
     * Remove the first queued response and return it.
     */
    getResponseJSON: function() {
        if ( this._responseQueue.length !== 0 ) {
            var result = this._responseQueue[0];
            this._responseQueue.shift();
            return result;
        }
        return null;
    },

	_execute: function( json ) {
		var that = this;
		var xhr = new XMLHttpRequest();
		xhr.open('POST', '/OdkDataHostIf', true);
		xhr.responseType = 'text';
		xhr.setRequestHeader('X-OpenDataKit-Version','2.0');
		xhr.setRequestHeader('Content-Type','application/json;charset=utf-8');
		xhr.setRequestHeader('Accept','application/json;charset=utf-8');
		xhr.onload = function() {
			if ( this.status === 200 ) {
				that._queueResponse(this.responseText);
			} else {
				that._queueResponse( { error: "unable to access server or returned something other than 200" } );
			}
		};
		xhr.send(JSON.stringify(json));
	},
	
    getViewData : function (_callbackId, limit, offset) {
	    var that = this;
		var json = { request: "getViewData",
					 url: window.location,
		             callbackId: _callbackId,
					 limit: limit,
					 offset: offset };
		that._execute( json );
    },

    getRoles: function(_callbackId) {
        var that = this;
		var json = { request: "getRoles",
					 url: window.location,
		             callbackId: _callbackId };
		that._execute( json );
    },

    getDefaultGroup: function(_callbackId) {
        var that = this;
		var json = { request: "getDefaultGroup",
					 url: window.location,
		             callbackId: _callbackId };
		that._execute( json );
    },

    getUsers: function(_callbackId) {
        var that = this;
		var json = { request: "getUsers",
					 url: window.location,
		             callbackId: _callbackId };
		that._execute( json );
    },

    getAllTableIds: function(_callbackId) {
        var that = this;
		var json = { request: "getAllTableIds",
					 url: window.location,
		             callbackId: _callbackId };
		that._execute( json );
    },

    query: function(tableId, whereClause, sqlBindParams, groupBy, having,
            orderByElementKey, orderByDirection, limit, offset, includeKVS, _callbackId) {
        var that = this;
		var json = { request: "query",
					 url: window.location,
		             callbackId: _callbackId,
					 tableId: tableId,
					 whereClause: whereClause,
					 sqlBindParams: sqlBindParams,
					 groupBy: groupBy,
					 having: having,
					 orderByElementKey: orderByElementKey,
					 orderByDirection: orderByDirection,
					 limit: limit,
					 offset: offset,
					 includeKVS: includeKVS };
		that._execute( json );
    },

    arbitraryQuery: function(tableId, sqlCommand, sqlBindParams, limit, offset, _callbackId) {
        var that = this;
		var json = { request: "arbitraryQuery",
					 url: window.location,
		             callbackId: _callbackId,
					 tableId: tableId,
					 sqlCommand: sqlCommand,
					 sqlBindParams: sqlBindParams,
					 limit: limit,
					 offset: offset };
		that._execute( json );
    },

    getRows: function(tableId, rowId, _callbackId) {
        var that = this;
		var json = { request: "getRows",
					 url: window.location,
		             callbackId: _callbackId,
					 tableId: tableId,
					 rowId: rowId };
		that._execute( json );
    },

    getMostRecentRow: function(tableId, rowId, _callbackId) {
        var that = this;
		var json = { request: "getMostRecentRow",
					 url: window.location,
		             callbackId: _callbackId,
					 tableId: tableId,
					 rowId: rowId };
		that._execute( json );
    },

    changeAccessFilterOfRow: function(tableId, defaultAccess, rowOwner, groupReadOnly, groupModify,
        groupPrivileged, rowId, _callbackId) {
        var that = this;
		var json = { request: "changeAccessFilterOfRow",
					 url: window.location,
		             callbackId: _callbackId,
					 tableId: tableId,
					 defaultAccess: defaultAccess,
					 rowOwner: rowOwner,
					 groupReadOnly: groupReadOnly,
					 groupModify: groupModify,
					 groupPrivileged: groupPrivileged,
					 rowId: rowId };
		that._execute( json );
    },

    updateRow: function(tableId, stringifiedJSON, rowId, _callbackId) {
        var that = this;
		var json = { request: "updateRow",
					 url: window.location,
		             callbackId: _callbackId,
					 tableId: tableId,
					 stringifiedJSON: stringifiedJSON,
					 rowId: rowId };
		that._execute( json );
    },

    deleteRow: function(tableId, stringifiedJSON, rowId, _callbackId) {
        var that = this;
		var json = { request: "deleteRow",
					 url: window.location,
		             callbackId: _callbackId,
					 tableId: tableId,
					 stringifiedJSON: stringifiedJSON,
					 rowId: rowId };
		that._execute( json );
    },

    addRow: function(tableId, stringifiedJSON, rowId, _callbackId) {
        var that = this;
		var json = { request: "addRow",
					 url: window.location,
		             callbackId: _callbackId,
					 tableId: tableId,
					 stringifiedJSON: stringifiedJSON,
					 rowId: rowId };
		that._execute( json );
    },

    addCheckpoint: function(tableId, stringifiedJSON, rowId, _callbackId) {
        var that = this;
		var json = { request: "addCheckpoint",
					 url: window.location,
		             callbackId: _callbackId,
					 tableId: tableId,
					 stringifiedJSON: stringifiedJSON,
					 rowId: rowId };
		that._execute( json );
    },

    saveCheckpointAsIncomplete: function(tableId, stringifiedJSON, rowId, _callbackId) {
        var that = this;
		var json = { request: "saveCheckpointAsIncomplete",
					 url: window.location,
		             callbackId: _callbackId,
					 tableId: tableId,
					 stringifiedJSON: stringifiedJSON,
					 rowId: rowId };
		that._execute( json );
    },

    saveCheckpointAsComplete: function(tableId, stringifiedJSON, rowId, _callbackId) {
        var that = this;
		var json = { request: "saveCheckpointAsComplete",
					 url: window.location,
		             callbackId: _callbackId,
					 tableId: tableId,
					 stringifiedJSON: stringifiedJSON,
					 rowId: rowId };
		that._execute( json );
    },

    deleteAllCheckpoints: function(tableId, rowId, _callbackId) {
        var that = this;
		var json = { request: "deleteAllCheckpoints",
					 url: window.location,
		             callbackId: _callbackId,
					 tableId: tableId,
					 rowId: rowId };
		that._execute( json );
    },

    deleteLastCheckpoint: function(tableId, rowId, _callbackId) {
        var that = this;
		var json = { request: "deleteLastCheckpoint",
					 url: window.location,
		             callbackId: _callbackId,
					 tableId: tableId,
					 rowId: rowId };
		that._execute( json );
    }
  };
}
return window.odkDataIf;
});

