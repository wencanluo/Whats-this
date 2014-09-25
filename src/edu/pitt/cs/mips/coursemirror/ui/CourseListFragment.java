package edu.pitt.cs.mips.coursemirror.ui;

import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import edu.pitt.cs.mips.coursemirror.CourseMirrorModule;
import edu.pitt.cs.mips.coursemirror.CourseMirrorServiceProvider;
import edu.pitt.cs.mips.coursemirror.R;
import edu.pitt.cs.mips.coursemirror.R.drawable;
import edu.pitt.cs.mips.coursemirror.authenticator.LogoutService;
import edu.pitt.cs.mips.coursemirror.core.Course;

import com.github.kevinsawicki.wishlist.SingleTypeAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CourseListFragment extends ItemListFragment<Course> {

    protected CourseMirrorServiceProvider serviceProvider;
    protected LogoutService logoutService;


    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        
        serviceProvider = CourseMirrorModule.getCourseMirrorServiceProvider();
        logoutService =  CourseMirrorModule.getLogoutService();        
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setEmptyText(R.string.no_courses);
    }

    @Override
    protected void configureList(final Activity activity, final ListView listView) {
        super.configureList(activity, listView);

        listView.setFastScrollEnabled(true);
        listView.setDividerHeight(0);

        getListAdapter().addHeader(activity.getLayoutInflater()
                .inflate(R.layout.course_list_item_labels, null));
    }

    @Override
    protected LogoutService getLogoutService() {
        return logoutService;
    }

    List<Course> courseHelper()
    {
    	ArrayList<Course> list = new ArrayList<Course>();
    	
    	Course course1 = new Course();
    	course1.setCID("CS2610");
    	course1.setTitle("Research Topics in Human-Computer Intearction");
    	list.add(course1);
    	

    	Course course2 = new Course();
    	course2.setCID("CS1635");
    	course2.setTitle("Designing, Prototyping, and Evlauating Mobile Interfaces");
    	list.add(course2);
    	
    	return list;
    }
    @Override
    public Loader<List<Course>> onCreateLoader(final int id, final Bundle args) {
        final List<Course> initialItems = items;
        return new ThrowableLoader<List<Course>>(getActivity(), items) {
            @Override
            public List<Course> loadData() throws Exception {

                try {
                    List<Course> latest = null;

                    if (getActivity() != null) {
                    	
                    	serviceProvider.getService(getActivity()).getQuestions();
                        latest = serviceProvider.getService(getActivity()).getCourses();
                        
                       // serviceProvider.getService(getActivity()).getQuestions();
                        
//                        latest = courseHelper();
                    }

                    if (latest != null) {
                        return latest;
                    } else {
                        return Collections.emptyList();
                    }
                } catch (final OperationCanceledException e) {
                    final Activity activity = getActivity();
                    if (activity != null) {
                        activity.finish();
                    }
                    return initialItems;
                }
            }
        };

    }

    public void onListItemClick(final ListView l, final View v, final int position, final long id) {
        final Course course = ((Course) l.getItemAtPosition(position));

/*        startActivity(new Intent(getActivity(), CourseActivity.class).putExtra(USER, user));*/
        
        String uri = course.getURL();
        
        if (uri == null || uri.length() < 5)
        {
        	uri = "http://www.cs.pitt.edu";
        } 
        
        CourseMirrorModule.setCID(course.getCID());
        //Log.v("CoursePosition", Integer.toString(position));
        if(CourseMirrorModule.getPrevCoursePosition()!=position){
        	updateUI(v.findViewById(R.id.course_avatar),position);
        }
        
        CarouselFragment.setPage(1);
          LectureListFragment.refreshList();

    }
    
    public void updateUI(View v, int position){
    	if(CourseMirrorModule.getPrevCourseView()!=null){
    		CourseMirrorModule.getPrevCourseView().setBackgroundResource(drawable.table_background_selector);
    		CourseMirrorModule.getPrevCourseView().findViewById(R.id.course_avatar).refreshDrawableState();
    	}
    	
    	CourseMirrorModule.setPrevCourseView(v);
    	CourseMirrorModule.setPrevCoursePosition(position);
        
        v.setBackgroundResource(drawable.button_background_enabled);
    }
    
    @Override
    public void onLoadFinished(final Loader<List<Course>> loader, final List<Course> items) {
        super.onLoadFinished(loader, items);
        
        if(CourseMirrorModule.StartFromNotification()){        	
        	CarouselFragment.setPage(2);
        	CourseMirrorModule.StartFromNotification(false);
        }

    }

    @Override
    protected int getErrorMessage(final Exception exception) {
        return R.string.error_loading_courses;
    }

    @Override
    protected SingleTypeAdapter<Course> createAdapter(final List<Course> items) {
        return new CourseListAdapter(getActivity(), getActivity().getLayoutInflater(), items);
    }
}
