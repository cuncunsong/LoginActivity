package com.wt.loginactivity.bean;

import org.litepal.crud.DataSupport;

public class Image extends DataSupport {
    private int id;
    private String imageSource;
    private int UserId;

    public Image(int id, String imageSource, int userId) {
        this.id = id;
        this.imageSource = imageSource;
        UserId = userId;
    }

    public Image() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageSource() {
        return imageSource;
    }

    public void setImageSource(String imageSource) {
        this.imageSource = imageSource;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }
}
