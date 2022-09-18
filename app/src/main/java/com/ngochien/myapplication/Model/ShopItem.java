package com.ngochien.myapplication.Model;

import java.io.Serializable;

public class ShopItem implements Serializable {
    public String image;
    public String price;
    public String des;
    public String rate;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public ShopItem(String image, String name, String des, String rate) {
        this.image = image;
        this.price = name;
        this.des = des;
        this.rate = rate;
    }
}
