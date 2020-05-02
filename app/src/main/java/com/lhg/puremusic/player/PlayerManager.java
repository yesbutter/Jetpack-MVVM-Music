package com.lhg.puremusic.player;

import android.content.Context;
import android.content.Intent;

import com.lhg.puremusic.data.bean.TestAlbum;
import com.lhg.puremusic.player.bean.dto.ChangeMusic;
import com.lhg.puremusic.player.bean.dto.PlayingMusic;
import com.lhg.puremusic.player.contract.IPlayController;
import com.lhg.puremusic.player.contract.IServiceNotifier;
import com.lhg.puremusic.player.notification.PlayerService;

import java.util.List;

import androidx.lifecycle.MutableLiveData;

/**
 * Create by lhg at 19/10/31
 */
public class PlayerManager implements IPlayController<TestAlbum, TestAlbum.TestMusic> {

    private static final PlayerManager S_MANAGER = new PlayerManager();

    private final PlayerController<TestAlbum, TestAlbum.TestMusic> mController;

    private Context mContext;

    private PlayerManager() {
        mController = new PlayerController<>();
    }

    public static PlayerManager getInstance() {
        return S_MANAGER;
    }

    public void init(Context context) {
        init(context, null);
    }

    @Override
    public void init(Context context, IServiceNotifier iServiceNotifier) {
        mContext = context.getApplicationContext();
        mController.init(mContext, null, startOrStop -> {
            Intent intent = new Intent(mContext, PlayerService.class);
            if (startOrStop) {
                mContext.startService(intent);
            } else {
                mContext.stopService(intent);
            }
        });
    }

    @Override
    public void loadAlbum(TestAlbum musicAlbum) {
        mController.loadAlbum(mContext, musicAlbum);
    }

    @Override
    public void loadAlbum(TestAlbum musicAlbum, int playIndex) {
        mController.loadAlbum(mContext, musicAlbum, playIndex);
    }

    @Override
    public void playAudio() {
        mController.playAudio(mContext);
    }

    @Override
    public void playAudio(int albumIndex) {
        mController.playAudio(mContext, albumIndex);
    }

    @Override
    public void playNext() {
        mController.playNext(mContext);
    }

    @Override
    public void playPrevious() {
        mController.playPrevious(mContext);
    }

    @Override
    public void playAgain() {
        mController.playAgain(mContext);
    }

    @Override
    public void pauseAudio() {
        mController.pauseAudio();
    }

    @Override
    public void resumeAudio() {
        mController.resumeAudio();
    }

    @Override
    public void clear() {
        mController.clear(mContext);
    }

    @Override
    public void changeMode() {
        mController.changeMode();
    }

    @Override
    public boolean isPlaying() {
        return mController.isPlaying();
    }

    @Override
    public boolean isPaused() {
        return mController.isPaused();
    }

    @Override
    public boolean isInited() {
        return mController.isInited();
    }

    @Override
    public void requestLastPlayingInfo() {
        mController.requestLastPlayingInfo();
    }

    @Override
    public void setSeek(int progress) {
        mController.setSeek(progress);
    }

    @Override
    public String getTrackTime(int progress) {
        return mController.getTrackTime(progress);
    }

    @Override
    public TestAlbum getAlbum() {
        return mController.getAlbum();
    }

    @Override
    public List<TestAlbum.TestMusic> getAlbumMusics() {
        return mController.getAlbumMusics();
    }

    @Override
    public void setChangingPlayingMusic(boolean changingPlayingMusic) {
        mController.setChangingPlayingMusic(mContext, changingPlayingMusic);
    }

    @Override
    public int getAlbumIndex() {
        return mController.getAlbumIndex();
    }

    @Override
    public MutableLiveData<ChangeMusic> getChangeMusicLiveData() {
        return mController.getChangeMusicLiveData();
    }

    @Override
    public MutableLiveData<PlayingMusic> getPlayingMusicLiveData() {
        return mController.getPlayingMusicLiveData();
    }

    @Override
    public MutableLiveData<Boolean> getPauseLiveData() {
        return mController.getPauseLiveData();
    }

    @Override
    public MutableLiveData<Enum> getPlayModeLiveData() {
        return mController.getPlayModeLiveData();
    }

    @Override
    public Enum getRepeatMode() {
        return mController.getRepeatMode();
    }

    @Override
    public void togglePlay() {
        mController.togglePlay(mContext);
    }

    @Override
    public TestAlbum.TestMusic getCurrentPlayingMusic() {
        return mController.getCurrentPlayingMusic();
    }
}
