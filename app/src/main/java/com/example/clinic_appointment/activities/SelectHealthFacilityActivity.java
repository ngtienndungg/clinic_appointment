package com.example.clinic_appointment.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.clinic_appointment.adapters.SelectHealthFacilityAdapter;
import com.example.clinic_appointment.databinding.ActivitySelectHealthFacilityBinding;
import com.example.clinic_appointment.listeners.HealthFacilityListener;
import com.example.clinic_appointment.models.HealthFacility.HealthFacility;
import com.example.clinic_appointment.models.HealthFacility.HealthFacilityResponse;
import com.example.clinic_appointment.networking.clients.RetrofitClient;
import com.example.clinic_appointment.utilities.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectHealthFacilityActivity extends AppCompatActivity implements HealthFacilityListener {
    private ActivitySelectHealthFacilityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectHealthFacilityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initiate();
    }

    private void initiate() {
        Call<HealthFacilityResponse> call = RetrofitClient.getPublicAppointmentService().getAllHealthFacilities();
        call.enqueue(new Callback<HealthFacilityResponse>() {
            @Override
            public void onResponse(@NonNull Call<HealthFacilityResponse> call, @NonNull Response<HealthFacilityResponse> response) {
                if (response.body() != null && response.body().isSuccess()) {
                    List<HealthFacility> healthFacilities = response.body().getAllHealthFacilities();
                    SelectHealthFacilityAdapter adapter = new SelectHealthFacilityAdapter(healthFacilities, SelectHealthFacilityActivity.this, getApplicationContext());
                    binding.rvHealthFacility.setAdapter(adapter);
                    binding.rvHealthFacility.setVisibility(View.VISIBLE);
                    binding.pbLoading.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<HealthFacilityResponse> call, @NonNull Throwable t) {
                displayError();
            }
        });
    }

    private void displayError() {
        binding.pbLoading.setVisibility(View.GONE);
        binding.rlError.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(HealthFacility healthFacility) {
        Intent intent = new Intent(this, SelectDepartmentActivity.class);
        intent.putExtra(Constants.KEY_HEALTH_FACILITY, healthFacility);
        startActivity(intent);
    }
}