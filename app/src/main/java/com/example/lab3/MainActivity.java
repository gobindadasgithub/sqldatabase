package com.example.lab3;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView textViewUsername;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        textViewUsername = findViewById(R.id.TextViewUserName);

        databaseHelper = new DatabaseHelper(this);

        String username = retrieveRegisteredUser();
        textViewUsername.setText("Welcome, " + username + "!");


    }
    @SuppressLint("Range")
    private String retrieveRegisteredUser() {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String[] columns = {DatabaseHelper.COLUMN_USERNAME};
        Cursor cursor = db.query(
                DatabaseHelper.TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                null
        );

        String username = "";

        if (cursor.moveToFirst()) {
            username = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_USERNAME));
        }

        cursor.close();
        db.close();

        return username;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Close the database connection
        databaseHelper.close();
    }



}