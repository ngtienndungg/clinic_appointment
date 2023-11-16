package com.example.clinic_appointment.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clinic_appointment.databinding.ItemContainerAppointmentTimeBinding;
import com.example.clinic_appointment.listeners.AppointmentTimeListener;
import com.example.clinic_appointment.models.AppointmentTime.AppointmentTime;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectTimeAdapter extends RecyclerView.Adapter<SelectTimeAdapter.ItemViewHolder> {
    private final List<AppointmentTime> appointmentTimes;
    private final AppointmentTimeListener listener;
    public static Map<String, String> timeMap;

    public SelectTimeAdapter(List<AppointmentTime> appointmentTimes, AppointmentTimeListener listener) {
        this.appointmentTimes = appointmentTimes;
        this.listener = listener;
        mapInitiation();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SelectTimeAdapter.ItemViewHolder(ItemContainerAppointmentTimeBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.setData(appointmentTimes.get(position));
        holder.binding.getRoot().setOnClickListener(v -> listener.onClick(appointmentTimes.get(holder.getBindingAdapterPosition())));
    }

    private void mapInitiation() {
        timeMap = new HashMap<>();
        timeMap.put("1", "07:00 - 08:00");
        timeMap.put("2", "08:00 - 09:00");
        timeMap.put("3", "09:00 - 10:00");
        timeMap.put("4", "10:00 - 11:00");
        timeMap.put("5", "11:00 - 12:00");
        timeMap.put("6", "13:00 - 14:00");
        timeMap.put("7", "14:00 - 15:00");
        timeMap.put("8", "15:00 - 16:00");
        timeMap.put("9", "16:00 - 17:00");
        timeMap.put("10", "17:00 - 18:00");
        timeMap.put("11", "18:00 - 19:00");
        timeMap.put("12", "19:00 - 20:00");
        timeMap.put("13", "20:00 - 21:00");
    }

    @Override
    public int getItemCount() {
        return appointmentTimes.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        private final ItemContainerAppointmentTimeBinding binding;

        public ItemViewHolder(ItemContainerAppointmentTimeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        private void setData(AppointmentTime appointmentTime) {
            for (Map.Entry<String, String> entry : timeMap.entrySet()) {
                if (appointmentTime.getTimeNumber().equals(entry.getKey())) {
                    binding.tvTime.setText(entry.getValue());
                    break;
                }
            }
        }
    }
}
