package com.example.doctorappointment.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.doctorappointment.adapter.AppointmentsAdapter;
import com.example.doctorappointment.databinding.FragmentAppointmentsBinding;

public class AppointmentsFragment extends Fragment {

    private FragmentAppointmentsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAppointmentsBinding.inflate(inflater, container, false);
        AppointmentsAdapter adapter = new AppointmentsAdapter();
        binding.rvAppointments.setAdapter(adapter);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}