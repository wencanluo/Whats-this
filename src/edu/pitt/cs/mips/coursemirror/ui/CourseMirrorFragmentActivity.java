package edu.pitt.cs.mips.coursemirror.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.squareup.otto.Bus;

import edu.pitt.cs.mips.coursemirror.CourseMirrorModule;

/**
 * Base class for all CourseMIRROR Activities that need fragments.
 */
public class CourseMirrorFragmentActivity extends ActionBarActivity {
    protected Bus eventBus;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        eventBus = CourseMirrorModule.getOttoBus();
    }

    @Override
    public void setContentView(final int layoutResId) {
        super.setContentView(layoutResId);

    }

    @Override
    protected void onResume() {
        super.onResume();
        eventBus.register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        eventBus.unregister(this);
    }
}
