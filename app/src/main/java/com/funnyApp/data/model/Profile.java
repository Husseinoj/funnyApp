package com.funnyApp.data.model;

import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Base64OutputStream;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by root on 6/9/17.
 */

public class Profile {
    @SerializedName("first_name")
    @Expose
    private String firstName;

    @SerializedName("last_name")
    @Expose
    private String lastName;

    @SerializedName("national_code")
    @Expose
    private String nationalCode;

    @SerializedName("gender")
    @Expose
    private Boolean gender;

    @SerializedName("profile_id")
    @Expose
    private String profileId;
    @SerializedName("user_id")
    @Expose
    private String userId;

    @SerializedName("avatar_url")
    @Expose
    private String avatarUrl;

    @SerializedName("username")
    @Expose
    private String userName;

    @SerializedName("user_type")
    @Expose
    private String user_type;

    public Profile() {
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNationalCode() {
        return nationalCode;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }



    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }
    private String base64;
    
    public String getAvatarUrl() {
        return "data:image/png;base64,"+avatarUrl;
    }

    public void setAvatarUrl(Bitmap bitmap) {
        //this.avatarUrl = getFileToByte(avatarFile.getAbsolutePath());
        this.avatarUrl = convertBitmapToBase64(bitmap);
    }
    private String convertBitmapToBase64(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
        return encoded;
    }
    public static String getFileToByte(String path){

        try{
            InputStream inputStream=new FileInputStream(path);
            byte[] buffer=new byte[8192];
            int bytesRead;
            ByteArrayOutputStream output=new ByteArrayOutputStream();
            Base64OutputStream output64=new Base64OutputStream(output, Base64.DEFAULT);
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                output64.write(buffer, 0, bytesRead);
            }
            output64.close();
            return output.toString();
        }catch (IOException e){
            e.printStackTrace();
        }

        //encodeString = Base64.encodeToString(b, Base64.DEFAULT);
        return null;

    }

}
