package com.correro.alejandro.profileapp.data.model;


import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Cat getAvatar() {
        return avatar;
    }

    public void setAvatar(Cat avatar) {
        this.avatar = avatar;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    private String name;
    private String phone;
    private String email;
    private Cat avatar;


    private String web;
    private String map;

    public User(String name, String phone, String email, Cat avatar, String web, String map) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.avatar = avatar;
        this.map = map;
        this.web = web;
    }

    protected User(Parcel in) {
        this.name = in.readString();
        this.phone = in.readString();
        this.email = in.readString();
        this.avatar = (Cat)in.readValue(getClass().getClassLoader());
        this.map = in.readString();
        this.web = in.readString();
        
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.phone);
        dest.writeString(this.email);
        dest.writeValue(this.avatar);
        dest.writeString(this.map);
        dest.writeString(this.web);
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

    @Override
    public int describeContents() {
        return 0;
    }

}
