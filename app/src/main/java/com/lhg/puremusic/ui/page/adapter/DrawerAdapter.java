package com.lhg.puremusic.ui.page.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.recyclerview.widget.RecyclerView;

import com.lhg.architecture.ui.adapter.SimpleBaseBindingAdapter;
import com.lhg.puremusic.R;
import com.lhg.puremusic.data.bean.LibraryInfo;
import com.lhg.puremusic.databinding.AdapterLibraryBinding;

/**
 * Create by lhg at 20/4/19
 */
public class DrawerAdapter extends SimpleBaseBindingAdapter<LibraryInfo, AdapterLibraryBinding> {

    public DrawerAdapter(Context context) {
        super(context, R.layout.adapter_library);
    }

    @Override
    protected void onSimpleBindItem(AdapterLibraryBinding binding, LibraryInfo item, RecyclerView.ViewHolder holder) {
        binding.setInfo(item);
        binding.getRoot().setOnClickListener(v -> {
            Uri uri = Uri.parse(item.getUrl());
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            binding.getRoot().getContext().startActivity(intent);
        });
    }
}
