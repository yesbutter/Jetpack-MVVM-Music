package com.lhg.puremusic.ui.page;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.library.baseAdapters.BR;

import com.lhg.puremusic.R;
import com.lhg.puremusic.bridge.request.DownloadRequestViewModel;
import com.lhg.puremusic.bridge.state.SearchViewModel;
import com.lhg.puremusic.ui.base.BaseFragment;
import com.lhg.puremusic.ui.base.DataBindingConfig;
import com.lhg.puremusic.ui.helper.DrawerCoordinateHelper;

/**
 * Create by lhg at 19/10/29
 */
public class SearchFragment extends BaseFragment {

    private SearchViewModel mSearchViewModel;
    private DownloadRequestViewModel mDownloadRequestViewModel;

    @Override
    protected void initViewModel() {
        mDownloadRequestViewModel = getActivityViewModel(DownloadRequestViewModel.class);
        mSearchViewModel = getFragmentViewModel(SearchViewModel.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {

        //TODO tip: DataBinding 严格模式：
        // 将 DataBinding 实例限制于 base 页面中，默认不向子类暴露，
        // 通过这样的方式，来彻底解决 视图调用的一致性问题，
        // 如此，视图刷新的安全性将和基于函数式编程的 Jetpack Compose 持平。
        // 而 DataBindingConfig 就是在这样的背景下，用于为 base 页面中的 DataBinding 提供绑定项。


        return new DataBindingConfig(R.layout.fragment_search, mSearchViewModel)
                .addBindingParam(BR.click, new ClickProxy());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getLifecycle().addObserver(DrawerCoordinateHelper.getInstance());

        //TODO tip1：绑定跟随视图控制器生命周期的、可叫停的、单独放在 UseCase 中处理的业务
        getLifecycle().addObserver(mDownloadRequestViewModel.getCanBeStoppedUseCase());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mDownloadRequestViewModel.getDownloadFileLiveData().observe(getViewLifecycleOwner(), downloadFile -> {
            mSearchViewModel.progress.set(downloadFile.getProgress());
        });

        mDownloadRequestViewModel.getDownloadFileCanBeStoppedLiveData().observe(getViewLifecycleOwner(), downloadFile -> {
            mSearchViewModel.progress.set(downloadFile.getProgress());
        });
    }

    public class ClickProxy {

        public void back() {
            nav().navigateUp();
        }

        public void testNav() {
            String u = "https://xiaozhuanlan.com/topic/5860149732";
            Uri uri = Uri.parse(u);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }

        public void subscribe() {
            String u = "https://xiaozhuanlan.com/lhg";
            Uri uri = Uri.parse(u);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }

        public void testDownload() {
            mDownloadRequestViewModel.requestDownloadFile();
        }

        //TODO tip2: 在 UseCase 中 执行可跟随生命周期中止的下载任务

        public void testLifecycleDownload() {
            mDownloadRequestViewModel.requestCanBeStoppedDownloadFile();
        }
    }
}
