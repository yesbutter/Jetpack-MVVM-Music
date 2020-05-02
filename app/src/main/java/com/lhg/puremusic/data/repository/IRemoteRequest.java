package com.lhg.puremusic.data.repository;

import androidx.lifecycle.MutableLiveData;

import com.lhg.puremusic.data.bean.DownloadFile;
import com.lhg.puremusic.data.bean.LibraryInfo;
import com.lhg.puremusic.data.bean.TestAlbum;
import com.lhg.puremusic.data.bean.User;

import java.util.List;

/**
 * Create by lhg at 19/10/29
 */
public interface IRemoteRequest {

    void getFreeMusic(MutableLiveData<TestAlbum> liveData);

    void getLibraryInfo(MutableLiveData<List<LibraryInfo>> liveData);

    void downloadFile(MutableLiveData<DownloadFile> liveData);

    void login(User user, MutableLiveData<String> liveData);
}
