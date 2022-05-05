package com.example.critique;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class SignIn extends AppCompatActivity {

    DBHelper db;

    EditText username, password;
    Button login;
    TextView registerLink;

    private NotificationManagerCompat notificationManager;
    private NotificationManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        registerLink = (TextView) findViewById(R.id.registerLink);

        db = DBHelper.getInstance(this);

        notificationManager = NotificationManagerCompat.from(getApplicationContext());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            manager = getSystemService(NotificationManager.class);

        registerLink.setOnClickListener(view -> SignIn.this.startActivity(new Intent(SignIn.this, SignUp.class)));

        login.setOnClickListener(view -> {
            String userN = username.getText().toString();
            String pass = password.getText().toString();

            if (userN.equals("") || pass.equals(""))
                Toast.makeText(SignIn.this, "Please fill all field", Toast.LENGTH_SHORT).show();
            else {
                boolean valid = db.validateInput(userN, pass);
                if (valid) {
                    if(db.getRow(userN).getString(3).equals("Retailer")) {
                        Intent intent = new Intent(SignIn.this, RetailerProfile.class);
                        intent.putExtra("name", userN);
                        intent.putExtra("ID", db.getRow(userN).getInt(0));
                        SignIn.this.startActivity(intent);
                    }
                    else
                        SignIn.this.startActivity(new Intent(SignIn.this, ViewStores.class));

                }
                else
                    Toast.makeText(SignIn.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        MenuItem item = menu.findItem(R.id.myswitch);
        item.setActionView(R.layout.switch_layout);

        Switch darkmode = item.getActionView().findViewById(R.id.darkmode);
        darkmode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (darkmode.isChecked())
                    getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                else
                    getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });
        return true;
    }

    private void createChannel(){
        final String channelID = "critique channel";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = manager.getNotificationChannel(channelID);
            if (channel == null) {
                channel = new NotificationChannel(channelID, "Critique Channel", NotificationManager.IMPORTANCE_DEFAULT);
                channel.setDescription("This is Critique Channel");
                manager.createNotificationChannel(channel);
            }
        }
    }
}