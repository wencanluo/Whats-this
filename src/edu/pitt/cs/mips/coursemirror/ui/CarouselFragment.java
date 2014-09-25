package edu.pitt.cs.mips.coursemirror.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.pitt.cs.mips.coursemirror.CourseMirrorModule;
import edu.pitt.cs.mips.coursemirror.R;
import com.viewpagerindicator.TitlePageIndicator;


/**
 * Fragment which houses the View pager.
 */
public class CarouselFragment extends Fragment {

    protected TitlePageIndicator indicator;
    protected ViewPager pager;
    
    static private CarouselFragment self;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_carousel, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        self = this;
        View view =  getView();
        

        indicator = (TitlePageIndicator ) view.findViewById(R.id.tpi_header);

        pager = (ViewPager) view.findViewById(R.id.vp_pages);

        pager.setAdapter(new CourseMirrorPagerAdapter(getResources(), getChildFragmentManager()));
        indicator.setViewPager(pager);
        
        pager.setCurrentItem(0);


    }
    
    static public void setPage(int page)
    {
    	self.pager.setCurrentItem(page);
    }
}