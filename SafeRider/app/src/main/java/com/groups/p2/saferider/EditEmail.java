package com.groups.p2.saferider;

import android.app.Fragment;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.zip.Inflater;

/**
 * Created by rodrigo on 27/10/15.
 */
public class EditEmail extends Fragment {

    public EditEmail(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.editemail, container, false);
        SQLiteHelper admin = new SQLiteHelper(inflater.getContext(), "Config", null, 1);
        SQLiteDatabase bd = admin.getReadableDatabase();
        Cursor f = bd.rawQuery("select * from Config where id =" + 1, null);
        f.moveToFirst();
        TextView tv1 = (TextView) view.findViewById(R.id.textView6);
        tv1.setText(f.getString(2));

        return view;
    }
}
