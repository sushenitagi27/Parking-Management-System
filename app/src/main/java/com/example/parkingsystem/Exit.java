package com.example.parkingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.strictmode.SqliteObjectLeakedViolation;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class Exit extends AppCompatActivity
{
    EditText t1;
    TextView tv,et,ex,pt,pc,d,cph,tc,design,eletric;
    CheckBox gpay,cash,card;
    String slot_id ;
    String cust_id;
    String vehicle_no;
    String contact_no;
    String reg_date;
    String time;
    String regular_customer;
    String Vehicle_electric;
    double cost,electric_charges=0;

    String r_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exit);
        t1=(EditText)findViewById(R.id.vh);
        tv =(TextView)findViewById(R.id.textView2);
        et =(TextView)findViewById(R.id.ent_time);
        ex =(TextView)findViewById(R.id.ex_t);
        pt = (TextView)findViewById(R.id.penalty_type);
        pc = (TextView)findViewById(R.id.penalty_cost);
        d  =(TextView)findViewById(R.id.duration);
        cph = (TextView)findViewById(R.id.cost_per_hr);
        tc = (TextView)findViewById(R.id.total_cost);
        gpay = (CheckBox)findViewById(R.id.checkBox);
        cash = (CheckBox)findViewById(R.id.checkBox2);
        card = (CheckBox)findViewById(R.id.checkBox3);
        design = (TextView)findViewById(R.id.textView5);
        eletric = (TextView)findViewById(R.id.textView8);

    }

    public void getdata(View view) {

        if(t1.getText().toString().length()!=10)
        {
            Toast.makeText(this, "Please Enter valid Phone Number", Toast.LENGTH_SHORT).show();
            return;
        }
      Date dx = Calendar.getInstance().getTime();
        String s = dx.toString();
        s=s.substring(10,16);

      // Toast.makeText(this, s.substring(4,6), Toast.LENGTH_SHORT).show();
      double exit_hr = Double.valueOf(s.substring(0,3));
        double exit_min = Double.valueOf(s.substring(4,6));
        int flag =0,flag2=0;
       DBmanager  obj = new DBmanager(this);

     //   Toast.makeText(this, String.valueOf(exit_hr) , Toast.LENGTH_SHORT).show();
        Cursor result = obj.getdata("customer","contact_number",t1.getText().toString());

        while (result.moveToNext()) {

            flag = 1;
            slot_id = result.getString(1);
            cust_id = result.getString(0);
            vehicle_no = result.getString(2);
            contact_no = result.getString(3);
            reg_date = result.getString(4);
            time = result.getString(5);
            regular_customer=result.getString(7);
            Vehicle_electric=result.getString(6);

        }
            Cursor holy = obj.getdata("reservation", "cust_id", cust_id);
            while (holy.moveToNext()) {
                flag2 = 1;
            }
            if (flag2 == 1) {
                Toast.makeText(this, "You have already exited the Parking Lot. Visit again", Toast.LENGTH_SHORT).show();
                return;
            }
            tv.setText("vehilce number  " + vehicle_no + "    Parking slot  " + slot_id + "\nContact Number " + contact_no + "   RegDate " + reg_date);
            int flag3=0;
            Cursor hmm = obj.getdata("electric_parking_slot","charging_preference like '%yes%' AND cust_id",cust_id);
            while (hmm.moveToNext())
            {
                flag3=1;
            }

            if(flag3==1)
            {
                Toast.makeText(this, "Electric charges Applicable", Toast.LENGTH_SHORT).show();
                electric_charges=0.3;
                eletric.setText("Electricity chaerges "+"Rs 12"+"/hr");
            }


            double entry_hr = Double.valueOf(time.substring(0, 3));
            double entry_min = Double.valueOf(time.substring(4, 6));
            double total_hr = exit_hr - entry_hr;
            double total_min = exit_min - entry_min;
            double t = total_min + ((total_hr) * 60);
            if(t<60)
            {
                cost= 18 + (electric_charges*60);
            }
            else{
                cost = (t * 0.3)+(t*electric_charges);
            }

            //  Toast.makeText(this, String.valueOf(total_min), Toast.LENGTH_SHORT).show();
            String turn = slot_id;
            turn = turn.substring(1);
            int s_flag = Integer.valueOf(turn);
            SQLiteDatabase database = obj.getWritableDatabase();
            database.execSQL("Update parkingSlot set is_slot_full='no' Where ps_id like '%" + s_flag + "%'");
            ContentValues r = new ContentValues();
            r.put("cust_id", cust_id);
            r.put("ps_id", slot_id);
            r.put("booking_date", reg_date);
            r.put("duration_in_minutes", t);
            long insert_result = database.insert("reservation", null, r);
            Cursor jesus = obj.getrid();
            while (jesus.moveToNext()) {
                r_id = jesus.getString(0);
            }
            ContentValues py = new ContentValues();
            py.put("r_id", r_id);
            py.put("actual_entry_time", time);
            py.put("actual_exit_time", s);
            py.put("penalty_type", "Nil");
            py.put("penalty_cost", 0);
            py.put("basic_cost", String.valueOf(cost));
            long slip_result = database.insert("parking_slip", null, py);
            if (insert_result == -1 || slip_result == -1)
                Toast.makeText(this, "Failed to Generate Parking Slip!!!!", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "Parking Slip Generated", Toast.LENGTH_SHORT).show();
            //database.execSQL("");
            et.setText("Entry Time: " + time);
            ex.setText("Exit Time : " + s);
            pt.setText("Penalty type : Nil");
            pc.setText("Penalty Cost : 0");
            d.setText("Duration : " + String.valueOf(t) + " min");
            cph.setText("Cost per hour : Rs 18/hour");
            design.setText("------------------------");
            tc.setText("Total Cost : Rs " + String.valueOf(cost));



        //Toast.makeText(this, "hi", Toast.LENGTH_SHORT).show();
        if(flag == 0)
        {
            Toast.makeText(this, "There is no vehicle with "+t1.getText().toString()+" parked", Toast.LENGTH_LONG).show();
            tv.setText("");
            et.setText("");
            ex.setText("");
            pt.setText("");
            pc.setText("");
            d.setText("");
            cph.setText("");
            tc.setText("");
        }

    }

    public void pay(View view) {
        DBmanager db = new DBmanager(this);
        SQLiteDatabase dbms = db.getWritableDatabase();
        ContentValues payment = new ContentValues();
        payment.put("s_id",slot_id);
        payment.put("per_hour_charges","10");
        payment.put("e_charging_per_hour","12");
        if(gpay.isChecked() & !cash.isChecked() &!card.isChecked() )
        {
            Toast.makeText(this, "Google Pay", Toast.LENGTH_SHORT).show();
            payment.put("payment_mode","GPay");
        }
        else if(!gpay.isChecked() & !cash.isChecked() & card.isChecked())
        {
            Toast.makeText(this, "card", Toast.LENGTH_SHORT).show();
            payment.put("payment_mode","Card");
        }
        else if (!gpay.isChecked() & cash.isChecked() & !card.isChecked())
        {
            Toast.makeText(this, "cash", Toast.LENGTH_SHORT).show();
            payment.put("payment_mode","Cash");
        }
        else
        {
            Toast.makeText(this, "Please select only 1 payment option", Toast.LENGTH_SHORT).show();
            return;
        }
        payment.put("total_cost",String.valueOf(cost));
        payment.put("cust_id", cust_id);
        long payment_status = dbms.insert("payment",null,payment);
        if(payment_status==-1)
        {
            Toast.makeText(this, "Payment unsuccessful", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "Payment successful ", Toast.LENGTH_SHORT).show();
        }
        startActivity(new Intent(this,MainActivity.class));
        t1.setText("");
        tv.setText("");
        et.setText("");
        ex.setText("");
        pt.setText("");
        pc.setText("");
        d.setText("");
        cph.setText("");
        tc.setText("");

    }

    public void pay_later(View view) {
        double c=0;
        String god = "god";
        DBmanager db = new DBmanager(this);
        Cursor crs=  db.getdata("customer","contact_number",contact_no);
        while(crs.moveToNext())
        {
            cust_id = crs.getString(0);
            god =crs.getString(7);
            break;
        }
                 int flag =0;
           String lol="";
          Cursor crss = db.getdata("regular_customer","cust_id",crs.getString(0));
          while(crss.moveToNext())
          {
              lol = crss.getString(4);
              flag=1;
          }

         // c= Integer.parseInt(lol);

          cost= cost + c;
       // Toast.makeText(this, String.valueOf(cost), Toast.LENGTH_SHORT).show();
           SQLiteDatabase dbms=db.getWritableDatabase();

        dbms.execSQL("Update regular_customer set pass_cost ="+String.valueOf(cost)+" where cust_id="+cust_id);
            if(flag==0)
            {
                Toast.makeText(this, "You are not Regular Customer Please make a payment of Rs "+String.valueOf(cost), Toast.LENGTH_LONG).show();
                return;
            }
            Toast.makeText(this, "Your deposited amount is Rs300 You due Amount is "+String.valueOf(cost), Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this,MainActivity.class));

        t1.setText("");
        tv.setText("");
        et.setText("");
        ex.setText("");
        pt.setText("");
        pc.setText("");
        d.setText("");
        cph.setText("");
        tc.setText("");
    }

}