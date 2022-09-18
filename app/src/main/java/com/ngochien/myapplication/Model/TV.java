package com.ngochien.myapplication.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class TV implements Parcelable {
    private String id,title,day,linkvideo,image;

    public TV(String id, String title, String day, String linkvideo, String image) {
        this.id = id;
        this.title = title;
        this.day = day;
        this.linkvideo = linkvideo;
        this.image = image;
    }

    protected TV(Parcel in) {
        id = in.readString();
        title = in.readString();
        day = in.readString();
        linkvideo = in.readString();
        image = in.readString();
    }

    public static final Creator<TV> CREATOR = new Creator<TV>() {
        @Override
        public TV createFromParcel(Parcel in) {
            return new TV(in);
        }

        @Override
        public TV[] newArray(int size) {
            return new TV[size];
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

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getLinkvideo() {
        return linkvideo;
    }

    public void setLinkvideo(String linkvideo) {
        this.linkvideo = linkvideo;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(day);
        dest.writeString(linkvideo);
        dest.writeString(image);
    }
}
