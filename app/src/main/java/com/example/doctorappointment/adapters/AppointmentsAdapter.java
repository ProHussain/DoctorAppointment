package com.example.doctorappointment.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.doctorappointment.R;
import com.example.doctorappointment.databinding.ItemAppointmentBinding;
import com.example.doctorappointment.models.AppointmentModel;

import java.util.List;

public class AppointmentsAdapter extends RecyclerView.Adapter<AppointmentsAdapter.ViewHolder> {
    Context context;
    private List<AppointmentModel> appointmentList;
    public AppointmentsAdapter(List<AppointmentModel> appointmentList) {
        this.appointmentList = appointmentList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new AppointmentsAdapter.ViewHolder(ItemAppointmentBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AppointmentModel appointment = appointmentList.get(position);
        holder.binding.textDrName.setText(appointment.getDrName());
        holder.binding.textDateTime.setText(getDateAndTime(appointment.getDate(),appointment.getTime()));
        String pathUri="file:///android_asset/"+"10.png";
        Glide.with(context).asBitmap().load(Uri.parse(pathUri)).into(holder.binding.imgDate);
    }

    private String getDateAndTime(String date, String time) {
        return date.substring(3)+" "+time;
    }

    @Override
    public int getItemCount() {
        return appointmentList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemAppointmentBinding binding;
        public ViewHolder(@NonNull ItemAppointmentBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}

