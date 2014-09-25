package edu.pitt.cs.mips.coursemirror;

import android.accounts.AccountManager;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.view.inputmethod.InputMethodManager;


public class AndroidModule {

	static public Context getAppContext() {
        return CourseMirrorApplication.getInstance().getApplicationContext();
    }

    static public SharedPreferences getDefaultSharedPreferences() {
    	
    	Context context = getAppContext();
    	
        return PreferenceManager.getDefaultSharedPreferences(context);
    }


    static public PackageInfo getPackageInfo() {
        try {
        	Context context  = getAppContext();
        	
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    static public TelephonyManager getTelephonyManager() {
    	Context context = getAppContext();
    	
        return (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
    }


    static public InputMethodManager getInputMethodManager() {
    	Context context = getAppContext();
    	
        return (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
    }


    static public ApplicationInfo getApplicationInfo() {
    	Context context = getAppContext();
    	
        return context.getApplicationInfo();
    }

    static public AccountManager getAccountManager() {
    	Context context = getAppContext();
    	
        return AccountManager.get(context);
    }

    static public ClassLoader getClassLoader() {
    	Context context = getAppContext();
    	
        return context.getClassLoader();
    }

    static public NotificationManager getNotificationManager() {
    	Context context = getAppContext();
    	
        return (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

}
