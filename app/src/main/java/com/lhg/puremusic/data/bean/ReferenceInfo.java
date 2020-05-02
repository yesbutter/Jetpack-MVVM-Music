package com.lhg.puremusic.data.bean;

/**
 * Create by lhg at 19/11/2
 */
public class ReferenceInfo {

    private String title;
    private String cover;
    private String url;

    public ReferenceInfo() {
    }

    public ReferenceInfo(String title, String cover, String url) {
        this.title = title;
        this.cover = cover;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
