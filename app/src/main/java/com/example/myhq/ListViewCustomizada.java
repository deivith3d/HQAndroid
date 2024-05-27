package com.example.myhq;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsCompat.Type;

public class ListViewCustomizada extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_customizada);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            int left = insets.getInsets(Type.systemBars()).left;
            int top = insets.getInsets(Type.systemBars()).top;
            int right = insets.getInsets(Type.systemBars()).right;
            int bottom = insets.getInsets(Type.systemBars()).bottom;
            v.setPadding(left, top, right, bottom);
            return insets;
        });
    }
}

