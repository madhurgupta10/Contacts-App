package com.example.contactsapp.model;

import android.database.Cursor;

public class CustomContact {

    private String _id;
    private String name;
    private String phoneNumber;
    private String profilePicUri;

    public CustomContact(Cursor cursor) {

        try {
            _id = cursor.getString(cursor.getColumnIndexOrThrow(cursor.getColumnName(0)));
            name = cursor.getString(cursor.getColumnIndexOrThrow(cursor.getColumnName(1)));
            phoneNumber = cursor.getString(cursor.getColumnIndexOrThrow(cursor.getColumnName(2)));
            profilePicUri = cursor.getString(cursor.getColumnIndexOrThrow(cursor.getColumnName(3)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProfilePicUri() {
        return profilePicUri;
    }

    public void setProfilePicUri(String profilePicUri) {
        this.profilePicUri = profilePicUri;
    }
}
