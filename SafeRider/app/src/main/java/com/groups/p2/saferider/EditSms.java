package com.groups.p2.saferider;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.data.Freezable;

/**
 * Created by rodrigo on 27/10/15.
 */
public class EditSms extends Fragment {

    public EditSms(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.editsms, container, false);

        return view;
    }
}
