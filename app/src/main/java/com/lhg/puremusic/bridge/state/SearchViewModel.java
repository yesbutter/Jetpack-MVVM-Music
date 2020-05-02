package com.lhg.puremusic.bridge.state;

import androidx.databinding.ObservableField;
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
public class SearchViewModel extends ViewModel {

    public final ObservableField<Integer> progress = new ObservableField<>();

}
