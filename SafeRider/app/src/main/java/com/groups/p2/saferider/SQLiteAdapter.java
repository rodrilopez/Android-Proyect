package com.groups.p2.saferider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by rodrigo on 17/11/15.
 */
public class SQLiteAdapter extends SQLiteHelper{


    public SQLiteAdapter(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void Cargar(Context context, String name, ContentValues registro){
        SQLiteHelper admin = new SQLiteHelper(context,name,null,1);
        SQLiteDatabase db = admin.getWritableDatabase();
        db.insert(name, null, registro);
        db.close();
    }

    public void Actualizar(Context context, String name, ContentValues NewValues, int id){
        SQLiteHelper admin = new SQLiteHelper(context,name,null,1);
        SQLiteDatabase db = admin.getWritableDatabase();
        db.update(name, NewValues, "id=" + id, null);
        db.close();
    }

    public String Leer(Context context, String name, int id, int pos){
        SQLiteHelper admin = new SQLiteHelper(context,name,null,1);
        SQLiteDatabase db = admin.getReadableDatabase();
        Cursor f = db.rawQuery("select * from " + name + " where id=" + id, null);
        f.moveToFirst();
        ArrayList<String> array = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            array.add(f.getString(i));
        }
        return array.get(pos);
    }
}
