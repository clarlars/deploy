  /*
   * Copyright (C) 2014 University of Washington
   *
   * Licensed under the Apache License, Version 2.0 (the "License"); you may not
   * use this file except in compliance with the License. You may obtain a copy of
   * the License at
   *
   * http://www.apache.org/licenses/LICENSE-2.0
   *
   * Unless required by applicable law or agreed to in writing, software
   * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
   * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
   * License for the specific language governing permissions and limitations under
   * the License.
   */

package org.opendatakit.webservice.rest.endpoint.sync;

import java.util.ArrayList;
import java.util.List;

import org.opendatakit.services.R;
import org.opendatakit.services.sync.service.GlobalSyncNotificationManager;
import org.opendatakit.services.sync.service.exceptions.NoAppNameSpecifiedException;

import android.content.Context;

public final class DesktopSyncNotificationManagerImpl implements GlobalSyncNotificationManager{

    private static final int UNIQUE_ID = 1337;
    private static final int UPDATE_NOTIFICATION_ID = 0;

    // Time Unit: milliseconds.
    // 5 minutes. Amount of time to hold onto the details of a sync outcome.
    // after this amount of time, if there are no outstanding sync actions
    // and if there are no active bindings, then the sync service will shut
    // down.
    public static final long RETENTION_PERIOD = 300000L;

    private final Context context;

    private boolean displayNotification;

    private List<AppSyncStatus> statusList = new ArrayList<AppSyncStatus>();
    
    private String displayText;

    public DesktopSyncNotificationManagerImpl(Context context) {
      this.context = context;
      this.displayNotification = false;
    }

    public synchronized void startingSync(String appName) throws NoAppNameSpecifiedException {
      AppSyncStatus appStatus = getAppStatus(appName);
      appStatus.setSyncing(true);
      update();
    }

    public synchronized void stoppingSync(String appName) throws NoAppNameSpecifiedException {
      AppSyncStatus appStatus = getAppStatus(appName);
      appStatus.setSyncing(false);
      update();
    }

    public synchronized boolean isDisplayingNotification() {
      return displayNotification;
    }

    private AppSyncStatus getAppStatus(String appName) throws NoAppNameSpecifiedException {
      if (appName == null) {
        throw new NoAppNameSpecifiedException("Cannot update NotificationManager without appName");
      }

      AppSyncStatus appStatus = null;

      // see if manager already knows about the app
      for (AppSyncStatus status : statusList) {
        if (status.getAppName().equals(appName)) {
          appStatus = status;
        }
      }

      // if manager does not know about app, create it
      if (appStatus == null) {
        appStatus = new AppSyncStatus(appName);
        statusList.add(appStatus);
      }
      return appStatus;
    }

    private void update() {
      // check if NotificationManager should be displaying notification
      boolean shouldDisplay = false;
      for (AppSyncStatus status : statusList) {
        if (status.isSyncing()) {
          shouldDisplay = true;
        }
      }

      // if should and actual do not match fix
      if (shouldDisplay && !displayNotification) {
        createNotification();
      } else if (!shouldDisplay && displayNotification) {
        removeNotification();
      }

      if ( shouldDisplay != displayNotification ) {
        throw new IllegalStateException("unexpected dissonant state");
      }
    }

    private void createNotification() {
      // The intent to launch when the user clicks the expanded notification
      // Intent tmpIntent = new Intent(service, SyncActivity.class);

     String title = "ODK Sync";
     String text = "ODK is syncing an Application";
     displayText = text;
     long when = System.currentTimeMillis();
     boolean autoCancel = false;
     boolean ongoing = true;
     // int smallIcon = R.drawable.odk_services;

     /*
      Notification runningNotification = builder.build();
      runningNotification.flags |= Notification.FLAG_NO_CLEAR;

      if (!test) {
        service.startForeground(UNIQUE_ID, runningNotification);
      }
      */
      displayNotification = true;
    }

    private void removeNotification() {
     /*
      if (!test) {
        service.stopForeground(true);
      }
      */
      displayNotification = false;
    }

    public synchronized void updateNotification( String appName, String text,
                                                int maxProgress, int progress, boolean indeterminateProgress) {
     String title = context.getString(R.string.sync_notification_syncing, appName);
     // String text = text;
     boolean autoCancel = false;
     boolean ongoing = true;
     // int smallIcon = R.drawable.ic_popup_sync

     int maxProgressStatus = maxProgress;
     int progressStatus = progress;
     boolean indeterminateProgressStatus = indeterminateProgress;
     // UPDATE_NOTIFICATION_ID
     displayText = text;
    }

    public synchronized void finalErrorNotification(String appName, String text) {
     String title = context.getString(R.string.sync_notification_failure, appName);
     // String text = text;
     boolean autoCancel = true;
     boolean ongoing = false;
     // int smallIcon = R.drawable.ic_error_white_24dp
     
     // UPDATE_NOTIFICATION_ID
     displayText = text;
    }

    public synchronized void finalConflictNotification(String appName, String text) {
       String title = context.getString(R.string.sync_notification_conflicts, appName);
       // String text = text;
       boolean autoCancel = true;
       boolean ongoing = false;
       // int smallIcon = R.drawable.ic_warning_white_24dp
       
       // UPDATE_NOTIFICATION_ID
       displayText = text;
    }

    public synchronized void clearNotification(String appName, String title, String text) {
       // String title = title;
       // String text = text;
       boolean autoCancel = true;
       boolean ongoing = false;
       // int smallIcon = R.drawable.ic_done_white_24dp
       
       // UPDATE_NOTIFICATION_ID
       displayText = text;
    }

    public synchronized void clearVerificationNotification(String appName, String title, String text) {
       // String title = title;
       // String text = text;
       boolean autoCancel = true;
       boolean ongoing = false;
       // int smallIcon = R.drawable.ic_done_white_24dp
       
       // UPDATE_NOTIFICATION_ID
       displayText = text;
    }

    private final class AppSyncStatus {
      private final String appName;
      private boolean syncing;

      AppSyncStatus(String appName) {
        this.appName = appName;
        this.syncing = false;
      }

      public String getAppName() {
        return appName;
      }

      public void setSyncing(boolean syncing) {
        this.syncing = syncing;
      }

      public boolean isSyncing() {
        return syncing;
      }

    }

    public String getStatus() {
      return displayText;
    }
  }
