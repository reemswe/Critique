package com.example.critique;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class NotificationView extends AppCompatActivity {

    Intent message;
    Bundle b;
    String msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_view);
        TextView textView = (TextView) findViewById(R.id.textView);

        message= getIntent();
        b = message.getExtras();
        msg= b.getString("msg");
        textView.setText(msg);
    }
}
