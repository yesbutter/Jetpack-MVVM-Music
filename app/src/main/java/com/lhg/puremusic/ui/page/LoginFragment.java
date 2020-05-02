package com.lhg.puremusic.ui.page;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lhg.architecture.data.manager.NetState;
import com.lhg.architecture.utils.SPUtils;
import com.lhg.puremusic.BR;
import com.lhg.puremusic.R;
import com.lhg.puremusic.bridge.request.AccountRequestViewModel;
import com.lhg.puremusic.bridge.state.LoginViewModel;
import com.lhg.puremusic.data.bean.User;
import com.lhg.puremusic.data.config.Configs;
import com.lhg.puremusic.ui.base.BaseFragment;
import com.lhg.puremusic.ui.base.DataBindingConfig;
import com.lhg.puremusic.ui.helper.DrawerCoordinateHelper;

/**
 * Create by lhg at 20/04/26
 */
public class LoginFragment extends BaseFragment {

    private LoginViewModel mLoginViewModel;
    private AccountRequestViewModel mAccountRequestViewModel;

    @Override
    protected void initViewModel() {
        mLoginViewModel = getFragmentViewModel(LoginViewModel.class);
        mAccountRequestViewModel = getFragmentViewModel(AccountRequestViewModel.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {

        //TODO tip: DataBinding 严格模式：
        // 将 DataBinding 实例限制于 base 页面中，默认不向子类暴露，
        // 通过这样的方式，来彻底解决 视图调用的一致性问题，
        // 如此，视图刷新的安全性将和基于函数式编程的 Jetpack Compose 持平。
        // 而 DataBindingConfig 就是在这样的背景下，用于为 base 页面中的 DataBinding 提供绑定项。


        return new DataBindingConfig(R.layout.fragment_login, mLoginViewModel)
                .addBindingParam(BR.click, new ClickProxy());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getLifecycle().addObserver(DrawerCoordinateHelper.getInstance());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //TODO tip: request-ViewModel 和 state-ViewModel 边界分明、点到为止、各司其职，

        mAccountRequestViewModel.getTokenLiveData().observe(getViewLifecycleOwner(), s -> {
            SPUtils.getInstance().put(Configs.TOKEN, s);
            mLoginViewModel.loadingVisible.set(false);

            //TODO 登录成功后进行的下一步操作...
            nav().navigateUp();
        });
    }

    //TODO tip: 网络状态的通知统一埋在 base 页面，有需要就在子类页面中重写

    @Override
    protected void onNetworkStateChanged(NetState netState) {
        super.onNetworkStateChanged(netState);

        mLoginViewModel.loadingVisible.set(false);
        if (!netState.isSuccess()) {
            showLongToast("网络状态不佳，请重试");
        }
    }

    public class ClickProxy {

        public void back() {
            nav().navigateUp();
        }

        public void login() {

            //TODO 通过 xml 中的双向绑定，使得能够通过 stateViewModel 中与控件发生绑定的可观察数据 拿到控件的数据。避免直接接触控件实例而埋下一致性隐患。

            if (TextUtils.isEmpty(mLoginViewModel.name.get()) || TextUtils.isEmpty(mLoginViewModel.password.get())) {
                showLongToast("用户名或密码不完整");
                return;
            }
            User user = new User(mLoginViewModel.name.get(), mLoginViewModel.password.get());
            mAccountRequestViewModel.requestLogin(user);
            mLoginViewModel.loadingVisible.set(true);
        }

    }

}
