package com.example.critique;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignIn extends AppCompatActivity {

    DBHelper db;

    EditText username, password;
    Button login;
    TextView registerLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        registerLink = (TextView) findViewById(R.id.registerLink);

        db = db.getInstance(this);


        registerLink.setOnClickListener(view -> {
            SignIn.this.startActivity(new Intent(SignIn.this, SignUp.class));
        });

        login.setOnClickListener(view -> {
            String userN = username.getText().toString();
            String pass = password.getText().toString();

            if (userN.equals("") || pass.equals(""))
                Toast.makeText(SignIn.this, "Please fill all field", Toast.LENGTH_SHORT).show();
            else {
                boolean valid = db.validateInput(userN, pass);
                if (valid)
                    SignIn.this.startActivity(new Intent(SignIn.this, SignUp.class));
                else
                    Toast.makeText(SignIn.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
            }
        });
    }
}