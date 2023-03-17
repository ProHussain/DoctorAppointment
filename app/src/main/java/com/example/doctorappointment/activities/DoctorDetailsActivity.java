package com.example.doctorappointment.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.example.doctorappointment.R;
import com.example.doctorappointment.adapters.TimeAdapter;
import com.example.doctorappointment.databinding.ActivityDoctorDetailsBinding;
import com.example.doctorappointment.databinding.AppointmentBottomSheetBinding;
import com.example.doctorappointment.models.AppointmentModel;
import com.example.doctorappointment.models.DoctorModel;
import com.example.doctorappointment.models.TimeModel;
import com.example.doctorappointment.utils.Utils;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DoctorDetailsActivity extends AppCompatActivity {
    ActivityDoctorDetailsBinding binding;
    private DoctorModel doctor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDoctorDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        doctor = (DoctorModel) getIntent().getSerializableExtra("Dr");
        setUI();

        binding.btnAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppointmentDialog();
            }
        });

        binding.drPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CallToDr();
            }
        });

        binding.drWhatsApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WhatsAppDr();
            }
        });
    }

    private void AppointmentDialog() {

        BottomSheetDialog dialog = new BottomSheetDialog(DoctorDetailsActivity.this);
        AppointmentBottomSheetBinding sheetBinding = AppointmentBottomSheetBinding.inflate(LayoutInflater.from(DoctorDetailsActivity.this));
        dialog.setContentView(sheetBinding.getRoot());
        sheetBinding.rvSheet.setLayoutManager(new LinearLayoutManager(DoctorDetailsActivity.this));

        List<TimeModel> timeModelList = new ArrayList<>();
        TimeAdapter adapter = new TimeAdapter(timeModelList,dialog,doctor);
        sheetBinding.rvSheet.setAdapter(adapter);
        
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Doctors").child("Time").child(doctor.getId());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1:snapshot.getChildren()) {
                    TimeModel model = snapshot1.getValue(TimeModel.class);
                    timeModelList.add(model);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Utils.ShowToast(DoctorDetailsActivity.this,"Error : "+error.getMessage());
            }
        });

        dialog.show();
    }

    private void WhatsAppDr() {
        Uri uri = Uri.parse("smsto:" + doctor.getPhone());
        Intent i = new Intent(Intent.ACTION_SENDTO, uri);
        i.setPackage("com.whatsapp");
        startActivity(Intent.createChooser(i, "Chat with"));
    }

    private void CallToDr() {
        String phone = (doctor.getPhone().startsWith("+")) ? doctor.getPhone() : "+"+doctor.getPhone();
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
        startActivity(intent);
    }

    private void setUI() {
        binding.drName.setText(doctor.getName());
        binding.drCatNdClinic.setText(doctor.getSpeciality() + " - " + doctor.getHospital());
        binding.drAbout.setText(doctor.getAbout());
        Glide.with(DoctorDetailsActivity.this).load(doctor.getImage()).placeholder(R.drawable.dr).into(binding.drImage);
        Glide.with(DoctorDetailsActivity.this).load(doctor.getImage()).placeholder(R.drawable.cover).into(binding.coverImage);
    }
}