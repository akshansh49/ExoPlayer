package com.example.magicpin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
    static String list[]={"SampleVideo1", "SampleVideo2", "SampleVideo3", "SampleVideo4", "SampleVideo5", "SampleVideo6"};;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView programmingList=(RecyclerView)findViewById(R.id.programmingList);
        programmingList.setLayoutManager(new LinearLayoutManager(this));
        programmingList.setAdapter(new ProgrammingAdapter(this,list));
    }
}
