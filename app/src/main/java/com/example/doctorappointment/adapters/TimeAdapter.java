package com.example.doctorappointment.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctorappointment.databinding.ItemTimeBinding;
import com.example.doctorappointment.models.AppointmentModel;
import com.example.doctorappointment.models.DoctorModel;
import com.example.doctorappointment.models.TimeModel;
import com.example.doctorappointment.utils.Constants;
import com.example.doctorappointment.utils.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class TimeAdapter extends RecyclerView.Adapter<TimeAdapter.ViewHolder> {

    Context context;
    List<TimeModel> timeModelList;
    BottomSheetDialog dialog;
    DoctorModel doctor;

    public TimeAdapter(List<TimeModel> timeModelList, BottomSheetDialog dialog, DoctorModel doctorModel) {
        this.timeModelList = timeModelList;
        this.dialog = dialog;
        doctor = doctorModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new ViewHolder(ItemTimeBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TimeModel model = timeModelList.get(position);
        holder.binding.txtStart.setText(model.getStartTime());
        holder.binding.txtEnd.setText(model.getEndTime());

        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
                String formattedDate = df.format(c);
                String time = model.getStartTime()+" - "+model.getEndTime();
                String orderID = doctor.getId()+formattedDate+time;
                AppointmentModel appointmentModel = new AppointmentModel(Constants.user.getId(),doctor.getId(),
                        doctor.getName(),time,formattedDate,orderID);
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Appointments");
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                         for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                            if (Objects.equals(dataSnapshot.child("order").getValue(), orderID)){
                                if (Objects.equals(dataSnapshot.child("userID").getValue(), Constants.user.getId())) {
                                    Utils.ShowToast(context,"You have already booked this appointment");
                                } else {
                                    Utils.ShowToast(context,"This appointment is already booked");
                                }
                                return;
                            }
                         }
                        reference.push().setValue(appointmentModel).addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Utils.ShowToast(context,"Appointment set");
                            }
                            dialog.dismiss();
                        });
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
    }

    @Override
    public int getItemCount() {
        return timeModelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemTimeBinding binding;
        public ViewHolder(@NonNull ItemTimeBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
