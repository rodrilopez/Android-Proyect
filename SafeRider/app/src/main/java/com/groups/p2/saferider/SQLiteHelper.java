package com.groups.p2.saferider;

import android.content.Context;
import android.database.Cursor;
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
        db.execSQL("create table if not exists Config(id integer primary key autoincrement, name text, mail text, password text, phone text, sms text)");
        db.execSQL("create table if not exists Ubicacion(id integer primary key autoincrement, location int)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("CREATE TABLE IF NOT EXISTS USERS ( " +
                "First_name TEXT, " +
                "Last_name TEXT, "+
                "Email KEY TEXT NOT NULL, "+
                "Username TEXT NOT NULL, "+
                "Password TEXT NOT NULL)");
        this.onCreate(db);
    }


    }

