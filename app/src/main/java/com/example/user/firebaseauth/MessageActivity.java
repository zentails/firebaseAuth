package com.example.user.firebaseauth;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MessageActivity extends AppCompatActivity {

    private String title;
    private String body;
    private TextView titleText, bodyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        title = getIntent().getStringExtra("TITLE");
        body = getIntent().getStringExtra("BODY");
        titleText = (TextView) findViewById(R.id.title);
        titleText.setText(title);
        bodyText = (TextView) findViewById(R.id.body);
        bodyText.setText(body);
    }
}
