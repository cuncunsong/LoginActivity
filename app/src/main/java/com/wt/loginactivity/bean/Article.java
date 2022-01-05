package com.wt.loginactivity.bean;

import java.io.Serializable;
import java.util.Date;

public class Article implements Serializable {
    private String title;
    private String author;
    private String time;
    private String content;
    private Date collTime;

    public Article() {

    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getTime() {
        return time;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public Date getCollTime() {
        return collTime;
    }

    public void setCollTime(Date collTime) {
        this.collTime = collTime;
    }

    public Article(String title, String author, String time) {
        this.title = title;
        this.author = author;
        this.time = time;
    }

    public Article(String title, String author, Date collTime) {
        this.title = title;
        this.author = author;
        this.collTime = collTime;
    }
}
