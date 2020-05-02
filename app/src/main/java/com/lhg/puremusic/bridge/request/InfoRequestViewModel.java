package com.lhg.puremusic.bridge.request;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.lhg.puremusic.data.bean.LibraryInfo;
import com.lhg.puremusic.data.repository.HttpRequestManager;

import java.util.List;

/**
 * 信息列表 Request-ViewModel
 *
 * TODO tip：Request-ViewModel 通常按业务划分
 * 一个项目中通常存在多个 Request-ViewModel
 *
 * request-ViewModel 的职责仅限于 数据请求，不建议在此处理 UI 逻辑，
 * UI 逻辑只适合在 Activity/Fragment 等视图控制器中完成，是 “数据驱动” 的一部分，
 * 将来升级到 Jetpack Compose 更是如此。
 *
 *
 *
 * Create by lhg at 19/11/2
 */
public class InfoRequestViewModel extends ViewModel {

    private MutableLiveData<List<LibraryInfo>> mLibraryLiveData;

    //TODO tip 向 ui 层提供的 request LiveData，使用抽象的 LiveData 而不是 MutableLiveData
    // 如此是为了来自数据层的数据，在 ui 层中只读，以避免团队新手不可预期的误用

    public LiveData<List<LibraryInfo>> getLibraryLiveData() {
        if (mLibraryLiveData == null) {
            mLibraryLiveData = new MutableLiveData<>();
        }
        return mLibraryLiveData;
    }

    public void requestLibraryInfo() {
        HttpRequestManager.getInstance().getLibraryInfo(mLibraryLiveData);
    }
}
