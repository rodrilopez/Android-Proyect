package com.groups.p2.saferider;

import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by rodrigo on 20/10/15.
 */
public class Settings2 extends Fragment {

    public Settings2(){}


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.settings2, container, false);

        SQLiteHelper admin = new SQLiteHelper(inflater.getContext(), "Config", null, 1);
        SQLiteDatabase bd = admin.getReadableDatabase();
        Cursor f = bd.rawQuery("select * from Config where id =" + 1, null);

        f.moveToFirst();

        System.out.println(f.getString(2));
        Toast.makeText(inflater.getContext(), "da", Toast.LENGTH_SHORT).show();

        //#Retomar




        return rootView;
    }


}
