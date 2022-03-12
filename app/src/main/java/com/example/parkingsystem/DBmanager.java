package com.example.parkingsystem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.nfc.Tag;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Date;

public class DBmanager extends SQLiteOpenHelper
{
    private static final String dbname="ParkingSystem.db";
    public DBmanager(@Nullable Context context) {
        super(context, dbname, null, 1);
    }
   //static int count=100,ct=0;

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        //CREATION OF TABLES

        db.execSQL("CREATE TABLE floor(f_id varchar2(10) primary key,no_slots integer,is_floor_full varchar2(10),is_reserved varchar2(10))");
        db.execSQL("CREATE TABLE parkingLot(p_id varchar2(10) primary key,f_id varchar2(10),is_slot_available varchar2(10),is_rentry_allowed varchar2(10), is_valet_parking_available varchar2(10), foreign key (f_id) references floor(f_id))");
        db.execSQL("create table parkingSlot(ps_id varchar2(10) primary key,is_slot_full varchar2(10) )");
        db.execSQL("create table slot_details(p_id varchar2(10),ps_id varchar2(10),foreign key (p_id) references parkingLot(p_id),foreign key (ps_id) references parkingSlot(ps_id))");
        db.execSQL("create table customer(cust_id integer primary key autoincrement ,ps_id varchar2(10), vehicle_number varchar2(15) NOT NULL,contact_number varchar2(10) NOT NULL,registration_date date,entry_time varchar2(10),is_vehicle_electric varchar2(10),is_regular_customer varchar2(10),foreign key (ps_id) references parkingSlot(ps_id))");
        db.execSQL("create table electric_parking_slot(ev_id integer primary key autoincrement,cust_id integer,f_id varchar2(10),charging_preference varchar2(10),foreign key(cust_id) references customer(cust_id),foreign key (f_id) references floor(f_id))");
        db.execSQL("create table regular_customer(reg_id integer primary key autoincrement,cust_id varchar2(10),purchase_date date,booking_date date,pass_cost varchar2(10),foreign key (cust_id) references customer(cust_id))");
        db.execSQL("create table reservation(r_id integer primary key autoincrement,cust_id varchar2(10) unique, ps_id varchar2(10),booking_date date,duration_in_minutes varchar2(10),foreign key (cust_id) references customer(cust_id))");
        db.execSQL("create table parking_slip(s_id integer primary key autoincrement,r_id integer , actual_entry_time varchar2(10), actual_exit_time varchar2(10),penalty_type varchar2(10),penalty_cost varchar2(10), basic_cost varchar2(10), foreign key (r_id) references reservation(r_id))");
        db.execSQL("create table payment(py_id integer primary key autoincrement, s_id varchar2(10),per_hour_charges varchar2(10),e_charging_per_hour varchar10(10),payment_mode varchar2(10),total_cost varchar2(10),cust_id varchar2(10),foreign key (cust_id) references customer(cust_id),foreign key (s_id) references parking_slip(s_id))");

        // INSERTION OF VALUES
        db.execSQL("INSERT INTO floor VALUES ('F1',10,'no','no')");
        db.execSQL("INSERT INTO floor VALUES ('F2',10,'no','no')");
        db.execSQL("INSERT INTO parkingLot VALUES ('L1','F1','yes','yes','yes')");
        db.execSQL("INSERT INTO parkingLot VALUES ('L2','F2','yes','yes','yes')");
        db.execSQL("INSERT INTO parkingSlot VALUES ('S1','no')");
        db.execSQL("INSERT INTO parkingSlot VALUES ('S2','no')");
        db.execSQL("INSERT INTO parkingSlot VALUES ('S3','no')");
        db.execSQL("INSERT INTO parkingSlot VALUES ('S4','no')");
        db.execSQL("INSERT INTO parkingSlot VALUES ('S5','no')");
        db.execSQL("INSERT INTO parkingSlot VALUES ('S6','no')");
        db.execSQL("INSERT INTO parkingSlot VALUES ('S7','no')");
        db.execSQL("INSERT INTO slot_details VALUES ('L1','S1')");
        db.execSQL("INSERT INTO slot_details VALUES ('L1','S2')");
        db.execSQL("INSERT INTO slot_details VALUES ('L1','S3')");
        db.execSQL("INSERT INTO slot_details VALUES ('L1','S4')");
        db.execSQL("INSERT INTO slot_details VALUES ('L2','S5')");
        db.execSQL("INSERT INTO slot_details VALUES ('L2','S6')");
        db.execSQL("INSERT INTO slot_details VALUES ('L2','S7')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldversion, int newversion)
    {
        db.execSQL("DROP TABLE IF EXISTS floor");
        db.execSQL("DROP TABLE IF EXISTS parkingLot");
        db.execSQL("DROP TABLE IF EXISTS parkingSlot");
        db.execSQL("DROP TABLE IF EXISTS slot_details");
        db.execSQL("DROP TABLE IF EXISTS customer");
        db.execSQL("DROP TABLE IF EXISTS electric_parking_slot");
        db.execSQL("DROP TABLE IF EXISTS regular_customer");
        db.execSQL("DROP TABLE IF EXISTS reservation");
        db.execSQL("DROP TABLE IF EXISTS parking_slip");
        db.execSQL("DROP TABLE IF EXISTS payment");

        onCreate(db);
    }
    public void  clear()
    {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS parkingLot");
        db.execSQL("DROP TABLE IF EXISTS floor");
        db.execSQL("DROP TABLE IF EXISTS parkingSlot");
        db.execSQL("DROP TABLE IF EXISTS floorDetails");
        db.execSQL("DROP TABLE IF EXISTS customer");
        onCreate(db);

    }
    public String addtwo(String p1, String p2, String p3,String p4,int slot ,String sl,String vehicle_type,String cus_type)
    {
        //count++;
       // ct++;
        SQLiteDatabase db=this.getWritableDatabase();
        String s = "S"+String.valueOf(slot);

        ContentValues cv = new ContentValues();
        cv.put("ps_id", sl);
        cv.put("vehicle_number",p1);
        cv.put("contact_number",p2);
        cv.put("registration_date", p3);
        cv.put("entry_time",p4);
        cv.put("is_vehicle_electric",vehicle_type);
        cv.put("is_regular_customer",cus_type);
        long res =db.insert("Customer",null,cv);
        if(res==-1 )
            return "FAILED";
        else
            {
                db.execSQL("Update parkingSlot set is_slot_full='yes' Where ps_id like '%"+slot+"%'");

            return "Park your vehicle here: "+sl+"\nSUCCESSFULLY PARKED";
            }
        }

    public Cursor getdata(String table,String att,String con)
    {
        SQLiteDatabase dbs = this.getReadableDatabase();
        String qry = "SELECT * from "+table +" where "+ att +" ="+con+"";
        Cursor crs = dbs.rawQuery(qry,null);
        if(crs==null)
            return null;
        return crs;
    }
    public Cursor getcusid()
    {
        SQLiteDatabase dbs = this.getReadableDatabase();
        String qry = "SELECT * from customer";
        Cursor crs = dbs.rawQuery(qry,null);
        if(crs==null)
            return null;
        return crs;
    }
    public Cursor getrid()
    {
        SQLiteDatabase dbs = this.getReadableDatabase();
        String qry = "SELECT * from reservation";
        Cursor crs = dbs.rawQuery(qry,null);
        if(crs==null)
            return null;
        return crs;
    }
    public Cursor getAvailableSlot(String table)
    {
        SQLiteDatabase dbs = this.getReadableDatabase();
        String qry = "SELECT * from "+table +" where is_slot_full like '%no%'";
        Cursor crs = dbs.rawQuery(qry,null);

        if(crs==null)
            return null;
        return crs;

    }

}
