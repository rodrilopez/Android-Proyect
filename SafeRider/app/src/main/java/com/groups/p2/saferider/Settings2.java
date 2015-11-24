package com.groups.p2.saferider;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import static android.support.v4.media.MediaDescriptionCompatApi21.Builder.setTitle;

/**
 * Created by rodrigo on 20/10/15.
 */
public class Settings2 extends Fragment implements AdapterView.OnItemClickListener {

    private Context applicationContext;

    public Settings2(){}
    ListView NavList2;
    ItemAdapter NavAdapter2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.settings2, container, false);

        SQLiteAdapter sql = new SQLiteAdapter(inflater.getContext(),"Config",null, 1);



        NavList2 = (ListView) view.findViewById(R.id.listView);

        String item1 = sql.Leer(inflater.getContext(),"Config",1, 1);
        String item3 = sql.Leer(inflater.getContext(),"Config",1, 3);
        System.out.println(item1);

        ArrayList<Item> NavItms2 = new ArrayList<Item>();
        NavItms2.add(new Item(R.drawable.arrobapng, "E-Mail", item1));
        NavItms2.add(new Item(R.drawable.candado, "Password", "******"));
        NavItms2.add(new Item(R.drawable.telefono, "Phone", item3));
        // Sets the data behind this ListView
        NavAdapter2 = new ItemAdapter(inflater.getContext(), NavItms2);
        NavList2.setAdapter(NavAdapter2);
        NavList2.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
        MostrarFragment(position + 1);
    }

    private void MostrarFragment(int position){
        Fragment fragment= null;
        System.out.println(position);
        switch (position){
            case 1:
                fragment = new EditEmail();
                break;
            case 2:
                fragment = new EditPass();
                break;
            case 3:
                fragment = new EditPhone();
                break;
            default:

                position=1;
                break;
        }
        if (fragment!=null){
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
            NavList2.setItemChecked(position, true);
            NavList2.setSelection(position);
        }
    }
}
