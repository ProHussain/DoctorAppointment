package com.example.doctorappointment.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.doctorappointment.activities.SearchActivity;
import com.example.doctorappointment.adapters.CategoryAdapter;
import com.example.doctorappointment.adapters.DoctorAdapter;
import com.example.doctorappointment.databinding.FragmentHomeBinding;
import com.example.doctorappointment.models.CategoryModel;
import com.example.doctorappointment.models.DoctorModel;
import com.example.doctorappointment.utils.Constants;
import com.example.doctorappointment.utils.Utils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private DoctorAdapter doctorAdapter;
    private CategoryAdapter categoryAdapter;
    private List<CategoryModel> categoryModels;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        categoryModels = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(categoryModels);
        binding.catRV.setAdapter(categoryAdapter);
        doctorAdapter = new DoctorAdapter(Constants.doctorModelsList);
        binding.topDrRV.setAdapter(doctorAdapter);

        LoadData();
        SearchFeature();
        return root;
    }

    private void SearchFeature() {
        binding.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String search = binding.edSearch.getText().toString();
                if (search.isEmpty()) {
                    return;
                }
                Intent intent = new Intent(requireActivity(), SearchActivity.class);
                intent.putExtra("Search",search);
                startActivity(intent);
            }
        });
    }

    private void LoadData() {
        LoadCategories();
        LoadDoctors();
    }

    private void LoadCategories() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Doctors").child("Categories").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Constants.doctorModelsList.clear();
                    for (DataSnapshot snapshot1:snapshot.getChildren()) {
                        CategoryModel model = snapshot1.getValue(CategoryModel.class);
                        categoryModels.add(model);
                    }
                    categoryAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Utils.ShowToast(requireActivity(),"Error : "+error.getMessage());
            }
        });
    }

    private void LoadDoctors() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Doctors").child("Accounts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Constants.doctorModelsList.clear();
                    for (DataSnapshot snapshot1:snapshot.getChildren()) {
                        DoctorModel model = snapshot1.getValue(DoctorModel.class);
                        Constants.doctorModelsList.add(model);
                    }
                    doctorAdapter.notifyDataSetChanged();
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