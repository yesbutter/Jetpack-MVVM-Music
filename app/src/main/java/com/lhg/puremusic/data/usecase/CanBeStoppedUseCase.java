package com.lhg.puremusic.data.usecase;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.lhg.architecture.data.usecase.UseCase;
import com.lhg.puremusic.data.bean.DownloadFile;
import com.lhg.puremusic.data.repository.HttpRequestManager;

import static com.lhg.puremusic.data.usecase.CanBeStoppedUseCase.RequestValues;
import static com.lhg.puremusic.data.usecase.CanBeStoppedUseCase.ResponseValue;


/**
 * UseCase 示例，实现 LifeCycle 接口，单独服务于 有 “叫停” 需求 的业务
 *
 * Create by lhg at 19/11/25
 */
public class CanBeStoppedUseCase extends UseCase<RequestValues, ResponseValue> implements DefaultLifecycleObserver {

    @Override
    public void onStop(@NonNull LifecycleOwner owner) {
        if (getRequestValues() != null && getRequestValues().liveData != null) {
            DownloadFile downloadFile = getRequestValues().liveData.getValue();
            downloadFile.setForgive(true);
            getRequestValues().liveData.setValue(downloadFile);
            getUseCaseCallback().onSuccess(new ResponseValue(getRequestValues().liveData));
        }
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {

        //访问数据层资源，在 UseCase 中处理带叫停性质的业务

        HttpRequestManager.getInstance().downloadFile(requestValues.liveData);

    }

    public static final class RequestValues implements UseCase.RequestValues {

        private MutableLiveData<DownloadFile> liveData;

        public RequestValues(MutableLiveData<DownloadFile> liveData) {
            this.liveData = liveData;
        }

        public MutableLiveData<DownloadFile> getLiveData() {
            return liveData;
        }

        public void setLiveData(MutableLiveData<DownloadFile> liveData) {
            this.liveData = liveData;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {

        private MutableLiveData<DownloadFile> liveData;

        public ResponseValue(MutableLiveData<DownloadFile> liveData) {
            this.liveData = liveData;
        }

        public LiveData<DownloadFile> getLiveData() {
            return liveData;
        }

        public void setLiveData(MutableLiveData<DownloadFile> liveData) {
            this.liveData = liveData;
        }
    }
}
