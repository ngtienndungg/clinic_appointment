package com.example.clinic_appointment.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.clinic_appointment.databinding.ItemContainterSelectHospitalBinding;
import com.example.clinic_appointment.models.Hospital.Hospital;

import java.util.ArrayList;
import java.util.List;

public class SelectHospitalAdapter extends RecyclerView.Adapter<SelectHospitalAdapter.HospitalViewHolder> {
    private List<Hospital> hospitals = new ArrayList<>();
    private final Context context;

    public SelectHospitalAdapter(List<Hospital> hospitals, Context context) {
        this.hospitals = hospitals;
        this.context = context;
    }

    @NonNull
    @Override
    public HospitalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HospitalViewHolder(ItemContainterSelectHospitalBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false));
    }

    @Override
    public void onBindViewHolder(@NonNull HospitalViewHolder holder, int position) {
        holder.setData(hospitals.get(position), context);
    }

    @Override
    public int getItemCount() {
        return hospitals.size();
    }

    public static class HospitalViewHolder extends RecyclerView.ViewHolder {
        private final ItemContainterSelectHospitalBinding binding;

        public HospitalViewHolder(@NonNull ItemContainterSelectHospitalBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setData(Hospital hospital, Context context) {
            String detailedAddress = hospital.getAddress().getWard() + ", " + hospital.getAddress().getDistrict() + ", " + hospital.getAddress().getProvince();
            binding.tvName.setText(hospital.getName());
            binding.tvAddress.setText(detailedAddress);
            Glide.with(context).load(hospital.getImage()).into(binding.ivImage);
        }
    }
}
