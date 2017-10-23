package edu.smu.lab.afinal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by student on 11/7/16.
 */


public class Sqlite extends SQLiteOpenHelper {

    String col_name;


    public static final String database = "shotlist.db";
    public static final String table = "shotlist";
    public static final String col1 = "shot1Time";
    public static final String col2 = "shot2Time";
    public static final String col3 = "splitTime1";
    public static final String col4 = "shot3Time";
    public static final String col5 = "splitTime2";
    public static final String col6 = "shot4Time";
    public static final String col7 = "splitTime3";
    public static final String col8 = "shot5Time";
    public static final String col9 = "splitTime4";
    public static final String col10 = "lastShotTime";

    public Sqlite(Context context) {
        super(context,database,null,1);


    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+table+"(ID INTEGER PRIMARY KEY AUTOINCREMENT, shot1Time STRING, shot2Time STRING, splitTime1 STRING, shot3Time STRING, splitTime2 STRING, shot4Time STRING, splitTime3 STRING, shot5Time STRING, splitTime4 STRING, lastShotTime STRING )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST"+table);
        onCreate(db);
    }

    public boolean insertdata (String s1, String s2 , String sp1, String s3, String sp2, String s4, String sp3, String s5, String sp4, String last ) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value;
        value = new ContentValues();
        value.put(col1,s1);
        value.put(col2, s2);
        value.put(col3, sp1);
        value.put(col4, s3);
        value.put(col5, sp2);
        value.put(col6, s4);
        value.put(col7, sp3);
        value.put(col8, s5);
        value.put(col9, sp4);
        value.put(col10, last);

        long result = db.insert(table, null, value);
        if (result == -1)
            return false;
        else {
            return true;
        }
    }

    public Cursor getdata(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from shotlist",null);
        return res;

    }

    public void deleteAll()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(table,null,null);
        db.execSQL("delete from "+ table);
        db.close();

    }




}


