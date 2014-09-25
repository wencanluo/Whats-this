package edu.pitt.cs.mips.coursemirror.core;

import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import edu.pitt.cs.mips.coursemirror.CourseMirrorModule;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class CourseMirrorNotificationReceiver extends BroadcastReceiver{
	
	private static final String TAG = "MyCustomReceiver";
	 
	  @Override
	  public void onReceive(Context context, Intent intent) {
		  String cid="";
		  int lectureNo=0;//0 as the default value
	
	    try {
	      String action = intent.getAction();
	      String channel = intent.getExtras().getString("com.parse.Channel");
	      JSONObject json = new JSONObject(intent.getExtras().getString("com.parse.Data"));
	 
	      //Log.d(TAG, "got action " + action + " on channel " + channel + " with:");
//	      Iterator itr = json.keys();
//	      while (itr.hasNext()) {
//	        String key = (String) itr.next();
//	        Log.d(TAG, "..." + key + " => " + json.getString(key));
//	      }
	      cid = json.getString("cid");
	      lectureNo = Integer.parseInt(json.getString("lectureNo"));
	      
	    } catch (JSONException e) {
	      Log.d(TAG, "JSONException: " + e.getMessage());
	    }
	    CourseMirrorModule.setCID(cid);
	    CourseMirrorModule.setLectureNo(lectureNo);
	    CourseMirrorModule.StartFromNotification(true);
	  }

}
