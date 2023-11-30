package com.example.clinic_appointment.activities;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.clinic_appointment.adapters.RatingAdapter;
import com.example.clinic_appointment.databinding.ActivityHealthFacilityInformationBinding;
import com.example.clinic_appointment.models.HealthFacility.HealthFacility;
import com.example.clinic_appointment.models.HealthFacility.HealthFacilityResponse;
import com.example.clinic_appointment.networking.clients.RetrofitClient;
import com.example.clinic_appointment.utilities.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
            Call<HealthFacilityResponse> call = RetrofitClient.getPublicAppointmentService().getHealthFacilityById(healthFacility.getId());
            call.enqueue(new Callback<HealthFacilityResponse>() {
                @Override
                public void onResponse(@NonNull Call<HealthFacilityResponse> call, @NonNull Response<HealthFacilityResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        RatingAdapter ratingAdapter = new RatingAdapter(response.body().getHealthFacility().getRatings());
                        binding.rvRatings.setAdapter(ratingAdapter);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<HealthFacilityResponse> call, @NonNull Throwable t) {

                }
            });
        }
    }
}