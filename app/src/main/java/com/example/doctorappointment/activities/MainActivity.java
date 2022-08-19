package com.example.doctorappointment.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.doctorappointment.R;
import com.example.doctorappointment.adapter.CategoryAdapter;
import com.example.doctorappointment.adapter.DoctorAdapter;

public class MainActivity extends AppCompatActivity {
    RecyclerView catRV,drRV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        catRV = findViewById(R.id.carRV);
        drRV = findViewById(R.id.topDrRV);

    }
}