package com.example.doctorappointment.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.example.doctorappointment.R;
import com.example.doctorappointment.databinding.ActivityDoctorDetailsBinding;
import com.example.doctorappointment.models.DoctorModel;
import com.example.doctorappointment.utils.Utils;

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
                Utils.ShowToast(DoctorDetailsActivity.this,"Feature require Dr. App");
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