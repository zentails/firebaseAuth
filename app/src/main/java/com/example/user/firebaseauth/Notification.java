package com.example.user.firebaseauth;

/**
 * Created by user on 3/14/2018.
 */

public class Notification {

    public String tt;
    public String body;
    public boolean read;

    public Notification(){

    }

    public Notification(String title, String body, boolean read) {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
        this.tt = title;
        this.body = body;
        this.read = read;
    }


}
