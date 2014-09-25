package edu.pitt.cs.mips.coursemirror.ui;

import static edu.pitt.cs.mips.coursemirror.util.Constants.Extra.REFLECTION_ITEM;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.ListView;
import edu.pitt.cs.mips.coursemirror.CourseMirrorModule;
import edu.pitt.cs.mips.coursemirror.CourseMirrorServiceProvider;
import edu.pitt.cs.mips.coursemirror.R;
import edu.pitt.cs.mips.coursemirror.authenticator.LogoutService;
import edu.pitt.cs.mips.coursemirror.core.Lecture;
import edu.pitt.cs.mips.coursemirror.core.Reflection;

import com.github.kevinsawicki.wishlist.SingleTypeAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReflectionListFragment extends ItemListFragment<Reflection> {

    protected CourseMirrorServiceProvider serviceProvider;
    protected LogoutService logoutService;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        serviceProvider = CourseMirrorModule.getCourseMirrorServiceProvider();
        logoutService =  CourseMirrorModule.getLogoutService();
    }

    @Override
    protected void configureList(final Activity activity, final ListView listView) {
        super.configureList(activity, listView);

        listView.setFastScrollEnabled(true);
        listView.setDividerHeight(0);

        getListAdapter()
                .addHeader(activity.getLayoutInflater()
                        .inflate(R.layout.reflection_list_item_labels, null));
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

    List<Reflection> reflectionHelper()
    {
    	ArrayList<Reflection> list = new ArrayList<Reflection>();
    	
    	Reflection reflection1 = new Reflection();
    	reflection1.setq1("Lecture 1 is too boring");
    	list.add(reflection1);

    	Reflection reflection2 = new Reflection();
    	reflection2.setq1("Can not have access to ACM Digital Library");
    	list.add(reflection2);
    	
    	Reflection reflection3 = new Reflection();
    	reflection3.setq1("The pace is too fast");
    	list.add(reflection3);
    	
    	return list;
    }
    
    
    @Override
    public Loader<List<Reflection>> onCreateLoader(final int id, final Bundle args) {
        final List<Reflection> initialItems = items;
        return new ThrowableLoader<List<Reflection>>(getActivity(), items) {

            @Override
            public List<Reflection> loadData() throws Exception {
                try {
                    if (getActivity() != null) {
                    	List<Reflection> reflections;
                    	reflections = serviceProvider.getService(getActivity()).getReflections();
                    	
                    	return reflections;
                    	
//                        return reflectionHelper();
                    } else {
                        return Collections.emptyList();
                    }
                } catch (final OperationCanceledException e) {
                    final Activity activity = getActivity();
                    if (activity != null)
                        activity.finish();
                    return initialItems;
                }
            }
        };
    }

    @Override
    protected SingleTypeAdapter<Reflection> createAdapter(final List<Reflection> items) {
        return new ReflectionListAdapter(getActivity().getLayoutInflater(), items);
    }

    public void onListItemClick(final ListView l, final View v, final int position, final long id) {
        final Reflection reflections = ((Reflection) l.getItemAtPosition(position));

        final String uri = "http://bit.ly/coursemirrorpitt";
/*
        // Show a chooser that allows the user to decide how to display this data, in this case, map data.
        startActivity(Intent.createChooser(
                new Intent(Intent.ACTION_VIEW, Uri.parse(uri)), getString(R.string.choose))
        );*/
        

        startActivity(new Intent(getActivity(), ReflectionActivity.class).putExtra("URL", uri));        
    }

    @Override
    protected int getErrorMessage(final Exception exception) {
        return R.string.error_loading_reflections;
    }
}
