package com.ngochien.myapplication.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Stylist implements Parcelable {
    private String id,image,title;

    public Stylist(String id, String image, String title) {
        this.id = id;
        this.image = image;
        this.title = title;
    }

    protected Stylist(Parcel in) {
        id = in.readString();
        image = in.readString();
        title = in.readString();
    }

    public static final Creator<Stylist> CREATOR = new Creator<Stylist>() {
        @Override
        public Stylist createFromParcel(Parcel in) {
            return new Stylist(in);
        }

        @Override
        public Stylist[] newArray(int size) {
            return new Stylist[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(image);
        dest.writeString(title);
    }
}
