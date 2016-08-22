package com.example.tanishka.findapp;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class FindApp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_app);
        FragmentManager manager=getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.frame,AppFragment.newInstance()).commit();
    }
}
