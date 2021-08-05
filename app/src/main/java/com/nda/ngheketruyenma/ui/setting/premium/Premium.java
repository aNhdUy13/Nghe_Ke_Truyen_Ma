package com.nda.ngheketruyenma.ui.setting.premium;

public class Premium {
    private int id;
    private String imgBG;

    public Premium(){}
    public Premium(int id, String imgBG) {
        this.id = id;
        this.imgBG = imgBG;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImgBG() {
        return imgBG;
    }

    public void setImgBG(String imgBG) {
        this.imgBG = imgBG;
    }
}
