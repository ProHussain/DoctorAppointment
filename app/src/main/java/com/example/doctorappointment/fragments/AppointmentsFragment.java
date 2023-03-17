package com.example.doctorappointment.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.doctorappointment.adapters.AppointmentsAdapter;
import com.example.doctorappointment.databinding.FragmentAppointmentsBinding;
import com.example.doctorappointment.models.AppointmentModel;
import com.example.doctorappointment.utils.Constants;
import com.example.doctorappointment.utils.Utils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AppointmentsFragment extends Fragment {

    private FragmentAppointmentsBinding binding;
    private AppointmentsAdapter adapter;
    private List<AppointmentModel> appointmentList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAppointmentsBinding.inflate(inflater, container, false);
        appointmentList = new ArrayList<>();
        adapter = new AppointmentsAdapter(appointmentList);
        binding.rvAppointments.setAdapter(adapter);
        loadAppointments();
        return binding.getRoot();
    }

    private void loadAppointments() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Appointments").orderByChild("userID").equalTo(Constants.user.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    appointmentList.clear();
                    for (DataSnapshot snapshot1:snapshot.getChildren()) {
                        AppointmentModel model = snapshot1.getValue(AppointmentModel.class);
                        appointmentList.add(model);
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Utils.ShowToast(requireActivity(),"Error : "+error.getMessage());
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}