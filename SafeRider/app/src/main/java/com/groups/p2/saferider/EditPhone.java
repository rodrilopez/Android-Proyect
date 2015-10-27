package com.groups.p2.saferider;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by rodrigo on 27/10/15.
 */
public class EditPhone extends Fragment {

    public EditPhone(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.editphone, container, false);

        return view;
    }
}
