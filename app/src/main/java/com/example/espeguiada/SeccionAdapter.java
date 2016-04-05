package com.example.espeguiada;

/**
 * Created by joelrivera on 4/5/16.
 */

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

public class SeccionAdapter extends ArrayAdapter<SECCION> {


    public SeccionAdapter(Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }
}
