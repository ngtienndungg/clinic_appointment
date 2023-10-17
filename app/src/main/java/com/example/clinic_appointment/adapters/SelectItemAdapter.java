package com.example.clinic_appointment.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.clinic_appointment.databinding.ItemContainterSelectItemBinding;
import com.example.clinic_appointment.listeners.ItemListener;
import com.example.clinic_appointment.models.Department.Department;
import com.example.clinic_appointment.models.Hospital.Hospital;
import com.example.clinic_appointment.utilities.Searchable;

import java.util.List;

public class SelectItemAdapter<T extends Searchable> extends RecyclerView.Adapter<SelectItemAdapter.ItemViewHolder> {
    private final ItemListener itemListener;
    private final List<T> items;
    private final Context context;
    private final Class<? extends Searchable> itemClass;

    public SelectItemAdapter(List<T> items, ItemListener itemListener, Context context) {
        this.items = items;
        this.context = context;
        this.itemListener = itemListener;
        itemClass = items.get(0).getClass();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(ItemContainterSelectItemBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false));
    }

    @Override
    public void onBindViewHolder(@NonNull SelectItemAdapter.ItemViewHolder holder, int position) {
        if (itemClass.equals(Hospital.class)) {
            holder.setHospitalData((Hospital) items.get(position), context);
            holder.binding.getRoot().setOnClickListener(v -> itemListener.onSelect((Hospital) items.get(holder.getAdapterPosition())));
        } else if (itemClass.equals(Department.class)) {
            holder.setDepartmentData((Department) items.get(position));
            holder.binding.getRoot().setOnClickListener(v -> itemListener.onSelect((Department) items.get(holder.getAdapterPosition())));
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        private final ItemContainterSelectItemBinding binding;

        public ItemViewHolder(@NonNull ItemContainterSelectItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setHospitalData(Hospital hospital, Context context) {
            binding.rlOtherItem.setVisibility(View.GONE);
            String detailedAddress = hospital.getAddress().getWard() + ", " + hospital.getAddress().getDistrict() + ", " + hospital.getAddress().getProvince();
            binding.tvHospitalName.setText(hospital.getName());
            binding.tvHospitalAddress.setText(detailedAddress);
            Glide.with(context).load(hospital.getImage()).centerCrop().into(binding.ivHospitalImage);
            binding.ctlHospital.setVisibility(View.VISIBLE);
        }

        public void setDepartmentData(Department department) {
            binding.ctlHospital.setVisibility(View.GONE);
            binding.ivHospitalImage.setVisibility(View.GONE);
            binding.tvHospitalAddress.setVisibility(View.GONE);
            binding.tvName.setText(department.getName());
            binding.rlOtherItem.setVisibility(View.VISIBLE);
        }
    }
}
