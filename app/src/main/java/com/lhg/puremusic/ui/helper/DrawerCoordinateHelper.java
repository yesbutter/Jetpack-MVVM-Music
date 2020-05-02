package com.lhg.puremusic.ui.helper;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import com.lhg.puremusic.bridge.callback.SharedViewModel;
import com.lhg.puremusic.ui.base.BaseFragment;

/**
 * TODO tip：通过 Lifecycle 来解决抽屉侧滑禁用与否的判断的 一致性问题，
 *
 * 每个需要注册和监听生命周期来判断的视图控制器，无需在各自内部手动书写解绑等操作。
 *
 * Create by lhg at 19/11/3
 */
public class DrawerCoordinateHelper implements DefaultLifecycleObserver {

    private static final DrawerCoordinateHelper S_HELPER = new DrawerCoordinateHelper();

    private DrawerCoordinateHelper() {
    }

    public static DrawerCoordinateHelper getInstance() {
        return S_HELPER;
    }

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {

        SharedViewModel.TAG_OF_SECONDARY_PAGES.add(owner.getClass().getSimpleName());

        ((BaseFragment) owner).getSharedViewModel()
                .enableSwipeDrawer.setValue(SharedViewModel.TAG_OF_SECONDARY_PAGES.size() == 0);
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {

        SharedViewModel.TAG_OF_SECONDARY_PAGES.remove(owner.getClass().getSimpleName());

        ((BaseFragment) owner).getSharedViewModel()
                .enableSwipeDrawer.setValue(SharedViewModel.TAG_OF_SECONDARY_PAGES.size() == 0);
    }

}
