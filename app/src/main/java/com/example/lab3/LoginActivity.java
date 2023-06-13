package com.example.lab3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private EditText editTextUsername, editTextPassword;
    private Button buttonLogin,buttonBack;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUsername = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonBack=findViewById(R.id.buttonback);

        databaseHelper = new DatabaseHelper(this);


        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the entered username and password
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();

                // Validate the input
                if (username.isEmpty() || password.isEmpty()) {


                } else {
                    // Check if the username and password match in the database
                    boolean loginSuccessful = checkLogin(username, password);

                    if (loginSuccessful) {
                        // Login successful
                        Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();

                        // Start the welcome activity
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("username", username);
                        startActivity(intent);
                    } else {
                        // Login failed

                        Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private boolean checkLogin(String username, String password) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String[] columns = {DatabaseHelper.COLUMN_USERNAME};
        String selection = DatabaseHelper.COLUMN_USERNAME + " = ? AND " + DatabaseHelper.COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = {username, password};
        Cursor cursor = db.query(
                DatabaseHelper.TABLE_NAME,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        boolean loginSuccessful = cursor.getCount() > 0;

        cursor.close();
        db.close();

        return loginSuccessful;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Close the database connection
        databaseHelper.close();
    }



}