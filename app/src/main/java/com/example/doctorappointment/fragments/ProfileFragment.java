package com.example.doctorappointment.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.doctorappointment.R;
import com.example.doctorappointment.activities.EditProfileActivity;
import com.example.doctorappointment.activities.SignUpActivity;
import com.example.doctorappointment.databinding.FragmentProfileBinding;
import com.example.doctorappointment.utils.Constants;
import com.example.doctorappointment.utils.MyPreference;
import com.example.doctorappointment.utils.Utils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;
    private MyPreference preference;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), EditProfileActivity.class));
            }
        });

        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogoutUser();
            }
        });
        return root;
    }

    private void LogoutUser() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getActivity(), SignUpActivity.class));
        requireActivity().finish();
    }

    @Override
    public void onResume() {
        super.onResume();
        SetUI();
    }

    private void SetUI() {
        binding.titleName.setText(Constants.user.getName());
        binding.txtName.setText(Constants.user.getName());
        binding.txtEmail.setText(Constants.user.getEmail());
        binding.txtBirthday.setText(Constants.user.getDob());
        binding.txtGender.setText(Constants.user.getGender());
        binding.txtLocation.setText(Constants.user.getLocation());

        if (Constants.user.getProfileImage() != null) {
            Glide.with(this).load(Constants.user.getProfileImage()).placeholder(R.drawable.user).into(binding.profileImage);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}