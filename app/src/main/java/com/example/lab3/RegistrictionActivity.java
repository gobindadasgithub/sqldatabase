package com.example.lab3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegistrictionActivity extends AppCompatActivity {
    private EditText et_Email, et_Password;
    private Button btn_reg,btn_login;
    private DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registriction);

        et_Email = findViewById(R.id.editTextEmail);
        et_Password = findViewById(R.id.editTextPassword);
        btn_reg = findViewById(R.id.buttonRegister);
        btn_login = findViewById(R.id.buttonlogin);


        databaseHelper = new DatabaseHelper(this);

        btn_reg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String username = et_Email.getText().toString();
                String password = et_Password.getText().toString();

                long rowId = databaseHelper.insertUser(username, password);



                if (rowId != -1) {
                    // Registration successful
                    Toast.makeText(RegistrictionActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(RegistrictionActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    // Registration failed
                    Toast.makeText(RegistrictionActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrictionActivity.this, LoginActivity.class);
                startActivity(intent);

            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Close the database connection
        databaseHelper.close();
    }
}