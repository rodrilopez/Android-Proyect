package com.groups.p2.saferider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by rodrigo on 06/10/15.
 */
public class SQLiteHelper extends SQLiteOpenHelper {


    public SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not esxist Config(dni int primary key autoincrement, name text, mail text, password text, phone int, count int)");
        db.execSQL("create table if not esxist Ubicacion(dni int primary key autoincrement, location int)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
}
