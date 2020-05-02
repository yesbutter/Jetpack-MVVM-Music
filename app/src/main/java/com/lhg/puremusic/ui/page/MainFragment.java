package com.lhg.puremusic.ui.page;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lhg.puremusic.BR;
import com.lhg.puremusic.R;
import com.lhg.puremusic.bridge.request.MusicRequestViewModel;
import com.lhg.puremusic.bridge.state.MainViewModel;
import com.lhg.puremusic.player.PlayerManager;
import com.lhg.puremusic.ui.base.BaseFragment;
import com.lhg.puremusic.ui.base.DataBindingConfig;
import com.lhg.puremusic.ui.page.adapter.PlaylistAdapter;

/**
 * Create by lhg at 19/10/29
 */
public class MainFragment extends BaseFragment {

    private MainViewModel mMainViewModel;
    private MusicRequestViewModel mMusicRequestViewModel;

    @Override
    protected void initViewModel() {
        mMainViewModel = getFragmentViewModel(MainViewModel.class);
        mMusicRequestViewModel = getFragmentViewModel(MusicRequestViewModel.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {

        //TODO tip: DataBinding 严格模式：
        // 将 DataBinding 实例限制于 base 页面中，默认不向子类暴露，
        // 通过这样的方式，来彻底解决 视图调用的一致性问题，
        // 如此，视图刷新的安全性将和基于函数式编程的 Jetpack Compose 持平。
        // 而 DataBindingConfig 就是在这样的背景下，用于为 base 页面中的 DataBinding 提供绑定项。


        return new DataBindingConfig(R.layout.fragment_main, mMainViewModel)
                .addBindingParam(BR.click, new ClickProxy())
                .addBindingParam(BR.adapter, new PlaylistAdapter(getContext()));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        PlayerManager.getInstance().getChangeMusicLiveData().observe(getViewLifecycleOwner(), changeMusic -> {

            // TODO tip 1：所有播放状态的改变，都要通过这个 作为 唯一可信源 的 PlayerManager 来统一分发，

            // 如此才能方便 追溯事件源，以及 避免 不可预期的 推送和错误。

            mMainViewModel.list.setValue(PlayerManager.getInstance().getAlbum().getMusics());
        });

        mMusicRequestViewModel.getFreeMusicsLiveData().observe(getViewLifecycleOwner(), musicAlbum -> {
            if (musicAlbum != null && musicAlbum.getMusics() != null) {
                mMainViewModel.list.setValue(musicAlbum.getMusics());

                // TODO tip 4：未作 UnPeek 处理的 用于 request 的 LiveData，在视图控制器重建时会自动倒灌数据

                // 一定要记住这一点，因为如果没有妥善处理，这里就会出现预期外的错误，一定要记得它在重建时 是一定会倒灌的。


                if (PlayerManager.getInstance().getAlbum() == null ||
                        !PlayerManager.getInstance().getAlbum().getAlbumId().equals(musicAlbum.getAlbumId())) {
                    PlayerManager.getInstance().loadAlbum(musicAlbum);
                }
            }
        });

        if (PlayerManager.getInstance().getAlbum() == null) {
            mMusicRequestViewModel.requestFreeMusics();
        } else {
            mMainViewModel.list.setValue(PlayerManager.getInstance().getAlbum().getMusics());
        }
    }


    // TODO tip 2：此处通过 DataBinding 来规避 在 setOnClickListener 时存在的 视图调用的一致性问题，

    // 也即，有绑定就有绑定，没绑定也没什么大不了的，总之 不会因一致性问题造成 视图调用的空指针。

    public class ClickProxy {

        public void openMenu() {

            // TODO tip 3：此处演示通过 UnPeekLiveData 来发送 生命周期安全的、事件源可追溯的 通知。

           // --------
            // 与此同时，此处传达的另一个思想是 最少知道原则，
            // Activity 内部的事情在 Activity 内部消化，不要试图在 fragment 中调用和操纵 Activity 内部的东西。
            // 因为 Activity 端的处理后续可能会改变，并且可受用于更多的 fragment，而不单单是本 fragment。

            getSharedViewModel().openOrCloseDrawer.setValue(true);
        }

        public void login() {
            nav().navigate(R.id.action_mainFragment_to_loginFragment);
        }

        public void search() {
            nav().navigate(R.id.action_mainFragment_to_searchFragment);
        }

    }

}
