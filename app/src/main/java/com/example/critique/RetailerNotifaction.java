package com.example.critique;

import androidx.appcompat.app.AppCompatActivity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import android.widget.Button;
import android.widget.EditText;
import java.util.Random;

public class RetailerNotifaction extends AppCompatActivity {

    private EditText title, text;
    private NotificationManagerCompat notificationManager;
    private NotificationManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_notifaction);
        title = findViewById(R.id.notificationTitle);
        text = findViewById(R.id.notificationText);
        Button sendNotification = findViewById(R.id.sendNotification);

        notificationManager = NotificationManagerCompat.from(getApplicationContext());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            manager = getSystemService(NotificationManager.class);
        }

        sendNotification.setOnClickListener(view -> {
            String Title = title.getText().toString();
            String Text = text.getText().toString();
            sendNotification(Title, Text);
        });
    }

    public void sendNotification(String title, String text)
    {   final String channelID = "critique channel";
        Intent intent = new Intent(this, NotificationView.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        String value = "\n\n\n"+"The title of the notification: "+ title + "\n\n\n"+"The message of the notification: " + text + "\n\n\n";
        intent.putExtra("msg",value);
        intent.setAction(Long.toString(System.currentTimeMillis()));
        PendingIntent pendingIntent;
        pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelID)
                .setContentTitle(title)
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE);
        builder.setContentIntent(pendingIntent);
        notificationManager.notify(new Random().nextInt(), builder.build());
    }

}