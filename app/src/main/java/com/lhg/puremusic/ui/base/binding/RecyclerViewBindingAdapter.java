package com.lhg.puremusic.ui.base.binding;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.lhg.architecture.ui.adapter.BaseBindingAdapter;

import java.util.List;

/**
 * Create by lhg at 20/4/18
 */
public class RecyclerViewBindingAdapter {

    @BindingAdapter(value = {"adapter"})
    public static void setAdapter(RecyclerView recyclerView, RecyclerView.Adapter adapter) {
        if (recyclerView != null && adapter != null) {
            recyclerView.setAdapter(adapter);
        }
    }

    @BindingAdapter(value = {"refreshList"})
    public static void refreshList(RecyclerView recyclerView, List list) {
        if (list != null) {
            ((BaseBindingAdapter) recyclerView.getAdapter()).setList(list);
            
            //TODO 此处可通过 diffUtil 进一步优化用户体验
            recyclerView.getAdapter().notifyDataSetChanged();
        }
    }
}
