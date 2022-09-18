package com.ngochien.myapplication.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class category implements Parcelable {
    private String id,title,image;

    public category(String id, String title, String image) {
        this.id = id;
        this.title = title;
        this.image = image;
    }

    protected category(Parcel in) {
        id = in.readString();
        title = in.readString();
        image = in.readString();
    }

    public static final Creator<category> CREATOR = new Creator<category>() {
        @Override
        public category createFromParcel(Parcel in) {
            return new category(in);
        }

        @Override
        public category[] newArray(int size) {
            return new category[size];
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
        dest.writeString(image);
    }
}
