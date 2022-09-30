package com.example.doctorappointment.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.doctorappointment.R;
import com.example.doctorappointment.activities.DoctorDetailsActivity;
import com.example.doctorappointment.databinding.ItemDoctorBinding;
import com.example.doctorappointment.models.DoctorModel;
import com.example.doctorappointment.utils.Constants;

import java.util.List;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.ViewHolder> {
    Context context;
    private List<DoctorModel> doctorModelList;
    public DoctorAdapter(List<DoctorModel> doctorModelsList) {
        this.doctorModelList = doctorModelsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new ViewHolder(ItemDoctorBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DoctorModel doctor = doctorModelList.get(position);
        Glide.with(context).load(doctor.getImage()).placeholder(R.drawable.dr).into(holder.binding.imgDr);
        holder.binding.textDrName.setText(doctor.getName());
        holder.binding.textDrCategory.setText(doctor.getSpeciality() + " - " + doctor.getHospital());
        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DoctorDetailsActivity.class);
                intent.putExtra("Dr",doctor);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return doctorModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemDoctorBinding binding;
        public ViewHolder(@NonNull ItemDoctorBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
