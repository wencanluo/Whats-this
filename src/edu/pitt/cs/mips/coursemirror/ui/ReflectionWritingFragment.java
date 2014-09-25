package edu.pitt.cs.mips.coursemirror.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseException;
import com.parse.SaveCallback;
import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.PageIndicator;

import edu.pitt.cs.mips.coursemirror.CourseMirrorModule;
import edu.pitt.cs.mips.coursemirror.CourseMirrorServiceProvider;
import edu.pitt.cs.mips.coursemirror.R;
import edu.pitt.cs.mips.coursemirror.R.drawable;
import edu.pitt.cs.mips.coursemirror.authenticator.LogoutService;
import edu.pitt.cs.mips.coursemirror.core.Course;
import edu.pitt.cs.mips.coursemirror.core.Question;
import edu.pitt.cs.mips.coursemirror.ui.view.NonSwipeableViewPager;

import android.accounts.AccountsException;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ReflectionWritingFragment extends Fragment  {

	 static int NUM_ITEMS = 5;

    MyAdapter mAdapter;

    NonSwipeableViewPager mPager;
    static ReflectionWritingFragment self;
    
	
	public static class MyAdapter extends FragmentPagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public Fragment getItem(int position) {
            return TestFragment.newInstance(position);
        }
    }
	
	
	public static class TestFragment extends Fragment {
		
	    private static final String KEY_CONTENT = "TestFragment:Content";
	    private String mContent = "???";
	    static String token="";
    	EditText answer;
    	TextView question;
    	TextView description;
    	Button back;
    	Button next;
    	Button start;
    	private final TextWatcher watcher = validationTextWatcher();

	    
	    public static TestFragment newInstance(int position) {
	        TestFragment fragment = new TestFragment();
	        fragment.mContent=Integer.toString(position);
	        return fragment;
	    }

	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        if ((savedInstanceState != null) && savedInstanceState.containsKey(KEY_CONTENT)) {
	            mContent = savedInstanceState.getString(KEY_CONTENT);
	        }
	    }
	    
	    @Override
		 public void onActivityCreated(Bundle savedInstanceState) {
	        super.onActivityCreated(savedInstanceState);
//	        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//	        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
	    }
	    
	    @Override
		 public void onResume() {
	        super.onResume();
	        //Log.v("newCreatedResume","new page");
	        View newV = getView();
	        EditText et = (EditText)newV.findViewById(R.id.et_reflectionAnswer);       
	        et.setText(CourseMirrorModule.getAnswers().get(Integer.parseInt(mContent)));
	        updateUIWithValidation();
//	        this.getActivity().getWindow().setSoftInputMode(
//  			      WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//	        Log.v("first page", "first page");
//	        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//	        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
	    }
	    
	    
//	    @Override
//	    public void onPause(){
//	    	super.onPause();
//	    	View newV = getView();
//	    	EditText et = (EditText)newV.findViewById(R.id.et_reflectionAnswer);
//	    	CourseMirrorModule.getAnswers().set(Integer.parseInt(mContent), et.getText().toString());
//	    	Log.v("pause", mContent);
//	    }

	    @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	        
	    	View v = inflater.inflate(R.layout.reflection_writing_view, container, false);
	    	question = (TextView)v.findViewById(R.id.tv_question);
	    	description = (TextView)v.findViewById(R.id.tv_description);
	    	answer = (EditText)v.findViewById(R.id.et_reflectionAnswer);
	    	back = (Button)v.findViewById(R.id.b_reflectionBack);
	    	next = (Button)v.findViewById(R.id.b_reflectionNext);
	    	start = (Button)v.findViewById(R.id.b_reflectionStart);

	    	/**
	    	 * first page: show course and lecture number
	    	 */
	    	if(Integer.parseInt(mContent)==0){ 
	    		if(CourseMirrorModule.getLectureNo()==0){
	    			question.setText("Please first select course and lecture");
	    			answer.setVisibility(View.INVISIBLE);
	    			back.setVisibility(View.INVISIBLE);
	    			next.setVisibility(View.INVISIBLE);
	    			start.setVisibility(View.INVISIBLE);
	    			
	    		}
	    		else{
	    			question.setText("You are writing reflection for: "+CourseMirrorModule.getCID()+", Lecture "+Integer.toString(CourseMirrorModule.getLectureNo()));
	    			answer.setVisibility(View.INVISIBLE);
	    			back.setVisibility(View.INVISIBLE);
	    			next.setVisibility(View.INVISIBLE);
	    			start.setText("Start");
	    			start.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							ReflectionWritingFragment.setPage(1);
							
						}
					});
	    		}

	    		description.setVisibility(View.INVISIBLE);
	    	}
	    	/**
	    	 * second page, token input
	    	 */
	    	else if(Integer.parseInt(mContent)==1){
                question.setText(R.string.inputToken);
                description.setText(R.string.tokenDescription);
                start.setVisibility(View.INVISIBLE);
                answer.setLines(1);
                answer.setMaxLines(1);
                answer.setSingleLine();
                answer.addTextChangedListener(watcher);
                back.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						token=answer.getText().toString();
						CourseMirrorModule.getAnswers().set(Integer.parseInt(mContent), token);
						ReflectionWritingFragment.setPage(0);
						
						final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
				        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
						
					}
				});
                next.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						token=answer.getText().toString();
						CourseMirrorModule.getAnswers().set(Integer.parseInt(mContent), token);
						ReflectionWritingFragment.setPage(2);
						
					}
				});
	    	}
	    	/**
	    	 * last page, answer the last question and submit
	    	 */
	    	else if(Integer.parseInt(mContent)==NUM_ITEMS-1) {
            	question.setText(CourseMirrorModule.getQuestions().get(Integer.parseInt(mContent)).getQuestionMain());
                description.setText(CourseMirrorModule.getQuestions().get(Integer.parseInt(mContent)).getQuestionSub());
                answer.addTextChangedListener(watcher);
                next.setText("    Submit    ");
                next.setBackgroundResource(R.drawable.button_submit_background_states);
                start.setVisibility(View.INVISIBLE);
                next.setOnClickListener(new View.OnClickListener() {	
					@Override
					public void onClick(View v) {
						//submit the reflection
						((ActionBarActivity)getActivity()).setSupportProgressBarIndeterminateVisibility(true);
						CourseMirrorModule.getAnswers().set(Integer.parseInt(mContent), answer.getText().toString());
						ParseObject newReflection = new ParseObject("Reflection");
						newReflection.put("cid", CourseMirrorModule.getCID());
						newReflection.put("lecture_number", CourseMirrorModule.getLectureNo());
						for(int i=1;i<=CourseMirrorModule.getAnswers().size()-2;i++){
							newReflection.put("q"+Integer.toString(i), CourseMirrorModule.getAnswers().get(i+1));
						}

						newReflection.put("user", token);
						newReflection.saveInBackground(new SaveCallback() {
							   public void done(ParseException e) {
								     if (e == null) {
								      //SavedSuccessfully
								    	 ((ActionBarActivity)getActivity()).setSupportProgressBarIndeterminateVisibility(false);
								    	 final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
				    				        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
								    	 for(int i=0;i<CourseMirrorModule.getAnswers().size();i++){
												CourseMirrorModule.getAnswers().set(i, " ");
							               	}
									     showToast(R.string.submit_successfully);
									     CourseMirrorModule.setLectureNo(0);
									    
									     if (CourseMirrorModule.getPrevLecturePosition() % 2 != 0){
								    			CourseMirrorModule.getPrevLectureView().setBackgroundResource(drawable.table_background_alternate_selector);
								    	 }
								    	 else{
								    			CourseMirrorModule.getPrevLectureView().setBackgroundResource(drawable.table_background_selector);
								    	 }
									     CourseMirrorModule.setPrevLecturePosition(-1);
									     CourseMirrorModule.setPrevLectureView(null);
									     
									     
									     Handler handler = new Handler(); 
						               	 handler.postDelayed(new Runnable() { 
						               	         public void run() { 
						               	        	CarouselFragment.setPage(1);
						               	        	self.setPage(0);
						               	        	
						               	         } 
						               	    }, 2000); 	
								    	 
								     } else {
								       //SaveDidNotSucceed
								    	 ((ActionBarActivity)getActivity()).setSupportProgressBarIndeterminateVisibility(false);
								    	 showToast(R.string.submit_unsuccessfully);
								     }
								   }
								 });
								
					}
				});
                back.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						CourseMirrorModule.getAnswers().set(Integer.parseInt(mContent), answer.getText().toString());
						ReflectionWritingFragment.setPage(Integer.parseInt(mContent)-1);
						
					}
				});
	    	}
	    	else{
	    		start.setVisibility(View.INVISIBLE);
	    		question.setText(CourseMirrorModule.getQuestions().get(Integer.parseInt(mContent)).getQuestionMain());
                description.setText(CourseMirrorModule.getQuestions().get(Integer.parseInt(mContent)).getQuestionSub());
                answer.addTextChangedListener(watcher);
                next.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						CourseMirrorModule.getAnswers().set(Integer.parseInt(mContent), answer.getText().toString());
						ReflectionWritingFragment.setPage(Integer.parseInt(mContent)+1);
						
					}
				});
                back.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						CourseMirrorModule.getAnswers().set(Integer.parseInt(mContent), answer.getText().toString());
						ReflectionWritingFragment.setPage(Integer.parseInt(mContent)-1);
						
						}
					});
	    		}
    	
	    	return v;
//        if (result != null) {
//            result.setArguments(new Bundle()); //TODO do we need this?
//        }
	   
	    }

	    @Override
	    public void onSaveInstanceState(Bundle outState) {
	        super.onSaveInstanceState(outState);
	        outState.putString(KEY_CONTENT, mContent);
	    }
	    
	    private TextWatcher validationTextWatcher() {
	        return new TextWatcherAdapter() {
	            public void afterTextChanged(final Editable gitDirEditText) {
	                updateUIWithValidation();
	            }

	        };
	    }
	    private void updateUIWithValidation() {
	    	if(Integer.parseInt(mContent)==1){//token page
	    		final boolean enable_next = validToken(answer);
	    	    next.setEnabled(enable_next);
	    	}
	    	else if (Integer.parseInt(mContent)==NUM_ITEMS-1){//last page, submit
	    		final boolean enable_submit = validAnswer(answer);
	    		next.setEnabled(enable_submit);
	    		
	    	}
	    	else{
	    		final boolean enable_next = validAnswer(answer);
	    		next.setEnabled(enable_next);
	    	}
	    }
	    
	    private boolean validToken(final EditText editText) {
	    	String token = editText.getText().toString();
	    	token = token.trim();
	    	
	        return (token.length()==5 && Character.isLetter(token.charAt(0)) && Character.isDigit(token.charAt(1)) &&
	        		Character.isDigit(token.charAt(2)) && Character.isDigit(token.charAt(3)) && Character.isDigit(token.charAt(4)));
	    }
	    private boolean validAnswer(final EditText editText) {
	        return editText.length() > 1;
	    }
	    
	    protected void showToast(int id) {
	    	CharSequence text = getString(id);
	        Toast.makeText(this.getActivity(), text, Toast.LENGTH_LONG).show();
	      }
	}
	
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_test, container, false);
    }

	
	@Override
	 public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //setHasOptionsMenu(true);   
       // Log.v("createReflectionFragment", "create");
       NUM_ITEMS=CourseMirrorModule.getQuestions().size();
       
        self=this;
        View view =  getView();

        mAdapter = new MyAdapter(getChildFragmentManager());

        mPager = (NonSwipeableViewPager)view.findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);
        

        CirclePageIndicator mIndicator = (CirclePageIndicator)view.findViewById(R.id.indicator);
        mIndicator.setViewPager(mPager);
        
        final float density = getResources().getDisplayMetrics().density;
        //mIndicator.setBackgroundColor(0xFFFFFFFF);
        //mIndicator.setRadius(10 * density);
        mIndicator.setPageColor(0xFFFFFFFF);
        mIndicator.setFillColor(0xFF000000);
        mIndicator.setStrokeColor(0xFF888888);
        //mIndicator.setStrokeWidth(2 * density);
    }
	static public void setPage(int page)
    {
    	self.mPager.setCurrentItem(page);
    }

}
