package com.example.clinic_appointment.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.clinic_appointment.databinding.ActivitySelectPaymentMethodBinding;

public class SelectPaymentMethodActivity extends AppCompatActivity {
    private ActivitySelectPaymentMethodBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectPaymentMethodBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        eventHandling();
    }

    private void eventHandling() {
        binding.tvOnsite.setOnClickListener(v -> startActivity(new Intent(this, PaymentInformationActivity.class)));
    }
}