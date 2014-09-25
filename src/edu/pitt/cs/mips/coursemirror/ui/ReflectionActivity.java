package edu.pitt.cs.mips.coursemirror.ui;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import edu.pitt.cs.mips.coursemirror.R;
import edu.pitt.cs.mips.coursemirror.core.Lecture;


public class ReflectionActivity extends CourseMirrorActivity {
    protected String URL = "http://mips.lrdc.pitt.edu/CourseMIRROR";
    protected WebView webView;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.reflection_view);
        
        
        webView = (WebView) this.findViewById(R.id.webview);

        webView.getSettings().setJavaScriptEnabled(true);
//        webView.getSettings().setSupportMultipleWindows(false);
        
        webView.setWebViewClient(new WebViewClient());        
        webView.loadUrl(URL);        

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        setTitle("Reflections");

    }

}
