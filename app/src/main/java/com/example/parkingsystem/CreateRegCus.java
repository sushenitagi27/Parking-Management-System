package com.example.parkingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CreateRegCus extends AppCompatActivity {

    TextView t9;
    EditText key;
    DBmanager db= new DBmanager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_reg_cus);
        t9=(TextView)findViewById(R.id.textView9);
        key = (EditText)findViewById(R.id.ph);
    }

    public void search(View view) {
        int flag = 0;
        Cursor crs = db.getdata("customer c,slot_details sd,parkingLot p"," c.ps_id=sd.ps_id AND sd.p_id=p.p_id AND contact_number",key.getText().toString());
        while (crs.moveToNext()) {
            flag = 1;
            t9.setText("Floor : "+crs.getString(11)+"\nParking Lot : "+crs.getString(8)+"\nParking Slot : "+crs.getString(9));

        }
        if(flag==0)
        {
            Toast.makeText(this, "Please enter valid Phone number", Toast.LENGTH_SHORT).show();
        }
        key.setText("");

    }
}