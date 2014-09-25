package edu.pitt.cs.mips.coursemirror.ui;

import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import edu.pitt.cs.mips.coursemirror.CourseMirrorModule;
import edu.pitt.cs.mips.coursemirror.CourseMirrorServiceProvider;
import edu.pitt.cs.mips.coursemirror.R;
import edu.pitt.cs.mips.coursemirror.R.drawable;
import edu.pitt.cs.mips.coursemirror.authenticator.LogoutService;
import edu.pitt.cs.mips.coursemirror.core.Lecture;

import com.github.kevinsawicki.wishlist.SingleTypeAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static edu.pitt.cs.mips.coursemirror.util.Constants.Extra.LECTURE_ITEM;

public class LectureListFragment extends ItemListFragment<Lecture> {

    protected CourseMirrorServiceProvider serviceProvider;
    protected LogoutService logoutService;
    static private LectureListFragment self;
    List<Lecture> lecture_items=null;
    LectureListAdapter listadapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        self = this;
        serviceProvider = CourseMirrorModule.getCourseMirrorServiceProvider();
        logoutService =  CourseMirrorModule.getLogoutService();
        
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setEmptyText(R.string.no_lectures);
    }

//    @Override
//	 public void onResume() {
//       super.onResume();
//       
//       View newV = getView();
//       Log.v("lecture1", "get the lecture!!");
//       updateUI();
//   }
    
    @Override
    protected void configureList(Activity activity, ListView listView) {
        super.configureList(activity, listView);

        listView.setFastScrollEnabled(true);
        listView.setDividerHeight(0);

        getListAdapter()
                .addHeader(activity.getLayoutInflater()
                        .inflate(R.layout.lecture_list_item_labels, null));
    }

    @Override
    protected LogoutService getLogoutService() {
        return logoutService;
    }

    @Override
    public void onDestroyView() {
        setListAdapter(null);

        super.onDestroyView();
    }

    static public void refreshList()
    {
    	self.refreshWithProgress();
    }
    
    List<Lecture> lectureHelper()
    {
    	ArrayList<Lecture> list = new ArrayList<Lecture>();
    	
    	Lecture lecture1 = new Lecture();
    	lecture1.setTitle("Introduction");
    	lecture1.setnumber(1);
    	list.add(lecture1);
    	
    	Lecture lecture2 = new Lecture();
    	lecture2.setTitle("Seminal Ideas");
    	lecture2.setnumber(2);
    	list.add(lecture2);

    	Lecture lecture3 = new Lecture();
    	lecture3.setTitle("The Iterative Design Cycle");
    	lecture3.setnumber(3);
    	list.add(lecture3);

    	Lecture lecture4 = new Lecture();
    	lecture4.setTitle("Affordance and Conceptual Models");
    	lecture4.setnumber(4);
    	list.add(lecture4);
    	
    	return list;
    	
    }
    
    @Override
    public Loader<List<Lecture>> onCreateLoader(int id, Bundle args) {
        final List<Lecture> initialItems = items;
        return new ThrowableLoader<List<Lecture>>(getActivity(), items) {

            @Override
            public List<Lecture> loadData() throws Exception {
                try {
                    if (getActivity() != null) {
                    	
                    	String cid = CourseMirrorModule.getCID();
                    	
                    	List<Lecture> list;
                    	if (cid == null)
                    	{
                    	   //list =serviceProvider.getService(getActivity()).getLectures();
                    		list = Collections.emptyList();// if cid is not specified, return empty list
                    	} else {
                    		cid = "{\"cid\":\"" + cid+"\"}";
                    		Log.i("CourseMIRROR", cid);
                    		//list = serviceProvider.getService(getActivity()).getCourseLectures(cid);//not sorted
                    		list = serviceProvider.getService(getActivity()).getCourseLectures(cid,"number");//sorted by lecture number
                    	}
                    	lecture_items=list;
  
                    	 return list;
//                        return lectureHelper(); 
                    } else {
                        return Collections.emptyList();
                    }

                } catch (OperationCanceledException e) {
                    Activity activity = getActivity();
                    if (activity != null)
                        activity.finish();
                    return initialItems;
                }
            }
        };
    }

    @Override
    protected SingleTypeAdapter<Lecture> createAdapter(List<Lecture> items) {
    	listadapter=new LectureListAdapter(getActivity().getLayoutInflater(), items);
        return listadapter;
    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        Lecture lecture = ((Lecture) l.getItemAtPosition(position));

/*      startActivity(new Intent(getActivity(), LectureActivity.class).putExtra(LECTURE_ITEM, lecture));*/
        CourseMirrorModule.setLectureNo(lecture.getnumber());

        
        if(CourseMirrorModule.getPrevLecturePosition()!=position){
        	updateUI(v,position);
        }
        	 
        //Log.v("position", Integer.toString(position));
        
        Fragment frg = ReflectionWritingFragment.self;
        final FragmentTransaction ft = this.getActivity().getSupportFragmentManager().beginTransaction();
        ft.detach(frg);
        ft.attach(frg);
        ft.commit();
        CarouselFragment.setPage(2);
        
        
    }
    
    /**UI change corresponding to the new lecture selection
     * 1) change the color of prev-selected lecture back
     * 2) set this lecture as pre-selected for future use
     * 3) hightlight the lecture
     * 
     * @param v
     * @param position
     */
    public void updateUI(View v, int position){
    	if(CourseMirrorModule.getPrevLecturePosition()!=-1){
    		if (CourseMirrorModule.getPrevLecturePosition() % 2 != 0)
    			CourseMirrorModule.getPrevLectureView().setBackgroundResource(drawable.table_background_alternate_selector);
    		else
    			CourseMirrorModule.getPrevLectureView().setBackgroundResource(drawable.table_background_selector);
    	}
    	CourseMirrorModule.setPrevLectureView(v);
    	CourseMirrorModule.setPrevLecturePosition(position);
        
        v.setBackgroundResource(drawable.button_background_enabled);
    }
    
    @Override
    protected int getErrorMessage(Exception exception) {
        return R.string.error_loading_lectures;
    }
    
   
}
