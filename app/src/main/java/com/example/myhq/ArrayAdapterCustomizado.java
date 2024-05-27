package com.example.myhq;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import java.util.List;

public class ArrayAdapterCustomizado extends ArrayAdapter {
    public ArrayAdapterCustomizado(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
    }
}
