package com.groups.p2.saferider;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rodrigo on 22/10/15.
 */
public class ItemAdapter extends BaseAdapter {
    private Context context;
    ArrayList<Item>arraylist2;


    public ItemAdapter(Context context, ArrayList<Item>list2array) {
        this.context = context;
        this.arraylist2 = list2array;
    }

    @Override
    public int getCount() {
        return this.arraylist2.size();
    }

    @Override
    public Object getItem(int position) {
        return this.arraylist2.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public static class Fila
    {
        TextView Titlef2;
        TextView Desc2;
        ImageView Itemf2;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup container) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        Fila view;

        if (convertView == null) {
            view = new Fila();
            //Creo objeto item y lo obtengo del array
            Item itm2=arraylist2.get(position);
            convertView = inflater.inflate(R.layout.list_item, null);
            view.Desc2 = (TextView) convertView.findViewById(R.id.desc);
            //Seteo en el campo titulo el nombre correspondiente obtenido del objeto
            view.Desc2.setText(itm2.getDesc());
            //Titulo
            view.Titlef2 = (TextView) convertView.findViewById(R.id.tvTitle);
            //Seteo en el campo titulo el nombre correspondiente obtenido del objeto
            view.Titlef2.setText(itm2.getTitle());
            //Icono
            view.Itemf2 = (ImageView) convertView.findViewById(R.id.ivItem);
            //Seteo el icono
            view.Itemf2.setImageResource(itm2.getImage());
            convertView.setTag(view);
        }
        else
        {
            view = (Fila) convertView.getTag();
        }
        return convertView;
    }
}
