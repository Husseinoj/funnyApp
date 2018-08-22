package com.funnyApp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by root on 6/9/17.
 */

public class Register {
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("profile")
    @Expose
    private Profile profile;
    @SerializedName("smsCode")
    @Expose
    private String smsCode;
    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
