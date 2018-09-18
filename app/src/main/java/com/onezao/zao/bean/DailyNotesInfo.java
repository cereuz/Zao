package com.onezao.zao.bean;

public class DailyNotesInfo {
    //_id integer primary key autoincrement , title varchar(20),author varchar(20),content varchar(10000000),picpath varchar(200),email varchar(30),time varchar(60))
    private String title;
    private String author;
    private String content;
    private String picpath;
    private String email;
    private String time;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPicpath() {
        return picpath;
    }

    public void setPicpath(String picpath) {
        this.picpath = picpath;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
