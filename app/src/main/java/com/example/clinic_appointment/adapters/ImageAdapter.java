package com.example.clinic_appointment.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.clinic_appointment.databinding.ItemContainerImageBinding;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ItemViewHolder> {
    private final List<String> base64Images;

    public ImageAdapter(List<String> base64Images) {
        this.base64Images = base64Images;
    }

    @NonNull
    @Override
    public ImageAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ImageAdapter.ItemViewHolder(ItemContainerImageBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull ImageAdapter.ItemViewHolder holder, int position) {
        holder.setData(base64Images.get(position));
    }

    @Override
    public int getItemCount() {
        return base64Images.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        private final ItemContainerImageBinding binding;

        public ItemViewHolder(@NonNull ItemContainerImageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setData(String base64Image) {
            byte[] decodedBytes = Base64.decode(base64Image, Base64.DEFAULT);
            Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
            Glide.with(binding.ivImage.getContext()).load(decodedBitmap).into(binding.ivImage);
        }
    }
}
