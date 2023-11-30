package com.example.clinic_appointment.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.clinic_appointment.databinding.ActivityHealthFacilityInformationBinding;
import com.example.clinic_appointment.models.HealthFacility.HealthFacility;
import com.example.clinic_appointment.utilities.Constants;

public class HealthFacilityInformationActivity extends AppCompatActivity {
    private ActivityHealthFacilityInformationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHealthFacilityInformationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initiate();
    }

    private void initiate() {
        HealthFacility healthFacility = (HealthFacility) getIntent().getSerializableExtra(Constants.KEY_HEALTH_FACILITY);
        if (healthFacility != null) {
            binding.tvName.setText(healthFacility.getName());
            Glide.with(this).load(healthFacility.getImage()).centerCrop().into(binding.ivLogo);
        }
    }
}