package com.lhg.puremusic.ui.base;

import android.util.SparseArray;

import androidx.lifecycle.ViewModel;

/**
 * TODO tip:
 *  将 DataBinding 实例限制于 base 页面中，默认不向子类暴露，
 *  通过这样的方式，来彻底解决 视图调用的一致性问题，
 *  如此，视图刷新的安全性将和基于函数式编程的 Jetpack Compose 持平。
 *  而 DataBindingConfig 就是在这样的背景下，用于为 base 页面中的 DataBinding 提供绑定项。
 *
 *
 * Create by lhg at 20/4/18
 */
public class DataBindingConfig {

    private int layout;

    private ViewModel stateViewModel;

    private SparseArray bindingParams = new SparseArray();

    public DataBindingConfig(int layout, ViewModel stateViewModel) {
        this.layout = layout;
        this.stateViewModel = stateViewModel;
    }

    public int getLayout() {
        return layout;
    }

    public ViewModel getStateViewModel() {
        return stateViewModel;
    }

    public SparseArray getBindingParams() {
        return bindingParams;
    }

    public DataBindingConfig addBindingParam(int variableId, Object object) {
        if (bindingParams.get(variableId) == null) {
            bindingParams.put(variableId, object);
        }
        return this;
    }
}
