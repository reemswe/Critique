package com.example.critique;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {
    DBHelper usersDB;
    EditText username, password, confirmPassword;
    RadioGroup radioGroup;
    RadioButton retailer, regularUser;
    Button signUp;
    TextView signInLink;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.regPassword);
        confirmPassword = (EditText) findViewById(R.id.confirmPassword);
        radioGroup = (RadioGroup) findViewById(R.id.selection);
        retailer = (RadioButton) findViewById(R.id.retailer);
        regularUser = (RadioButton) findViewById(R.id.regularUser);
        signUp = (Button) findViewById(R.id.signUp);
        usersDB = usersDB.getInstance(this);
        signInLink = (TextView) findViewById(R.id.signInLink);

        signUp.setOnClickListener(view -> {
            addUser();
        });

        signInLink.setOnClickListener(view -> {
            SignUp.this.startActivity(new Intent(SignUp.this, SignIn.class));
        });

    }

    public void addUser(){
        String userN =  username.getText().toString();
        String pass = password.getText().toString();
        String confirmPass = confirmPassword.getText().toString();

        if(userN.equals("") || pass.equals("") || confirmPass.equals("") || radioGroup.getCheckedRadioButtonId() == -1)
            Toast.makeText(SignUp.this, "Please fill all field", Toast.LENGTH_SHORT).show();
        else {
            if (!pass.equals(confirmPass))
                Toast.makeText(SignUp.this, "The two passwords don't match", Toast.LENGTH_SHORT).show();
            else {
                String type = "";
                if(retailer.isChecked())
                    type = "Retailer";
                else if(regularUser.isChecked())
                    type = "Regular User";

                boolean isInserted = usersDB.insertData(userN, pass, type);

                if (isInserted) {
                    Toast.makeText(SignUp.this, "New Account Created Successfully", Toast.LENGTH_SHORT).show();
                    SignUp.this.startActivity(new Intent(SignUp.this, SignIn.class));
                } else
                    Toast.makeText(SignUp.this, "Account is not Created", Toast.LENGTH_SHORT).show();
            }
        }
    }
}