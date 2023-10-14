package com.example.clinic_appointment.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.clinic_appointment.databinding.ItemContainterSelectItemBinding;
import com.example.clinic_appointment.listeners.HospitalListener;
import com.example.clinic_appointment.models.Hospital.Hospital;

import java.util.List;

public class SelectHospitalAdapter extends RecyclerView.Adapter<SelectHospitalAdapter.HospitalViewHolder> {
    private final HospitalListener hospitalListener;
    private final List<Hospital> hospitals;
    private final Context context;

    public SelectHospitalAdapter(List<Hospital> hospitals, HospitalListener hospitalListener, Context context) {
        this.hospitals = hospitals;
        this.context = context;
        this.hospitalListener = hospitalListener;
    }

    @NonNull
    @Override
    public HospitalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HospitalViewHolder(ItemContainterSelectItemBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false));
    }

    @Override
    public void onBindViewHolder(@NonNull HospitalViewHolder holder, int position) {
        holder.setData(hospitals.get(position), context);
        holder.binding.getRoot().setOnClickListener(v -> {
            hospitalListener.onSelect(hospitals.get(holder.getAdapterPosition()));
        });
    }

    @Override
    public int getItemCount() {
        return hospitals.size();
    }

    public static class HospitalViewHolder extends RecyclerView.ViewHolder {
        private final ItemContainterSelectItemBinding binding;

        public HospitalViewHolder(@NonNull ItemContainterSelectItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setData(Hospital hospital, Context context) {
            String detailedAddress = hospital.getAddress().getWard() + ", " + hospital.getAddress().getDistrict() + ", " + hospital.getAddress().getProvince();
            binding.tvName.setText(hospital.getName());
            binding.tvAddress.setText(detailedAddress);
            Glide.with(context).load(hospital.getImage()).centerCrop().into(binding.ivImage);
        }
    }
}
