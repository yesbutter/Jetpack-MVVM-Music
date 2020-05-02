package com.lhg.puremusic.data.bean;

import com.lhg.puremusic.player.bean.base.BaseAlbumItem;
import com.lhg.puremusic.player.bean.base.BaseArtistItem;
import com.lhg.puremusic.player.bean.base.BaseMusicItem;

/**
 * Create by lhg at 19/10/31
 */
public class TestAlbum extends BaseAlbumItem<TestAlbum.TestMusic, TestAlbum.TestArtist> {

    private String albumMid;

    public String getAlbumMid() {
        return albumMid;
    }

    public void setAlbumMid(String albumMid) {
        this.albumMid = albumMid;
    }

    public static class TestMusic extends BaseMusicItem<TestArtist> {

        private String songMid;

        public String getSongMid() {
            return songMid;
        }

        public void setSongMid(String songMid) {
            this.songMid = songMid;
        }
    }

    public static class TestArtist extends BaseArtistItem {

        private String birthday;

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }
    }
}

