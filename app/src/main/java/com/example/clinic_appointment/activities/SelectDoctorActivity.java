package com.example.clinic_appointment.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.clinic_appointment.R;
import com.example.clinic_appointment.databinding.ActivitySelectDoctorBinding;

public class SelectDoctorActivity extends AppCompatActivity {
    private ActivitySelectDoctorBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectDoctorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initiate();
    }

    private void initiate() {

    }
}