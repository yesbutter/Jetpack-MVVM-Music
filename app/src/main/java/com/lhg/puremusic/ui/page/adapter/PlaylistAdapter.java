package com.lhg.puremusic.ui.page.adapter;

import android.content.Context;
import android.graphics.Color;

import androidx.recyclerview.widget.RecyclerView;

import com.lhg.architecture.ui.adapter.SimpleBaseBindingAdapter;
import com.lhg.puremusic.R;
import com.lhg.puremusic.data.bean.TestAlbum;
import com.lhg.puremusic.databinding.AdapterPlayItemBinding;
import com.lhg.puremusic.player.PlayerManager;

/**
 * Create by lhg at 20/4/19
 */
public class PlaylistAdapter extends SimpleBaseBindingAdapter<TestAlbum.TestMusic, AdapterPlayItemBinding> {

    public PlaylistAdapter(Context context) {
        super(context, R.layout.adapter_play_item);
    }

    @Override
    protected void onSimpleBindItem(AdapterPlayItemBinding binding, TestAlbum.TestMusic item, RecyclerView.ViewHolder holder) {
        binding.setAlbum(item);
        int currentIndex = PlayerManager.getInstance().getAlbumIndex();
        binding.ivPlayStatus.setColor(currentIndex == holder.getAdapterPosition()
                ? binding.getRoot().getContext().getResources().getColor(R.color.gray) : Color.TRANSPARENT);
        binding.getRoot().setOnClickListener(v -> {
            PlayerManager.getInstance().playAudio(holder.getAdapterPosition());
        });
    }
}
