package com.ngochien.myapplication.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Dichvu implements Parcelable {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("price")
    @Expose
    private String price;

    @SerializedName("type")
    @Expose
    private String type;

    public Dichvu(String id, String title, String description, String image, String price, String type) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.image = image;
        this.price = price;
        this.type = type;
    }

    protected Dichvu(Parcel in) {
        id = in.readString();
        title = in.readString();
        description = in.readString();
        image = in.readString();
        price = in.readString();
        type = in.readString();
    }

    public static final Creator<Dichvu> CREATOR = new Creator<Dichvu>() {
        @Override
        public Dichvu createFromParcel(Parcel in) {
            return new Dichvu(in);
        }

        @Override
        public Dichvu[] newArray(int size) {
            return new Dichvu[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(image);
        dest.writeString(price);
        dest.writeString(type);
    }
}
