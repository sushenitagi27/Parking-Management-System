package com.example.parkingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.opengl.GLDebugHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.DigitalClock;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class InsertData extends AppCompatActivity
{

    String sio;

    EditText t1,t2;
    static int ct=0;
    CheckBox regular,electric,pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_data);
        t1=(EditText)findViewById(R.id.t1);
        t2=(EditText)findViewById(R.id.t2);
        regular = (CheckBox)findViewById(R.id.checkBox4);
        electric = (CheckBox)findViewById(R.id.checkBox5);
        pref = (CheckBox)findViewById(R.id.checkBox6);
        sio = t1.getText().toString();
    }

    public void addrecords(View view)
    {

        if(t1.getText().toString().isEmpty() || t2.getText().toString().isEmpty() || t1.getText().toString().length() !=13 || t2.getText().toString().length()!=10)
        {
            Toast.makeText(this, "Please input the valid data", Toast.LENGTH_SHORT).show();
            return ;
        }
        if(!t1.getText().toString().substring(2,3).contentEquals("-") || !t1.getText().toString().substring(5,6).contentEquals("-") || !t1.getText().toString().substring(8,9).contentEquals("-"))
        {
            Toast.makeText(this, "Please follow the right Vehicle number pattern AA-NN-AA-NNNN", Toast.LENGTH_SHORT).show();
            return;
        }


        //ct++;
      //  String slot = "S"+String.valueOf(ct);
        Date d = Calendar.getInstance().getTime();
        String s=d.toString();
        s=s.substring(10,16);
        String sd=d.toString();
        sd=sd.substring(4,10);
        int found=0;
       // Toast.makeText(this,slot, Toast.LENGTH_LONG).show();
        DBmanager db = new DBmanager(this);
        String flag = "All Parking are Full";
        {
            Cursor con_slot_details = db.getAvailableSlot("parkingSlot");
            while (con_slot_details.moveToNext()) {
                flag = con_slot_details.getString(0);
                String l = flag.substring(1);
                ct=Integer.valueOf(l);
                found =1;
                break;
            }
        }
        if(found==0)
        {
            Toast.makeText(this, flag, Toast.LENGTH_SHORT).show();
            return;
        }


      int reg_flag=0;
      int reg_id=0;

        String cus_type="no",vehicle_type="no";
        if(regular.isChecked())
        {

            cus_type="yes";
            SQLiteDatabase database = db.getWritableDatabase();
            Cursor crs = db.getdata("regular_customer r,customer c","r.cust_id = c.cust_id AND contact_number",t2.getText().toString()) ;
            while(crs.moveToNext())
            {
                reg_flag=1;
                Toast.makeText(this, "Regular Customer", Toast.LENGTH_SHORT).show();
            }
            if(reg_flag==0)
            {
                Toast.makeText(this, "You are not a Regular customer", Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(this,CreateRegCus.class));
                Toast.makeText(this, "Please make a deposit of Rs 300", Toast.LENGTH_SHORT).show();
                Cursor pointer=db.getcusid();
                while (pointer.moveToNext())
                {
                    reg_id = pointer.getInt(0);
                }
               reg_id = reg_id +1;
                ContentValues regular_customer = new ContentValues();
                regular_customer.put("cust_id",String.valueOf(reg_id));
                regular_customer.put("purchase_date",sd);
                regular_customer.put("booking_date",sd);
                regular_customer.put("pass_cost","300");

                long re=database.insert("regular_customer",null,regular_customer);
                if(re==-1)
                {
                    Toast.makeText(this, "Unable to make u a regular Customer", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(this,"Successfully made Regular Customer", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            cus_type="no";
        }
        if(electric.isChecked())
        {
            vehicle_type="yes";
            SQLiteDatabase database = db.getWritableDatabase();
            Cursor pointer=db.getcusid();
            while (pointer.moveToNext())
            {
                reg_id = pointer.getInt(0);
            }
            reg_id = reg_id +1;
            String preference="no";
            ContentValues ev_slot = new ContentValues();
            ev_slot.put("cust_id",String.valueOf(reg_id));
            ev_slot.put("f_id","2");
            if(pref.isChecked())
                preference="yes";
            ev_slot.put("charging_preference",preference);


            long re=database.insert("electric_parking_slot",null,ev_slot);
            if(re==-1)
            {
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(this,"successfully alloted EV slot", Toast.LENGTH_SHORT).show();


        }
        else
        {
            vehicle_type="no";
        }


        String result = db.addtwo(t1.getText().toString(),(t2.getText().toString()),sd,s,ct,flag,vehicle_type,cus_type);
        Toast.makeText(this, result, Toast.LENGTH_LONG).show();

        t1.setText("");
        t2.setText("");
        startActivity(new Intent(this,MainActivity.class));
        //db.clear();
    }

}