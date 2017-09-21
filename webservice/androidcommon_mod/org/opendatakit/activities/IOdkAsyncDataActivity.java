package org.opendatakit.activities;

import org.opendatakit.views.ExecutorProcessor;

public interface IOdkAsyncDataActivity extends IOdkDataActivity {

  void start(ExecutorProcessor work);

}
