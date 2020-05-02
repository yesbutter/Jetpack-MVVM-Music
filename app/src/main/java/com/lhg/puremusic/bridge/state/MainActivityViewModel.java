package com.lhg.puremusic.bridge.state;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * TODO tip：每个页面都要单独准备一个 state-ViewModel，
 * 来托管 DataBinding 绑定的临时状态，以及视图控制器重建时状态的恢复。
 *
 * 此外，state-ViewModel 的职责仅限于 状态托管，不建议在此处理 UI 逻辑，
 * UI 逻辑只适合在 Activity/Fragment 等视图控制器中完成，是 “数据驱动” 的一部分，
 * 将来升级到 Jetpack Compose 更是如此。
 *
 *
 * Create by lhg at 19/10/29
 */
public class MainActivityViewModel extends ViewModel {

    //TODO 演示 LiveData 来用作 DataBinding 数据绑定的情况。
    // 记得在视图控制器中要加入 mBinding.setLifecycleOwner(this);

    public final MutableLiveData<Boolean> openDrawer = new MutableLiveData<>();

    public final MutableLiveData<Boolean> allowDrawerOpen = new MutableLiveData<>();

    public final MutableLiveData<Boolean> listenDrawerState = new MutableLiveData<>();

    {
        listenDrawerState.setValue(true);
        allowDrawerOpen.setValue(true);
        openDrawer.setValue(false);
    }
}
