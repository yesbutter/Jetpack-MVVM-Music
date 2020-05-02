package com.lhg.puremusic.ui.page;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lhg.puremusic.BR;
import com.lhg.puremusic.R;
import com.lhg.puremusic.bridge.request.InfoRequestViewModel;
import com.lhg.puremusic.bridge.state.DrawerViewModel;
import com.lhg.puremusic.ui.base.BaseFragment;
import com.lhg.puremusic.ui.base.DataBindingConfig;
import com.lhg.puremusic.ui.page.adapter.DrawerAdapter;

/**
 * Create by lhg at 19/10/29
 */
public class DrawerFragment extends BaseFragment {

    private DrawerViewModel mDrawerViewModel;
    private InfoRequestViewModel mInfoRequestViewModel;

    @Override
    protected void initViewModel() {
        mInfoRequestViewModel = getFragmentViewModel(InfoRequestViewModel.class);
        mDrawerViewModel = getFragmentViewModel(DrawerViewModel.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {

        //TODO tip: DataBinding 严格模式：
        // 将 DataBinding 实例限制于 base 页面中，默认不向子类暴露，
        // 通过这样的方式，来彻底解决 视图调用的一致性问题，
        // 如此，视图刷新的安全性将和基于函数式编程的 Jetpack Compose 持平。
        // 而 DataBindingConfig 就是在这样的背景下，用于为 base 页面中的 DataBinding 提供绑定项。


        return new DataBindingConfig(R.layout.fragment_drawer, mDrawerViewModel)
                .addBindingParam(BR.click, new ClickProxy())
                .addBindingParam(BR.adapter, new DrawerAdapter(getContext()));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mInfoRequestViewModel.getLibraryLiveData().observe(getViewLifecycleOwner(), libraryInfos -> {
            mInitDataCame = true;
            if (mAnimationLoaded && libraryInfos != null) {
                mDrawerViewModel.list.setValue(libraryInfos);
            }
        });

        mInfoRequestViewModel.requestLibraryInfo();
    }

    @Override
    public void loadInitData() {
        super.loadInitData();
        if (mInfoRequestViewModel.getLibraryLiveData().getValue() != null) {
            mDrawerViewModel.list.setValue(mInfoRequestViewModel.getLibraryLiveData().getValue());
        }
    }

    public class ClickProxy {

        public void logoClick() {
            String u = "https://github.com/lhg/Jetpack-MVVM-Best-Practice";
            Uri uri = Uri.parse(u);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }

}
