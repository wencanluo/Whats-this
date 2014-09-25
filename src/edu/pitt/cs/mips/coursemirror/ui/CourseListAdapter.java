package edu.pitt.cs.mips.coursemirror.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import edu.pitt.cs.mips.coursemirror.CourseMirrorApplication;
import edu.pitt.cs.mips.coursemirror.CourseMirrorModule;
import edu.pitt.cs.mips.coursemirror.R;
import edu.pitt.cs.mips.coursemirror.R.drawable;
import edu.pitt.cs.mips.coursemirror.core.Course;

import com.github.kevinsawicki.wishlist.SingleTypeAdapter;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Adapter to display a list of traffic items
 */
public class CourseListAdapter extends SingleTypeAdapter<Course> {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MMMM dd");

    private Context mContext;
    /**
     * @param inflater
     * @param items
     */
    public CourseListAdapter(Context context, final LayoutInflater inflater, final List<Course> items) {
        super(inflater, R.layout.course_list_item);

        mContext = context;
        setItems(items);
    }

    /**
     * @param inflater
     */
    public CourseListAdapter(Context context, final LayoutInflater inflater) {
        this(context, inflater, null);

    }

    @Override
    public long getItemId(final int position) {
        final String id = getItem(position).getObjectId();
        return !TextUtils.isEmpty(id) ? id.hashCode() : super
                .getItemId(position);
    }

    @Override
    protected int[] getChildViewIds() {
        return new int[]{R.id.course_avatar, R.id.course_name};
    }

    @Override
    public View getView(final int position, View convertView,
        final ViewGroup parent) {
    	
      View view = super.getView(position,  convertView, parent);
      
      ImageView imageview = (ImageView)view.findViewById(R.id.course_detail);
      
      imageview.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View v) {
	        final int position = ((ListView)parent).getPositionForView(v);
	        if (position != ListView.INVALID_POSITION) {
	        	
	        	Log.i("CourseMIRROR", "Position = " + position);
	        	
	            final Course course = ((Course) ((ListView)parent).getItemAtPosition(position));

	            /*        startActivity(new Intent(getActivity(), CourseActivity.class).putExtra(USER, user));*/
	                    
	                    String uri = course.getURL();	        	
	            // Show a chooser that allows the user to decide how to display this data, in this case, map data.
	                    mContext.startActivity(Intent.createChooser(
	                    new Intent(Intent.ACTION_VIEW, Uri.parse(uri)), mContext.getString(R.string.choose) ));	        	

	        }
		}
    	  
      }
      );
      
      return view;
    }
    
    @Override
    protected void update(final int position, final Course course) {

        Picasso.with(CourseMirrorApplication.getInstance())
                .load("http://mips.lrdc.pitt.edu/coursemirror/coursemirror.png")
                .placeholder(R.drawable.ic_pitt)
                .into(imageView(0));
        

        setText(1, String.format("%1$s %2$s", course.getCID(), course.getTitle()));
        if(CourseMirrorModule.getCID()==course.getCID()){
        	//updater.view.setBackgroundResource(drawable.button_background_enabled);
        	imageView(0).setBackgroundResource(drawable.button_background_enabled);
//        	CourseMirrorModule.setPrevCourseView(updater.view);
//        	CourseMirrorModule.setPrevLecturePosition(position);
        }

    }

}
