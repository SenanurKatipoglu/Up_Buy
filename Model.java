package com.example.up_buy;

public class Model {
    private int image;
    private String title;
    private String tanım;

    public Model(int image, String title, String tanım) {
        this.image = image;
        this.title = title;
        this.tanım = tanım;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTanım() {
        return tanım;
    }

    public void setTanım(String tanım) {
        this.tanım = tanım;
    }
}
