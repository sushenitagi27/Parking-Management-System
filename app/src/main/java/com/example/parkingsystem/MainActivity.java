package com.example.parkingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startdbapp(View view)
    {
        new DBmanager(this);
        startActivity(new Intent(this,InsertData.class));
    }

    public void exit(View view) {
        startActivity(new Intent(this,Exit.class));
    }

    public void clearDatabase(View view) {
        startActivity(new Intent(this,Admin.class));
    }

    public void find(View view) {
        startActivity(new Intent(this,CreateRegCus.class));
    }



    public void goToAbout(View view) {

        startActivity(new Intent(this,cover.class));
    }
}