package com.lhg.architecture.ui.adapter;

import android.content.Context;

import androidx.annotation.LayoutRes;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author KunMinX
 * Create at 2018/6/30
 */
public abstract class SimpleBaseBindingAdapter<M, B extends ViewDataBinding> extends BaseBindingAdapter {

    private final int layout;

    public SimpleBaseBindingAdapter(Context context, int layout) {
        super(context);
        this.layout = layout;
    }

    @Override
    protected @LayoutRes
    int getLayoutResId(int viewType) {
        return this.layout;
    }

    protected abstract void onSimpleBindItem(B binding, M item, RecyclerView.ViewHolder holder);

    @Override
    protected void onBindItem(ViewDataBinding binding, Object item, RecyclerView.ViewHolder holder) {
        //noinspection unchecked
        onSimpleBindItem((B) binding, (M) item, holder);
    }
}