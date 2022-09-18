package com.ngochien.myapplication.Model;

import java.io.Serializable;

public class Voucher implements Serializable {
    public String id;
    public String owner;
    public String discount;
    public String code;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Voucher(String id, String owner, String discount, String code) {
        this.id = id;
        this.owner = owner;
        this.discount = discount;
        this.code = code;
    }
}
