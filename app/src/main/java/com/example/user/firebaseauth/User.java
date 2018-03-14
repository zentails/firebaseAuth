package com.example.user.firebaseauth;
import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by user on 3/13/2018.
 */
@IgnoreExtraProperties
public class User {

    public String username;
    public String id;
    public Preferences preferences;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username,String id, Preferences pref) {
        this.id = id;
        this.username = username;
        this.preferences = pref;
    }

}
