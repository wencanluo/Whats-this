package edu.pitt.cs.mips.coursemirror.ui;

import android.view.LayoutInflater;

import edu.pitt.cs.mips.coursemirror.R;
import edu.pitt.cs.mips.coursemirror.core.Reflection;

import java.util.List;


public class ReflectionListAdapter extends AlternatingColorListAdapter<Reflection> {
    /**
     * @param inflater
     * @param items
     * @param selectable
     */
    public ReflectionListAdapter(final LayoutInflater inflater, final List<Reflection> items,
                               final boolean selectable) {
        super(R.layout.reflection_list_item, inflater, items, selectable);
    }

    /**
     * @param inflater
     * @param items
     */
    public ReflectionListAdapter(final LayoutInflater inflater, final List<Reflection> items) {
        super(R.layout.reflection_list_item, inflater, items);
    }

    @Override
    protected int[] getChildViewIds() {
        return new int[]{R.id.tv_name, R.id.tv_date};
    }

    @Override
    protected void update(final int position, final Reflection item) {
        super.update(position, item);

        setText(0, item.getq1());
    }
}
