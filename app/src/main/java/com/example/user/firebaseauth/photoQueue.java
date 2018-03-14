package com.example.user.firebaseauth;

/**
 * Created by user on 3/14/2018.
 */

public class photoQueue {

    public String id;
    public boolean read;

    public photoQueue() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public photoQueue(String id, boolean read) {
        this.id = id;
        this.read = read;
    }
}
