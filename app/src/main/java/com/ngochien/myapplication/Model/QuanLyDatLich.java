package com.ngochien.myapplication.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class QuanLyDatLich implements Parcelable {
    private String id,iduser,Ngaydat,Giodat,Stylist,Price,State;
    private ArrayList<Dichvu> Dichvu;
    private String Username;

    public QuanLyDatLich(String id, String iduser, String ngaydat, String giodat, String stylist, String price, String state, ArrayList<com.ngochien.myapplication.Model.Dichvu> dichvu, String username) {
        this.id = id;
        this.iduser = iduser;
        Ngaydat = ngaydat;
        Giodat = giodat;
        Stylist = stylist;
        Price = price;
        State = state;
        Dichvu = dichvu;
        Username = username;
    }

    public QuanLyDatLich(String id , String ngaydat, String stylist, String price, ArrayList<com.ngochien.myapplication.Model.Dichvu> dichvu) {
        this.id = id;
        Ngaydat = ngaydat;
        Stylist = stylist;
        Price = price;
        Dichvu = dichvu;
    }

    protected QuanLyDatLich(Parcel in) {
        id = in.readString();
        iduser = in.readString();
        Ngaydat = in.readString();
        Giodat = in.readString();
        Stylist = in.readString();
        Price = in.readString();
        State = in.readString();
        Dichvu = in.createTypedArrayList(com.ngochien.myapplication.Model.Dichvu.CREATOR);
        Username = in.readString();
    }

    public static final Creator<QuanLyDatLich> CREATOR = new Creator<QuanLyDatLich>() {
        @Override
        public QuanLyDatLich createFromParcel(Parcel in) {
            return new QuanLyDatLich(in);
        }

        @Override
        public QuanLyDatLich[] newArray(int size) {
            return new QuanLyDatLich[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIduser() {
        return iduser;
    }

    public void setIduser(String iduser) {
        this.iduser = iduser;
    }

    public String getNgaydat() {
        return Ngaydat;
    }

    public void setNgaydat(String ngaydat) {
        Ngaydat = ngaydat;
    }

    public String getGiodat() {
        return Giodat;
    }

    public void setGiodat(String giodat) {
        Giodat = giodat;
    }

    public String getStylist() {
        return Stylist;
    }

    public void setStylist(String stylist) {
        Stylist = stylist;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public ArrayList<com.ngochien.myapplication.Model.Dichvu> getDichvu() {
        return Dichvu;
    }

    public void setDichvu(ArrayList<com.ngochien.myapplication.Model.Dichvu> dichvu) {
        Dichvu = dichvu;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(iduser);
        dest.writeString(Ngaydat);
        dest.writeString(Giodat);
        dest.writeString(Stylist);
        dest.writeString(Price);
        dest.writeString(State);
        dest.writeTypedList(Dichvu);
        dest.writeString(Username);
    }
}
