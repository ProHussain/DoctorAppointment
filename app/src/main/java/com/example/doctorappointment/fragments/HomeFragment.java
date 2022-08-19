package com.example.doctorappointment.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.doctorappointment.adapter.CategoryAdapter;
import com.example.doctorappointment.adapter.DoctorAdapter;
import com.example.doctorappointment.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        CategoryAdapter adapter = new CategoryAdapter();
        binding.carRV.setAdapter(adapter);
        DoctorAdapter doctorAdapter = new DoctorAdapter();
        binding.topDrRV.setAdapter(doctorAdapter);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}