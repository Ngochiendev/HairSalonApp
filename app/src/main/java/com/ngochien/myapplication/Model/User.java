package com.ngochien.myapplication.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    String id,Username,Email,Address,Phone,Avatar;

    public User(String id, String username, String email, String address, String phone, String avatar) {
        this.id = id;
        Username = username;
        Email = email;
        Address = address;
        Phone = phone;
        Avatar = avatar;
    }

    protected User(Parcel in) {
        id = in.readString();
        Username = in.readString();
        Email = in.readString();
        Address = in.readString();
        Phone = in.readString();
        Avatar = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String avatar) {
        Avatar = avatar;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(Username);
        parcel.writeString(Email);
        parcel.writeString(Address);
        parcel.writeString(Phone);
        parcel.writeString(Avatar);
    }
}
