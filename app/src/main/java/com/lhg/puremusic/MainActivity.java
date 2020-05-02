package com.lhg.puremusic;

import android.os.Bundle;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.lhg.puremusic.R;
import com.lhg.puremusic.bridge.callback.SharedViewModel;
import com.lhg.puremusic.bridge.state.MainActivityViewModel;
import com.lhg.puremusic.ui.base.BaseActivity;
import com.lhg.puremusic.ui.base.DataBindingConfig;

/**
 * Create by lhg at 19/10/16
 */

public class MainActivity extends BaseActivity {

    private MainActivityViewModel mMainActivityViewModel;
    private boolean mIsListened = false;

    @Override
    protected void initViewModel() {
        mMainActivityViewModel = getActivityViewModel(MainActivityViewModel.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {

        //TODO tip 1: DataBinding 严格模式：
        // 将 DataBinding 实例限制于 base 页面中，默认不向子类暴露，
        // 通过这样的方式，来彻底解决 视图调用的一致性问题，
        // 如此，视图刷新的安全性将和基于函数式编程的 Jetpack Compose 持平。
        // 而 DataBindingConfig 就是在这样的背景下，用于为 base 页面中的 DataBinding 提供绑定项。


        return new DataBindingConfig(R.layout.activity_main, mMainActivityViewModel);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSharedViewModel().activityCanBeClosedDirectly.observe(this, aBoolean -> {
            NavController nav = Navigation.findNavController(this, R.id.main_fragment_host);
            if (nav.getCurrentDestination() != null && nav.getCurrentDestination().getId() != R.id.mainFragment) {
                nav.navigateUp();

            } else if (SharedViewModel.IS_DRAWER_OPENED.get()) {

                //TODO 同 tip 2

                getSharedViewModel().openOrCloseDrawer.setValue(false);

            } else {
                super.onBackPressed();
            }
        });

        getSharedViewModel().openOrCloseDrawer.observe(this, aBoolean -> {

            //TODO yes：同 tip 1: 此处将 drawer 的 open 和 close 都放在 drawerBindingAdapter 中操作，规避了视图调用的一致性问题，

            //因为 横屏布局 根本就没有 drawerLayout。此处如果用传统的视图调用方式，会很容易因疏忽而造成空引用。

            //TODO 此外，此处为 drawerLayout 绑定的状态 "openDrawer"，使用 LiveData 而不是 ObservableField，主要是考虑到 ObservableField 具有 "防抖" 的特性，不适合该场景。


            mMainActivityViewModel.openDrawer.setValue(aBoolean);

            //TODO do not:（容易因疏忽 而埋下视图调用的一致性隐患）

            /*if (mBinding.dl != null) {
                if (aBoolean && !mBinding.dl.isDrawerOpen(GravityCompat.START)) {
                    mBinding.dl.openDrawer(GravityCompat.START);
                } else {
                    mBinding.dl.closeDrawer(GravityCompat.START);
                }
            }*/
        });

        getSharedViewModel().enableSwipeDrawer.observe(this, aBoolean -> {

            //TODO yes: 同 tip 1

            mMainActivityViewModel.allowDrawerOpen.setValue(aBoolean);

            // TODO do not:（容易因疏忽 而埋下视图调用的一致性隐患）

            /*if (mBinding.dl != null) {
                mBinding.dl.setDrawerLockMode(aBoolean
                        ? DrawerLayout.LOCK_MODE_UNLOCKED
                        : DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            }*/
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (!mIsListened) {

            // TODO tip 2：此处演示通过 UnPeekLiveData 来发送 生命周期安全的、事件源可追溯的 通知。

            // --------
            // 与此同时，此处传达的另一个思想是 最少知道原则，
            // fragment 内部的事情在 fragment 内部消化，不要试图在 Activity 中调用和操纵 Fragment 内部的东西。
            // 因为 fragment 端的处理后续可能会改变，并且可受用于更多的 Activity，而不单单是本 Activity。

            getSharedViewModel().timeToAddSlideListener.setValue(true);

            mIsListened = true;
        }
    }

    @Override
    public void onBackPressed() {

        // TODO 同 tip 2

        getSharedViewModel().closeSlidePanelIfExpanded.setValue(true);
    }
}
