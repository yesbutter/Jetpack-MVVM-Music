package com.lhg.puremusic.data.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lhg.architecture.data.manager.NetState;
import com.lhg.architecture.data.manager.NetworkStateManager;
import com.lhg.architecture.utils.Utils;
import com.lhg.puremusic.R;
import com.lhg.puremusic.data.bean.DownloadFile;
import com.lhg.puremusic.data.bean.LibraryInfo;
import com.lhg.puremusic.data.bean.TestAlbum;
import com.lhg.puremusic.data.bean.User;
import com.lhg.architecture.data.manager.NetState;
import com.lhg.architecture.data.manager.NetworkStateManager;
import com.lhg.architecture.utils.Utils;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Create by lhg at 19/10/29
 */
public class HttpRequestManager implements ILocalRequest, IRemoteRequest {

    private static final HttpRequestManager S_REQUEST_MANAGER = new HttpRequestManager();
    private MutableLiveData<String> responseCodeLiveData;

    private HttpRequestManager() {
    }

    public static HttpRequestManager getInstance() {
        return S_REQUEST_MANAGER;
    }

    public MutableLiveData<String> getResponseCodeLiveData() {
        if (responseCodeLiveData == null) {
            responseCodeLiveData = new MutableLiveData<>();
        }
        return responseCodeLiveData;
    }

    @Override
    public void getFreeMusic(MutableLiveData<TestAlbum> liveData) {

        Gson gson = new Gson();
        Type type = new TypeToken<TestAlbum>() {
        }.getType();
        TestAlbum testAlbum = gson.fromJson(Utils.getApp().getString(R.string.free_music_json), type);

        liveData.setValue(testAlbum);
    }

    @Override
    public void getLibraryInfo(MutableLiveData<List<LibraryInfo>> liveData) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<LibraryInfo>>() {
        }.getType();
        List<LibraryInfo> list = gson.fromJson(Utils.getApp().getString(R.string.library_json), type);

        liveData.setValue(list);
    }

    /**
     * TODO：模拟下载任务:
     * 可分别用于 普通的请求，和可跟随页面生命周期叫停的请求，
     * 具体可见 ViewModel 和 UseCase 中的使用。
     *
     * @param liveData 从 Request-ViewModel 或 UseCase 注入 LiveData，用于 控制流程、回传进度、回传文件
     */
    @Override
    public void downloadFile(MutableLiveData<DownloadFile> liveData) {

        Timer timer = new Timer();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                //模拟下载，假设下载一个文件要 10秒、每 100 毫秒下载 1% 并通知 UI 层

                DownloadFile downloadFile = liveData.getValue();
                if (downloadFile == null) {
                    downloadFile = new DownloadFile();
                }
                if (downloadFile.getProgress() < 100) {
                    downloadFile.setProgress(downloadFile.getProgress() + 1);
                    Log.d("TAG", "下载进度 " + downloadFile.getProgress() + "%");
                } else {
                    timer.cancel();
                    downloadFile.setProgress(0);
                    return;
                }
                if (downloadFile.isForgive()) {
                    timer.cancel();
                    downloadFile.setProgress(0);
                    downloadFile.setForgive(false);
                    return;
                }
                liveData.postValue(downloadFile);
                downloadFile(liveData);
            }
        };

        timer.schedule(task, 100);
    }

    /**
     * TODO 模拟登录的网络请求
     *
     * @param user     ui 层填写的用户信息
     * @param liveData 模拟网络请求返回的 token
     */
    @Override
    public void login(User user, MutableLiveData<String> liveData) {

        Timer timer = new Timer();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                //TODO 模拟登录，假装花费了 2000 毫秒去提交用户信息，结果遭遇网络状况不良。
                //这时候可以通过 NetworkState 去通知 UI 层做出变化。

                NetState netState = new NetState();
                netState.setSuccess(false);
                netState.setResponseCode("404");
                NetworkStateManager.getInstance().networkStateCallback.postValue(netState);

                if (netState.isSuccess()) {
                    //TODO 否则，网络状况好的情况下，可向 UI 层回传来自网络请求响应的 token 等其他信息
                    liveData.postValue("token:xxxxxxxxxxxx");
                }
            }
        };

        timer.schedule(task, 2000);
    }

}
