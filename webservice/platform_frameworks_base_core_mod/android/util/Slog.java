/*
 * Copyright (C) 2006 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package android.util;

/**
 * @hide
 */
public final class Slog {

    private Slog() {
    }

    public static int v(String tag, String msg) {
    	Log.v(tag, msg);
    	return 0;
    }

    public static int v(String tag, String msg, Throwable tr) {
    	Log.v(tag, msg, tr);
    	return 0;
    }

    public static int d(String tag, String msg) {
    	Log.d(tag, msg);
    	return 0;
    }

    public static int d(String tag, String msg, Throwable tr) {
    	Log.d(tag, msg, tr);
    	return 0;
    }

    public static int i(String tag, String msg) {
    	Log.i(tag, msg);
    	return 0;
    }

    public static int i(String tag, String msg, Throwable tr) {
    	Log.i(tag, msg, tr);
    	return 0;
    }

    public static int w(String tag, String msg) {
    	Log.w(tag, msg);
    	return 0;
    }

    public static int w(String tag, String msg, Throwable tr) {
    	Log.w(tag, msg, tr);
    	return 0;
    }

    public static int e(String tag, String msg) {
    	Log.e(tag, msg);
    	return 0;
    }

    public static int e(String tag, String msg, Throwable tr) {
    	Log.e(tag, msg, tr);
    	return 0;
    }

    public static int w(String tag, Throwable tr) {
    	Log.w(tag, "system error", tr);
    	return 0;
    }

    /**
     * Like {@link Log#wtf(String, String)}, but will never cause the caller to crash, and
     * will always be handled asynchronously.  Primarily for use by coding running within
     * the system process.
     */
    public static int wtf(String tag, String msg) {
        return Log.wtf(Log.LOG_ID_SYSTEM, tag, msg, null, false, true);
    }

    /**
     * Like {@link #wtf(String, String)}, but does not output anything to the log.
     */
    public static void wtfQuiet(String tag, String msg) {
        Log.wtfQuiet(Log.LOG_ID_SYSTEM, tag, msg, true);
    }

    /**
     * Like {@link Log#wtfStack(String, String)}, but will never cause the caller to crash, and
     * will always be handled asynchronously.  Primarily for use by coding running within
     * the system process.
     */
    public static int wtfStack(String tag, String msg) {
        return Log.wtf(Log.LOG_ID_SYSTEM, tag, msg, null, true, true);
    }

    /**
     * Like {@link Log#wtf(String, Throwable)}, but will never cause the caller to crash,
     * and will always be handled asynchronously.  Primarily for use by coding running within
     * the system process.
     */
    public static int wtf(String tag, Throwable tr) {
        return Log.wtf(Log.LOG_ID_SYSTEM, tag, tr.getMessage(), tr, false, true);
    }

    /**
     * Like {@link Log#wtf(String, String, Throwable)}, but will never cause the caller to crash,
     * and will always be handled asynchronously.  Primarily for use by coding running within
     * the system process.
     */
    public static int wtf(String tag, String msg, Throwable tr) {
        return Log.wtf(Log.LOG_ID_SYSTEM, tag, msg, tr, false, true);
    }

    public static int println(int priority, String tag, String msg) {
        return Log.println_native(Log.LOG_ID_SYSTEM, priority, tag, msg);
    }
}

