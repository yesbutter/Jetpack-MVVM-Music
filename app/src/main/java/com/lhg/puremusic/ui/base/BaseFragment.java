package com.lhg.puremusic.ui.base;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.lhg.architecture.data.manager.NetState;
import com.lhg.architecture.data.manager.NetworkStateManager;
import com.lhg.puremusic.App;
import com.lhg.puremusic.BR;
import com.lhg.puremusic.R;
import com.lhg.puremusic.bridge.callback.SharedViewModel;


/**
 * Create by lhg at 19/7/11
 */
public abstract class BaseFragment extends Fragment {

    private static final Handler HANDLER = new Handler();
    protected AppCompatActivity mActivity;
    private SharedViewModel mSharedViewModel;
    protected boolean mAnimationEnterLoaded;
    protected boolean mAnimationLoaded;
    protected boolean mInitDataCame;
    private ViewModelProvider mFragmentProvider;
    private ViewModelProvider mActivityProvider;
    private ViewDataBinding mBinding;
    private TextView mTvStrictModeTip;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mActivity = (AppCompatActivity) context;
    }

    protected abstract void initViewModel();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSharedViewModel = ((App) mActivity.getApplicationContext()).getAppViewModelProvider(mActivity).get(SharedViewModel.class);

        initViewModel();

        //TODO 注意 liveData 的 lambda 回调中不可为空，不然会出现 Cannot add the same observer with different lifecycles 的现象，
        NetworkStateManager.getInstance().networkStateCallback.observe(this, this::onNetworkStateChanged);
    }

    @SuppressWarnings("EmptyMethod")
    protected void onNetworkStateChanged(NetState netState) {
        //TODO 子类可以重写该方法，统一的网络状态通知和处理
    }

    protected abstract DataBindingConfig getDataBindingConfig();

    /**
     * TODO tip: 警惕使用。非必要情况下，尽可能不在子类中拿到 binding 实例乃至获取 view 实例。使用即埋下隐患。
     * 目前的方案是在 debug 模式下，对获取实例的情况给予提示。
     * <p>
     *
     * @return
     */
    protected ViewDataBinding getBinding() {
        if (isDebug() && mBinding != null) {
            if (mTvStrictModeTip == null) {
                mTvStrictModeTip = new TextView(getContext());
                mTvStrictModeTip.setAlpha(0.5f);
                mTvStrictModeTip.setTextSize(16);
                mTvStrictModeTip.setBackgroundColor(Color.WHITE);
                mTvStrictModeTip.setText(R.string.debug_fragment_databinding_warning);
                ((ViewGroup) mBinding.getRoot()).addView(mTvStrictModeTip);
            }
        }
        return mBinding;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        DataBindingConfig dataBindingConfig = getDataBindingConfig();

        //TODO tip: DataBinding 严格模式：
        // 将 DataBinding 实例限制于 base 页面中，默认不向子类暴露，
        // 通过这样的方式，来彻底解决 视图调用的一致性问题，
        // 如此，视图刷新的安全性将和基于函数式编程的 Jetpack Compose 持平。


        ViewDataBinding binding = DataBindingUtil.inflate(inflater, dataBindingConfig.getLayout(), container, false);
        binding.setLifecycleOwner(this);
        binding.setVariable(BR.vm, dataBindingConfig.getStateViewModel());
        SparseArray bindingParams = dataBindingConfig.getBindingParams();
        for (int i = 0, length = bindingParams.size(); i < length; i++) {
            binding.setVariable(bindingParams.keyAt(i), bindingParams.valueAt(i));
        }
        mBinding = binding;
        return binding.getRoot();
    }

    @Nullable
    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        HANDLER.postDelayed(() -> {
            mAnimationLoaded = true;
            if (mInitDataCame && !mAnimationEnterLoaded) {
                mAnimationEnterLoaded = true;
                loadInitData();
            }
        }, 280);
        return super.onCreateAnimation(transit, enter, nextAnim);
    }

    protected void loadInitData() {

    }

    public boolean isDebug() {
        return mActivity.getApplicationContext().getApplicationInfo() != null &&
                (mActivity.getApplicationContext().getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
    }

    protected void showLongToast(String text) {
        Toast.makeText(mActivity.getApplicationContext(), text, Toast.LENGTH_LONG).show();
    }

    protected void showShortToast(String text) {
        Toast.makeText(mActivity.getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }

    protected void showLongToast(int stringRes) {
        showLongToast(mActivity.getApplicationContext().getString(stringRes));
    }

    protected void showShortToast(int stringRes) {
        showShortToast(mActivity.getApplicationContext().getString(stringRes));
    }

    protected <T extends ViewModel> T getFragmentViewModel(@NonNull Class<T> modelClass) {
        if (mFragmentProvider == null) {
            mFragmentProvider = new ViewModelProvider(this);
        }
        return mFragmentProvider.get(modelClass);
    }

    protected <T extends ViewModel> T getActivityViewModel(@NonNull Class<T> modelClass) {
        if (mActivityProvider == null) {
            mActivityProvider = new ViewModelProvider(mActivity);
        }
        return mActivityProvider.get(modelClass);
    }

    protected NavController nav() {
        return NavHostFragment.findNavController(this);
    }

    public SharedViewModel getSharedViewModel() {
        return mSharedViewModel;
    }

}
