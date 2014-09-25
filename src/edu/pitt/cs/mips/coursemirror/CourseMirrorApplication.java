

package edu.pitt.cs.mips.coursemirror;

import com.parse.Parse;
import com.parse.PushService;

import edu.pitt.cs.mips.coursemirror.ui.MainActivity;
import edu.pitt.cs.mips.coursemirror.util.Ln;
import android.app.Application;
import android.app.Instrumentation;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract.Constants;

import static edu.pitt.cs.mips.coursemirror.util.Constants.Http.PARSE_APP_ID;
import static edu.pitt.cs.mips.coursemirror.util.Constants.Http.PARSE_CLIENT_KEY;

/**
 * CourseMIRROR application
 */
public class CourseMirrorApplication extends Application {

    private static CourseMirrorApplication instance;

    /**
     * Create main application
     */
    public CourseMirrorApplication() {
    }

    /**
     * Create main application
     *
     * @param context
     */
    public CourseMirrorApplication(final Context context) {
        this();
        attachBaseContext(context);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        
        Parse.initialize(this, PARSE_APP_ID, PARSE_CLIENT_KEY);
        PushService.setDefaultPushCallback(this, MainActivity.class);
        
    }

    /**
     * Create main application
     *
     * @param instrumentation
     */
    public CourseMirrorApplication(final Instrumentation instrumentation) {
        this();
        attachBaseContext(instrumentation.getTargetContext());
    }

    public static CourseMirrorApplication getInstance() {
        return instance;
    }
    
    public NotificationManager getNotificationManager() {
        return (NotificationManager) instance.getSystemService(Context.NOTIFICATION_SERVICE);
    }    
}
