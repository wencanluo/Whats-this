package edu.pitt.cs.mips.coursemirror.util;


import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.util.Log;


import edu.pitt.cs.mips.coursemirror.CourseMirrorApplication;


/**
 * Originally from RoboGuice:
 * https://github.com/roboguice/roboguice/blob/master/roboguice/src/main/java/roboguice/util/Ln.java
 * <p/>
 * A more natural android logging facility
 * <p/>
 * WARNING: CHECK OUT COMMON PITFALLS BELOW
 * <p/>
 * Unlike {@link android.util.Log}, Log provides sensible defaults.
 * Debug and Verbose logging is enabled for applications that
 * have "android:debuggable=true" in their AndroidManifest.xml.
 * For apps built using SDK Tools r8 or later, this means any debug
 * build.  Release builds built with r8 or later will have verbose
 * and debug log messages turned off.
 * <p/>
 * The default tag is automatically set to your app's packagename,
 * and the current context (eg. activity, service, application, etc)
 * is appended as well.  You can add an additional parameter to the
 * tag using {@link #Log(String)}.
 * <p/>
 * Log-levels can be programatically overridden for specific instances
 * using {@link #Log(String, boolean, boolean)}.
 * <p/>
 * Log messages may optionally use {@link String#format(String, Object...)}
 * formatting, which will not be evaluated unless the log statement is output.
 * Additional parameters to the logging statement are treated as varrgs parameters
 * to {@link String#format(String, Object...)}
 * <p/>
 * Also, the current file and line is automatically appended to the tag
 * (this is only done if debug is enabled for performance reasons).
 * <p/>
 * COMMON PITFALLS:
 * * Make sure you put the exception FIRST in the call.  A common
 * mistake is to place it last as is the android.util.Log convention,
 * but then it will get treated as varargs parameter.
 * * vararg parameters are not appended to the log message!  You must
 * insert them into the log message using %s or another similar
 * format parameter
 * <p/>
 * Usage Examples:
 * <p/>
 * Ln.v("hello there");
 * Ln.d("%s %s", "hello", "there");
 * Ln.e( exception, "Error during some operation");
 * Ln.w( exception, "Error during %s operation", "some other");
 */
@SuppressWarnings({"ImplicitArrayToString"})
public class Ln {

    public static int v(Throwable t) {
        return println(Log.VERBOSE,  Log.getStackTraceString(t));
    }

    public static int v(Object s1, Object... args) {
        final String s = Strings.toString(s1);
        final String message = args.length > 0 ? String.format(s, args) : s;
        return println(Log.VERBOSE, message);
    }

    public static int v(Throwable throwable, Object s1, Object... args) {

        final String s = Strings.toString(s1);
        final String message = (args.length > 0 ? String.format(s, args) : s) + '\n' +
                Log.getStackTraceString(throwable);
        return println(Log.VERBOSE, message);
    }

    public static int d(Throwable t) {
    	
        return println(Log.DEBUG,  Log.getStackTraceString(t));
    }

    public static int d(Object s1, Object... args) {

        final String s = Strings.toString(s1);
        final String message = args.length > 0 ? String.format(s, args) : s;
        
        return println(Log.DEBUG, message);
    }

    public static int d(Throwable throwable, Object s1, Object... args) {

        final String s = Strings.toString(s1);
        final String message = (args.length > 0 ? String.format(s, args) : s) + '\n' +
                Log.getStackTraceString(throwable);
        return println(Log.DEBUG, message);
    }

    public static int i(Throwable t) {
    	
        return println(Log.INFO, Log.getStackTraceString(t));
    }

    public static int i(Object s1, Object... args) {
        final String s = Strings.toString(s1);
        final String message = args.length > 0 ? String.format(s, args) : s;
        return println(Log.INFO, message);
    }

    public static int i(Throwable throwable, Object s1, Object... args) {
        final String s = Strings.toString(s1);
        final String message = (args.length > 0 ? String.format(s, args) : s) + '\n' +
                Log.getStackTraceString(throwable);
        return println(Log.INFO, message);
    }

    public static int w(Throwable t) {
        return println(Log.WARN, Log.getStackTraceString(t));
    }

    public static int w(Object s1, Object... args) {
        final String s = Strings.toString(s1);
        final String message = args.length > 0 ? String.format(s, args) : s;
        return println(Log.WARN, message);
    }

    public static int w(Throwable throwable, Object s1, Object... args) {

        final String s = Strings.toString(s1);
        final String message = (args.length > 0 ? String.format(s, args) : s) + '\n' +
                Log.getStackTraceString(throwable);
        return println(Log.WARN, message);
    }

    public static int e(Throwable t) {
    	
        return println(Log.ERROR,  Log.getStackTraceString(t));
    }

    public static int e(Object s1, Object... args) {
        final String s = Strings.toString(s1);
        final String message = args.length > 0 ? String.format(s, args) : s;
        return println(Log.ERROR, message);
    }

    public static int e(Throwable throwable, Object s1, Object... args) {
        final String s = Strings.toString(s1);
        final String message = (args.length > 0 ? String.format(s, args) : s) + '\n' +
                Log.getStackTraceString(throwable);
        return println(Log.ERROR, message);
    }


    public static String logLevelToString(int loglevel) {
        switch (loglevel) {
            case Log.VERBOSE:
                return "VERBOSE";
            case Log.DEBUG:
                return "DEBUG";
            case Log.INFO:
                return "INFO";
            case Log.WARN:
                return "WARN";
            case Log.ERROR:
                return "ERROR";
            case Log.ASSERT:
                return "ASSERT";
        }

        return "UNKNOWN";
    }


    public static int println(int priority, String msg) {
        return Log.println(priority, "CourseMIRROR", processMessage(msg));
    }

    static protected String processMessage(String msg) {
            msg = String.format("%s %s", Thread.currentThread().getName(), msg);
        return msg;
    }

}