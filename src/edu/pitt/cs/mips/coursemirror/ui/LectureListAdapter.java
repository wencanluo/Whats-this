package edu.pitt.cs.mips.coursemirror.ui;

import android.util.Log;
import android.view.LayoutInflater;

import edu.pitt.cs.mips.coursemirror.CourseMirrorModule;
import edu.pitt.cs.mips.coursemirror.R;
import edu.pitt.cs.mips.coursemirror.R.drawable;
import edu.pitt.cs.mips.coursemirror.core.Lecture;

import java.util.List;

public class LectureListAdapter extends AlternatingColorListAdapter<Lecture> {
    /**
     * @param inflater
     * @param items
     * @param selectable
     */
    public LectureListAdapter(final LayoutInflater inflater, final List<Lecture> items,
                           final boolean selectable) {
        super(R.layout.lecture_list_item, inflater, items, selectable);
    }

    /**
     * @param inflater
     * @param items
     */
    public LectureListAdapter(final LayoutInflater inflater, final List<Lecture> items) {
        super(R.layout.lecture_list_item, inflater, items);
    }

    @Override
    protected int[] getChildViewIds() {
        return new int[]{R.id.lecture_title, R.id.lecture_summary,
                R.id.lecture_date};
    }

    @Override
    protected void update(final int position, final Lecture item) {
        super.update(position, item);

        setText(0, "Lecture " + item.getnumber());
        setText(1, item.getTitle());
        setText(2, item.getdate());
        
        //highlight the current lecture item 
        if(CourseMirrorModule.getLectureNo()==item.getnumber()){
        	updater.view.setBackgroundResource(drawable.button_background_enabled);
        	CourseMirrorModule.setPrevLectureView(updater.view);
        	//Log.v("updatePosition", Integer.toString(position));
        	CourseMirrorModule.setPrevLecturePosition(position+1);
        }
        
        //setNumber(R.id.tv_date, item.getCreatedAt());
    }
}
