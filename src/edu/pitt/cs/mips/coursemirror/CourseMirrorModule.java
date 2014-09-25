package edu.pitt.cs.mips.coursemirror;

import java.util.ArrayList;

import android.accounts.AccountManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import edu.pitt.cs.mips.coursemirror.authenticator.ApiKeyProvider;
import edu.pitt.cs.mips.coursemirror.authenticator.CourseMirrorAuthenticatorActivity;
import edu.pitt.cs.mips.coursemirror.authenticator.LogoutService;
import edu.pitt.cs.mips.coursemirror.core.CourseMirrorService;
import edu.pitt.cs.mips.coursemirror.core.PostFromAnyThreadBus;
import edu.pitt.cs.mips.coursemirror.core.Question;
import edu.pitt.cs.mips.coursemirror.core.RestAdapterRequestInterceptor;
import edu.pitt.cs.mips.coursemirror.core.RestErrorHandler;
import edu.pitt.cs.mips.coursemirror.core.UserAgentProvider;
import edu.pitt.cs.mips.coursemirror.util.Constants;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.otto.Bus;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;


public class CourseMirrorModule {
	
	static Bus ottoBus;
	static LogoutService logoutService;
	static String CID;
	static String user;
	static int lecture_No;
	static ArrayList<Question> questionList;
	static ArrayList<String> answerList;
	static int prev_selected_course_position = -1;
	static int prev_selected_lecture_position = -1;
	static View prev_selected_course_view = null;
	static View prev_selected_lecture_view = null;
	
	
	static boolean startFromNotification=false;//whether the activity is triggered by clicking the notification
	
	static public void setPrevCoursePosition(int pos){
		prev_selected_course_position=pos;
	}
	static public int getPrevCoursePosition(){
		return prev_selected_course_position;
	}
	
	
	static public void setPrevLecturePosition(int pos){
		prev_selected_lecture_position=pos;
	}
	static public int getPrevLecturePosition(){
		return prev_selected_lecture_position;
	}
	
	static public void setPrevCourseView(View v){
		prev_selected_course_view=v;
	}
	static public View getPrevCourseView(){
		return prev_selected_course_view;
	}
	
	
	static public void setPrevLectureView(View v){
		prev_selected_lecture_view=v;
	}
	static public View getPrevLectureView(){
		return prev_selected_lecture_view;
	}
	
	static public ArrayList<Question> getQuestions()
	{
		return questionList;
	}
	
	static public void SetQuestions(ArrayList<Question> list)
	{
		questionList=list;
	}
	
	static public ArrayList<String> getAnswers()
	{
		return answerList;
	}
	
	static public void SetAnswers(ArrayList<String> list)
	{
		answerList=list;
	}
	
	static public String getCID()
	{
		return CID;
	}

	static public void setCID(String cid)
	{
		CID = cid;
	}
	static public int getLectureNo()
	{
		return lecture_No;
	}

	static public void setLectureNo(int number)
	{
		lecture_No = number;
	}
	
	static public String getUser()
	{
		return user;
	}

	static public void setUser(String u)
	{
		user = u;
	}
	
	static public void StartFromNotification(boolean b)
	{
		startFromNotification=b;
	}
	
	static public boolean StartFromNotification()
	{
		return startFromNotification;
	}
	
    static public Bus getOttoBus() {
    	if (ottoBus == null)
    	{
    		ottoBus = new PostFromAnyThreadBus();
    	}
    	
    	return ottoBus; 
    }

    static public LogoutService getLogoutService() {
    	if (logoutService == null)
    	{
    	
    		Context context = AndroidModule.getAppContext();
    		AccountManager accountManager = AndroidModule.getAccountManager();
    		logoutService = new LogoutService(context, accountManager);
    	}
    	
        return logoutService;
    }

    static public CourseMirrorService getCourseMirrorService() {
    	RestAdapter restAdapter = getRestAdapter();
    	
        return new CourseMirrorService(restAdapter);
    }

    static public CourseMirrorServiceProvider getCourseMirrorServiceProvider() {
    	RestAdapter restAdapter = getRestAdapter();
    	ApiKeyProvider apiKeyProvider = getApiKeyProvider();
    	
        return new CourseMirrorServiceProvider(restAdapter, apiKeyProvider);
    }

    static public ApiKeyProvider getApiKeyProvider() {
    	AccountManager accountManager = AndroidModule.getAccountManager();
    	
        return new ApiKeyProvider(accountManager);
    }

    static public Gson getGson() {
        /**
         * GSON instance to use for all request  with date format set up for proper parsing.
         * <p/>
         * You can also configure GSON with different naming policies for your API.
         * Maybe your API is Rails API and all json values are lower case with an underscore,
         * like this "first_name" instead of "firstName".
         * You can configure GSON as such below.
         * <p/>
         *
         * public static final Gson GSON = new GsonBuilder().setDateFormat("yyyy-MM-dd")
         *         .setFieldNamingPolicy(LOWER_CASE_WITH_UNDERSCORES).create();
         */
        return new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
    }

    static public RestErrorHandler getRestErrorHandler() {
    	Bus bus = getOttoBus();
    	
        return new RestErrorHandler(bus);
    }

    static public RestAdapterRequestInterceptor getRestAdapterRequestInterceptor() {
    	
    	UserAgentProvider userAgentProvider = new UserAgentProvider();
    	
        return new RestAdapterRequestInterceptor(userAgentProvider);
    }

    static public RestAdapter getRestAdapter() {
    	RestErrorHandler restErrorHandler = getRestErrorHandler();
    	RestAdapterRequestInterceptor restRequestInterceptor =  getRestAdapterRequestInterceptor();
    	Gson gson  = getGson();
    	
        return new RestAdapter.Builder()
                .setEndpoint(Constants.Http.URL_BASE)
                .setErrorHandler(restErrorHandler)
                .setRequestInterceptor(restRequestInterceptor)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setConverter(new GsonConverter(gson))
                .build();
    }

}
