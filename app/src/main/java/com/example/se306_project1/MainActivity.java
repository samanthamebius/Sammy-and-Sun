package com.example.se306_project1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.se306_project1.data.ClutchesDataProvider;
import com.example.se306_project1.data.CrossBodyBagsDataProvider;
import com.example.se306_project1.data.ToteBagsDataProvider;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ClutchesDataProvider.addClutchestoFirestore();
        ToteBagsDataProvider.addToteBagstoFirestore();
        CrossBodyBagsDataProvider.addCrossBodyBagstoFirestore();
    }
}