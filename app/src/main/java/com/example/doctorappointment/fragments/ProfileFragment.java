package com.example.doctorappointment.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.doctorappointment.databinding.FragmentProfileBinding;
import com.example.doctorappointment.utils.MyPreference;
import com.example.doctorappointment.utils.Utils;

import java.util.Objects;

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;
    private MyPreference preference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        preference = new MyPreference(requireContext());
        if (!preference.GetLogin()) {
            Utils.LoginDialog(requireContext());
        }
        SetUI();
        return root;
    }

    private void SetUI() {
        binding.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditProfile();
            }
        });
    }

    private void EditProfile() {
        if (preference.GetLogin()) {

        } else {
            Utils.LoginDialog(requireContext());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}