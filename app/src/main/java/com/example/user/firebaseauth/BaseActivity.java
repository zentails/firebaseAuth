package com.example.user.firebaseauth;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by user on 3/14/2018.
 */

public class BaseActivity extends AppCompatActivity {

    // Activity code here
    private FirebaseUser user;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            inflater.inflate(R.menu.logout, menu);
        }
        else{
            inflater.inflate(R.menu.main, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        switch (item.getItemId()) {
            case R.id.logout:
                // do what you want here
                FirebaseAuth.getInstance().signOut();

                return true;
            case R.id.login:
                // do what you want here
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
