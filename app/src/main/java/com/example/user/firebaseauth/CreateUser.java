package com.example.user.firebaseauth;

import android.content.Intent;
import android.media.MediaDataSource;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class CreateUser extends BaseActivity {

    private HashMap<String, Boolean> selectedOptions = new HashMap<>();
    private String[] options = {"news", "cricket", "music","weather"};
    private Button addUser;
    private EditText username;
    private CheckBox news,weather,music,cricket;
    private DatabaseReference mDatabase,childNodes;
    private FirebaseUser user;
    private long childCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        for(int j=0; j < options.length; j++){
            selectedOptions.put(options[j], false);
        }

        mDatabase = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();

        childNodes = mDatabase.child("users").child(user.getUid()).child("users");

        childNodes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //User post = dataSnapshot.getValue(User.class);
                //Toast.makeText(CreateUser.this, post.username, Toast.LENGTH_SHORT).show();
                childCount = dataSnapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        news = (CheckBox) findViewById(R.id.news);
        weather = (CheckBox) findViewById(R.id.weather);
        cricket = (CheckBox) findViewById(R.id.cricket);
        music = (CheckBox) findViewById(R.id.music);

        addListener(news);
        addListener(cricket);
        addListener(music);
        addListener(weather);

        username = (EditText) findViewById(R.id.username);
        addUser = (Button) findViewById(R.id.add);

        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUser();
            }
        });
    }

    public void addListener(final CheckBox chckbox){
        chckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selectedOptions.put(String.valueOf(chckbox.getTag()), true);
                }else{
                    selectedOptions.put(String.valueOf(chckbox.getTag()), false);
                }

            }
        });
    }

    public void addUser(){
        Preferences p = new Preferences(selectedOptions);
        User usr = new User(username.getText().toString(),p);
        //mDatabase.child("users").child(userId).setValue(user);
        mDatabase.child("users").child(user.getUid()).child("users").child(String.valueOf(childCount + 1)).setValue(usr);
        Intent i = new Intent(getApplicationContext(),ShowProfile.class);
        startActivity(i);
    }

}
