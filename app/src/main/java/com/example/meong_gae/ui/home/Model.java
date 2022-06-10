package com.example.meong_gae.ui.home;

public class Model {
    private String title;
    private String count;
    private String date;

    public Model(String title, String count, String date) {
        this.title = title;
        this.count = count;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
