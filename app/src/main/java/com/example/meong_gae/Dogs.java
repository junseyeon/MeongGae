package com.example.meong_gae;

public class Dogs {

    String dogname;
    String dogsex;
    String dogsize;
    String dogage;
    private int imageResId;

public Dogs()
{


}

    public Dogs(int imageResId, String dogname, String dogsex, String dogsize, String dogage) {
        this.imageResId = imageResId;
        this.dogname = dogname;
        this.dogsex = dogsex;
        this.dogsize = dogsize;
        this.dogage = dogage;
    }

    public String getDogname() {
        return dogname;
    }

    public void setDogname(String dogname) {
        this.dogname = dogname;
    }

    public String getDogsex() {
        return dogsex;
    }

    public void setDogsex(String dogsex) {
        this.dogsex = dogsex;
    }

    public String getDogsize() {
        return dogsize;
    }

    public void setDogsize(String dogsize) {
        this.dogsize = dogsize;
    }

    public String getDogage() {
        return dogage;
    }

    public void setDogage(String dogage) {
        this.dogage = dogage;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }
}
