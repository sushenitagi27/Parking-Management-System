package com.example.parkingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Admin extends AppCompatActivity {

    EditText user;
    EditText pwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        user= findViewById(R.id.user);
        pwd= findViewById(R.id.pwd);

    }

    public void clear_database(View view) {
        String use = "team2";
        String pass = "pass1";
        if(user.getText().toString().contentEquals("team2") && pwd.getText().toString().contentEquals("pass1"))
        {
            DBmanager Db = new DBmanager(this);
            SQLiteDatabase database = Db.getWritableDatabase();
            Db.onUpgrade(database,0,0);
            Toast.makeText(this, "Successfully Cleared", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "User name or Passward is not Matching", Toast.LENGTH_SHORT).show();
        }

    }
}