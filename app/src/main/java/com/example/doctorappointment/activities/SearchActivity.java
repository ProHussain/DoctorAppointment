package com.example.doctorappointment.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.doctorappointment.R;
import com.example.doctorappointment.adapters.DoctorAdapter;
import com.example.doctorappointment.databinding.ActivitySearchBinding;
import com.example.doctorappointment.models.DoctorModel;
import com.example.doctorappointment.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    ActivitySearchBinding binding;
    private List<DoctorModel> doctorList;
    private DoctorAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String search = getIntent().getStringExtra("Search");
        doctorList = new ArrayList<>();
        adapter = new DoctorAdapter(doctorList);
        binding.rvSearch.setAdapter(adapter);

        SearchDoctor(search);
    }

    private void SearchDoctor(String search) {
        for (DoctorModel model: Constants.doctorModelsList) {
            if (model.getName().contains(search) || model.getSpeciality().contains(search)) {
                doctorList.add(model);
            }
        }

        if (doctorList.isEmpty()) {
            binding.rvSearch.setVisibility(View.GONE);
            binding.imgNotFound.setVisibility(View.VISIBLE);
        } else {
            adapter.notifyDataSetChanged();
        }
    }
}