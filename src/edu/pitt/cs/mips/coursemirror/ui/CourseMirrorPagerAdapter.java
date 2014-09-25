

package edu.pitt.cs.mips.coursemirror.ui;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import edu.pitt.cs.mips.coursemirror.CourseMirrorModule;
import edu.pitt.cs.mips.coursemirror.R;

/**
 * Pager adapter
 */
public class CourseMirrorPagerAdapter extends FragmentPagerAdapter {

    private final Resources resources;

    /**
     * Create pager adapter
     *
     * @param resources
     * @param fragmentManager
     */
    public CourseMirrorPagerAdapter(final Resources resources, final FragmentManager fragmentManager) {
        super(fragmentManager);
        this.resources = resources;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Fragment getItem(final int position) {
        final Fragment result;
        switch (position) {
            case 0:
                result = new CourseListFragment();
                //CourseMirrorModule.setCID(null);
                break;
            case 1:
                result = new LectureListFragment();
               // CourseMirrorModule.setLectureNo(0);
                break;
            case 2:
//                result = new ReflectionListFragment();
               // result = new ReflectionFragment();
            	result = new ReflectionWritingFragment();
                break;
            default:
                result = null;
                break;
        }
        if (result != null) {
            result.setArguments(new Bundle()); //TODO do we need this?
        }
        return result;
    }

    @Override
    public CharSequence getPageTitle(final int position) {
        switch (position) {
            case 0:
                return resources.getString(R.string.page_course);
            case 1:
                return resources.getString(R.string.page_lecture);
            case 2:
                return resources.getString(R.string.page_reflection);
            default:
                return null;
        }
    }
}
