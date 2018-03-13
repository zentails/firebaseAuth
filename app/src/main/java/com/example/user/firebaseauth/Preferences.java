package com.example.user.firebaseauth;

import java.util.HashMap;

/**
 * Created by user on 3/13/2018.
 */

public class Preferences {
    public boolean news;
    public boolean weather;
    public boolean cricket;
    public boolean music;

    public Preferences() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Preferences(HashMap<String, Boolean> hashMap) {
        this.news = hashMap.get("news");
        this.weather = hashMap.get("weather");
        this.cricket = hashMap.get("cricket");
        this.music = hashMap.get("music");
    }
}
