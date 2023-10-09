package com.example.clinic_appointment.activities;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.clinic_appointment.R;
import com.example.clinic_appointment.adapters.SelectHospitalAdapter;
import com.example.clinic_appointment.databinding.ActivitySelectHospitalBinding;
import com.example.clinic_appointment.models.Hospital.Hospital;
import com.example.clinic_appointment.models.Hospital.HospitalResponse;
import com.example.clinic_appointment.networking.clients.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectHospitalActivity extends AppCompatActivity {
    private ActivitySelectHospitalBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectHospitalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initiate();
        eventHandling();
    }

    private void initiate() {
        Call<HospitalResponse> call = RetrofitClient.getPublicAppointmentService().getFilteredClinic();
        call.enqueue(new Callback<HospitalResponse>() {
            @Override
            public void onResponse(@NonNull Call<HospitalResponse> call, @NonNull Response<HospitalResponse> response) {
                binding.pbLoading.setVisibility(View.VISIBLE);
                if (response.body() != null && response.body().isSuccess()) {
                    List<Hospital> hospitals = response.body().getHospitals();
                    SelectHospitalAdapter adapter = new SelectHospitalAdapter(hospitals, getApplicationContext());
                    binding.rvResult.setAdapter(adapter);
                    binding.rvResult.setVisibility(View.VISIBLE);
                    binding.pbLoading.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<HospitalResponse> call, @NonNull Throwable t) {

            }
        });
    }

    private void eventHandling() {
        binding.tvClose.setOnClickListener(v -> {
            onBackPressed();
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_default, R.anim.slide_down);
    }
}