package com.example.doctorappointment.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.doctorappointment.adapters.NotificationAdapter;
import com.example.doctorappointment.databinding.FragmentNotificationsBinding;
import com.example.doctorappointment.models.CategoryModel;
import com.example.doctorappointment.models.NotificationModel;
import com.example.doctorappointment.utils.Constants;
import com.example.doctorappointment.utils.Utils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;
    private NotificationAdapter adapter;
    private List<NotificationModel> notificationModelList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        notificationModelList = new ArrayList<>();
        adapter = new NotificationAdapter(notificationModelList);
        binding.rvNotifications.setAdapter(adapter);

        LoadNotifications();
        return binding.getRoot();
    }

    private void LoadNotifications() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Notifications");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    notificationModelList.clear();
                    for (DataSnapshot snapshot1:snapshot.getChildren()) {
                        NotificationModel model = snapshot1.getValue(NotificationModel.class);
                        notificationModelList.add(model);
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