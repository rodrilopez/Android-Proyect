package com.groups.p2.saferider;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by rodrigo on 19/11/15.
 */
public class PassConfirm extends Fragment {

    public PassConfirm(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.passconfirm, container, false);
        return rootView;
    }
}
