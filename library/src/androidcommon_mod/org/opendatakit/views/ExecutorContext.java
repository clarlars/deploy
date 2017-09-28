/*
 * Copyright (C) 2015 University of Washington
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.opendatakit.views;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.opendatakit.activities.IOdkAsyncDataActivity;
import org.opendatakit.activities.IOdkDataActivity;
import org.opendatakit.database.data.OrderedColumns;
import org.opendatakit.database.service.DbHandle;
import org.opendatakit.database.service.UserDbInterface;
import org.opendatakit.logging.WebLogger;
import org.opendatakit.utilities.ODKFileUtils;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * @author mitchellsundt@gmail.com
 */
public class ExecutorContext {
  private static final String TAG = "ExecutorContext";
    private static ExecutorContext currentContext = null;

    private static void updateCurrentContext(ExecutorContext ctxt) {
      if ( ctxt == null ) {
        WebLogger.getContextLogger().i(TAG, "*******CLEARING current context");
      } else {
        WebLogger.getLogger(ctxt.getAppName()).i(TAG, "*******switching current context");
      }
      currentContext = ctxt;
    }

    /**
     * The activity containing the web view.
     * Specifically, the API we need to access.
     */
    private final IOdkAsyncDataActivity activity;

  /**
   * The mutex used to guard all of the private data structures:
   *   worker, workQueue, activeConnections, mCacheOrderedDefns
   */
  private final Object mutex = new Object();

   /**
     * Our use of an executor is a bit odd:
     *
     * We need to handle database service disconnections.
     *
     * That requires direct management of the work queue.
     *
     * We still queue actions, but those actions need to pull
     * the request definitions off a work queue that is explicitly
     * managed by the ExecutorContext.
     *
     * The processors effectively record that there is (likely) work
     * to be processed. The work is held here.
     */

    /**
     * workQueue should only be accessed by synchronized methods, as it may be
     * accessed in multiple threads.
     */
    private final LinkedList<ExecutorRequest> workQueue = new LinkedList<ExecutorRequest>();

    private Map<String, DbHandle> activeConnections = new HashMap<String, DbHandle>();
    private Map<String, OrderedColumns> mCachedOrderedDefns = new HashMap<String, OrderedColumns>();

    private ExecutorContext(IOdkAsyncDataActivity fragment) {
        this.activity = fragment;
        updateCurrentContext(this);
    }

    public static synchronized ExecutorContext getContext(IOdkDataActivity fragment) {
      if ( currentContext != null && (currentContext.activity == fragment)) {
        return currentContext;
      } else {
        if ( fragment instanceof IOdkAsyncDataActivity) {
          return new ExecutorContext((IOdkAsyncDataActivity) fragment);
        } else {
          throw new IllegalArgumentException("should be a IOdkAsyncDataActivity type");
        }
      }
    }

  /**
   * if we are not shutting down then queue a request and fire an ExecutorProcessor.
   * @param request
   */
  public void queueRequest(ExecutorRequest request) {
      // processor is most often NOT discarded
      ExecutorProcessor processor = activity.newExecutorProcessor(this);
      synchronized (mutex) {
        workQueue.add(request);
      }
      activity.start(processor);
    }

  /**
   * @return the next ExecutorRequest or null if the queue is empty
   */
  public ExecutorRequest peekRequest() {
      synchronized (mutex) {
        if (workQueue.isEmpty()) {
          return null;
        } else {
          return workQueue.peekFirst();
        }
      }
    }

  /**
   * Remove the current item from the top of the work queue.
   *
   * @param trigger true if we should fire an ExecutorProcessor.
   */
  public void popRequest(boolean trigger) {
    synchronized (mutex) {
      if ( !workQueue.isEmpty() ) {
        workQueue.removeFirst();
      }
    }
  }

  /**
     * shutdown the worker. This is done within the mutex to ensure that the above methods
     * never throw an unexpected state exception.
     */
  void shutdownWorker() {
  }

  /**
   * Get the connection on which this transaction is active.
   *
   * @param transId
   * @return DbHandle
   */
  public DbHandle getActiveConnection(String transId) {
    synchronized (mutex) {
      return activeConnections.get(transId);
    }
  }

  public void registerActiveConnection(String transId, DbHandle dbHandle) {
    boolean alreadyExists = false;
    synchronized (mutex) {
      if ( activeConnections.containsKey(transId) ) {
        alreadyExists = true;
      } else {
        activeConnections.put(transId, dbHandle);
      }
    }
    if ( alreadyExists ) {
      WebLogger.getLogger(currentContext.getAppName()).e(TAG,"transaction id " + transId + " already registered!");
      throw new IllegalArgumentException("transaction id already registered!");
    }
  }

  private String getFirstActiveTransactionId() {
    synchronized (mutex) {
      Set<String> transIds = activeConnections.keySet();
      if ( transIds.isEmpty() ) {
        return null;
      } else {
        return transIds.iterator().next();
      }
    }
  }

  public void removeActiveConnection(String transId) {
    synchronized (mutex) {
      activeConnections.remove(transId);
    }
  }

  public OrderedColumns getOrderedColumns(String tableId) {
    synchronized (mutex) {
      return mCachedOrderedDefns.get(tableId);
    }
  }

  public void putOrderedColumns(String tableId, OrderedColumns orderedColumns) {
    synchronized (mutex) {
      mCachedOrderedDefns.put(tableId, orderedColumns);
    }
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////
  // No direct access to data structures below this point

  /**
   * @return
   */
    public UserDbInterface getDatabase() {
        return activity.getDatabase();
    }

    public String getAppName() {
        return activity.getAppName();
    }

    public void releaseResources(String reason) {
    }

    public void reportError(String callbackJSON, String callerID, String transId,
        String errorMessage) {
      if ( callbackJSON != null ) {
        Map<String, Object> response = new HashMap<String, Object>();
        response.put("callbackJSON", callbackJSON);
        response.put("error", errorMessage);
        if (transId != null) {
          response.put("transId", transId);
        }
        String responseStr = null;
        try {
          responseStr = ODKFileUtils.mapper.writeValueAsString(response);
        } catch (JsonProcessingException e) {
          WebLogger.getLogger(currentContext.getAppName()).e(TAG, "should never have a conversion error");
          WebLogger.getLogger(currentContext.getAppName()).printStackTrace(e);
          throw new IllegalStateException("should never have a conversion error");
        }
        activity.signalResponseAvailable(responseStr, callerID);
      }
    }


    public void reportSuccess(String callbackJSON, String callerID, String transId,
        ArrayList<List<Object>> data, Map<String,Object> metadata) {
        Map<String,Object> response = new HashMap<String,Object>();
        response.put("callbackJSON", callbackJSON);
        if ( transId != null ) {
            response.put("transId", transId);
        }
        if ( data != null ) {
            response.put("data", data);
        }
        if ( metadata != null ) {
            response.put("metadata", metadata);
        }
        String responseStr = null;
        try {
            responseStr = ODKFileUtils.mapper.writeValueAsString(response);
        } catch (JsonProcessingException e) {
          WebLogger.getLogger(currentContext.getAppName()).e(TAG, "should never have a conversion error");
          WebLogger.getLogger(currentContext.getAppName()).printStackTrace(e);
          throw new IllegalStateException("should never have a conversion error");
        }
        activity.signalResponseAvailable(responseStr, callerID);
    }

    public synchronized boolean isAlive() {
       return true;
    }
}
