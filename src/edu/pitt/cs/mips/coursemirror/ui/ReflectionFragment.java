package edu.pitt.cs.mips.coursemirror.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import edu.pitt.cs.mips.coursemirror.R;
import edu.pitt.cs.mips.coursemirror.R.id;



/**
 * Fragment which houses the View pager.
 */
public class ReflectionFragment extends Fragment {
	protected String URL = "http://mips.lrdc.pitt.edu/CourseMIRROR";
	protected WebView webView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	View view = inflater.inflate(R.layout.fragment_reflection, container, false);
    	
    	webView = (WebView) view.findViewById(id.webview);
    	
        webView.getSettings().setJavaScriptEnabled(true);
//      webView.getSettings().setSupportMultipleWindows(false);
      
        webView.setWebViewClient(new WebViewClient());        
        webView.loadUrl(URL);        
    	
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }
    
}


