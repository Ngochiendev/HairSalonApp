package com.ngochien.myapplication.Model;

import java.io.Serializable;

public class StoreData implements Serializable {
    public String id;
    public String detailAddress;
    public String address;
    public String sdt;
    public String image;


    public StoreData(String id, String detailAddress, String address, String sdt, String image) {
        this.id = id;
        this.detailAddress = detailAddress;
        this.address = address;
        this.sdt = sdt;
        this.image = image;
    }

}
