package com.example.user.firebaseauth;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Random;

public class EditProfile extends BaseActivity {

    private HashMap<String, Boolean> selectedOptions = new HashMap<>();
    private String[] options = {"news", "cricket", "music","weather"};
    private DatabaseReference mDatabase,childNodes;
    private FirebaseUser user;
    private TextView username;
    private CheckBox news,weather,music,cricket;
    private Button edit,photo;
    private String id;
    private Random rand = new Random();
    private DataSnapshot ds;
    private String childC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        for(int j=0; j < options.length; j++){
            selectedOptions.put(options[j], false);
        }

        username = (TextView) findViewById(R.id.username);
        news = (CheckBox) findViewById(R.id.news);
        cricket = (CheckBox) findViewById(R.id.cricket);
        weather = (CheckBox) findViewById(R.id.weather);
        music = (CheckBox) findViewById(R.id.music);
        edit = (Button) findViewById(R.id.makeChanges);
        photo = (Button) findViewById(R.id.takePhoto);

        addListener(news);
        addListener(cricket);
        addListener(music);
        addListener(weather);

        id = getIntent().getStringExtra("userId");

        user = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        childNodes = mDatabase.child("users").child(user.getUid()).child("users").child(id);

        mDatabase.child("users").child(user.getUid()).child("photoQueue").addValueEventListener( new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ds = dataSnapshot;

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        mDatabase.child("users").child(user.getUid()).child("users").addValueEventListener( new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                childC = String.valueOf(dataSnapshot.getChildrenCount());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        childNodes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //User post = dataSnapshot.getValue(User.class);
                //Toast.makeText(CreateUser.this, post.username, Toast.LENGTH_SHORT).show();
               // childCount = dataSnapshot.getChildrenCount();

                username.setText(dataSnapshot.getValue(User.class).username);

                cricket.setChecked(dataSnapshot.child("preferences").getValue(Preferences.class).cricket);
                news.setChecked(dataSnapshot.child("preferences").getValue(Preferences.class).news);
                weather.setChecked(dataSnapshot.child("preferences").getValue(Preferences.class).weather);
                music.setChecked(dataSnapshot.child("preferences").getValue(Preferences.class).music);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeChanges();
            }
        });

        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePhoto();
            }
        });

    }

    public void takePhoto(){
        long countC = ds.getChildrenCount();
        Toast.makeText(this, String.valueOf(countC + 1), Toast.LENGTH_SHORT).show();
        //int countChild = dref.c
        photoQueue pQ = new photoQueue(id,false);
        mDatabase.child("users").child(user.getUid()).child("photoQueue").child(String.valueOf(countC + 1)).setValue(pQ);
        //Toast.makeText(this, "Take Photo", Toast.LENGTH_SHORT).show();
    }

    public void makeChanges(){
        Preferences p = new Preferences(selectedOptions);
        User usr = new User(username.getText().toString(),String.valueOf(childC + 1),p);
        //mDatabase.child("users").child(userId).setValue(user);
        mDatabase.child("users").child(user.getUid()).child("users").child(id).setValue(usr);
        
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
}
