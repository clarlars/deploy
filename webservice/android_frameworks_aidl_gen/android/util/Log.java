package android.util;

import org.opendatakit.logging.WebLogger;

public class Log {

    /**
     * Priority constant for the println method; use Log.v.
     */
    public static final int VERBOSE = 2;

    /**
     * Priority constant for the println method; use Log.d.
     */
    public static final int DEBUG = 3;

    /**
     * Priority constant for the println method; use Log.i.
     */
    public static final int INFO = 4;

    /**
     * Priority constant for the println method; use Log.w.
     */
    public static final int WARN = 5;

    /**
     * Priority constant for the println method; use Log.e.
     */
    public static final int ERROR = 6;

    /**
     * Priority constant for the println method.
     */
    public static final int ASSERT = 7;

    public static boolean isLoggable(String tag, int level) {
    	return true;
    }

	public static void e(String tag, String message, Throwable t) {
		WebLogger.getContextLogger().e(tag, message);
		WebLogger.getContextLogger().printStackTrace(t);
	}
	public static void e(String tag, String message) {
		WebLogger.getContextLogger().e(tag, message);
	}
	public static void w(String tag, String message, Throwable t) {
		WebLogger.getContextLogger().w(tag, message);
		WebLogger.getContextLogger().printStackTrace(t);
	}
	public static void w(String tag, String message) {
		WebLogger.getContextLogger().w(tag, message);
	}
	public static void i(String tag, String message, Throwable t) {
		WebLogger.getContextLogger().i(tag, message);
		WebLogger.getContextLogger().printStackTrace(t);
	}
	public static void i(String tag, String message) {
		WebLogger.getContextLogger().i(tag, message);
	}
	public static void d(String tag, String message, Throwable t) {
		WebLogger.getContextLogger().d(tag, message);
		WebLogger.getContextLogger().printStackTrace(t);
	}
	public static void d(String tag, String message) {
		WebLogger.getContextLogger().d(tag, message);
	}
	public static void v(String tag, String message, Throwable t) {
		WebLogger.getContextLogger().v(tag, message);
		WebLogger.getContextLogger().printStackTrace(t);
	}
	public static void v(String tag, String message) {
		WebLogger.getContextLogger().v(tag, message);
	}
	
    /** @hide */ public static final int LOG_ID_MAIN = 0;
    /** @hide */ public static final int LOG_ID_RADIO = 1;
    /** @hide */ public static final int LOG_ID_EVENTS = 2;
    /** @hide */ public static final int LOG_ID_SYSTEM = 3;
    /** @hide */ public static final int LOG_ID_CRASH = 4;


    static int wtf(int logId, String tag, String msg, Throwable tr, boolean localStack,
            boolean system) {
		WebLogger.getContextLogger().e(tag, msg);
		WebLogger.getContextLogger().printStackTrace(tr);
        return 0;
    }

    static void wtfQuiet(int logId, String tag, String msg, boolean system) {
		WebLogger.getContextLogger().e(tag, msg);
    }
    
    public static int println_native(int bufID, int priority, String tag, String msg) {
		WebLogger.getContextLogger().e(tag, msg);
		return 0;
    }
}