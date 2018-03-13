package com.example.user.firebaseauth;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ShowProfile extends AppCompatActivity {

    private DatabaseReference mDatabase,childNodes;
    private Button addUserBtn;
    private LinearLayout root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_profile);

        addUserBtn = (Button) findViewById(R.id.addUsr);
        root = (LinearLayout) findViewById(R.id.root);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
            mDatabase = FirebaseDatabase.getInstance().getReference();
            childNodes = mDatabase.child("users").child(user.getUid()).child("users");

            childNodes.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //User post = dataSnapshot.getValue(User.class);
                    //Toast.makeText(CreateUser.this, post.username, Toast.LENGTH_SHORT).show();
                    root.removeAllViews();
                    for(int i=1; i<= dataSnapshot.getChildrenCount();i++){
                        TextView text = new TextView(getApplicationContext());
                        text.setText(dataSnapshot.child(""+i).getValue(User.class).username);
                        text.setTag(i+"");
                        text.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                showProfile(view.getTag().toString());
                            }
                        });
                        root.addView(text);
                        View hr = new View(getApplicationContext());
                        hr.setLayoutParams(new ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.FILL_PARENT,
                                2));
                        hr.setBackgroundResource(R.color.colorPrimary);
                        root.addView(hr);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getCode());
                }
            });

        } else {
            // No user is signed in
        }

        addUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), CreateUser.class);
                startActivity(i);
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.addUser:
                Intent i = new Intent(getApplicationContext(), CreateUser.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void showProfile(String tag){
        Intent i =  new Intent(getApplicationContext(),EditProfile.class);
        i.putExtra("userId",tag);
        startActivity(i);
    }
}
