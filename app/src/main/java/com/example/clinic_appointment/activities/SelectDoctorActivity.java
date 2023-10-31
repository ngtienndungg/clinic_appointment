package com.example.clinic_appointment.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.clinic_appointment.adapters.SelectDoctorAdapter;
import com.example.clinic_appointment.databinding.ActivitySelectDoctorBinding;
import com.example.clinic_appointment.listeners.DoctorListener;
import com.example.clinic_appointment.models.Department.Department;
import com.example.clinic_appointment.models.Doctor.Doctor;
import com.example.clinic_appointment.models.Doctor.DoctorResponse;
import com.example.clinic_appointment.models.HealthFacility.HealthFacility;
import com.example.clinic_appointment.networking.clients.RetrofitClient;
import com.example.clinic_appointment.utilities.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectDoctorActivity extends AppCompatActivity implements DoctorListener {
    private ActivitySelectDoctorBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectDoctorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initiate();
    }

    private void initiate() {
        Department department = (Department) getIntent().getSerializableExtra(Constants.KEY_DEPARTMENT);
        HealthFacility healthFacility = (HealthFacility) getIntent().getSerializableExtra(Constants.KEY_HEALTH_FACILITY);
        Log.d("TestData", healthFacility.getId() + " " + department.getId());
        Call<DoctorResponse> call = RetrofitClient.getPublicAppointmentService().getDoctorByDepartmentAndHealthFacility(department.getId(), healthFacility.getId());
        call.enqueue(new Callback<DoctorResponse>() {
            @Override
            public void onResponse(@NonNull Call<DoctorResponse> call, @NonNull Response<DoctorResponse> response) {
                if (response.body() != null && response.body().isSuccess()) {
                    List<Doctor> doctors = response.body().getDoctors();
                    SelectDoctorAdapter adapter = new SelectDoctorAdapter(SelectDoctorActivity.this, doctors);
                    binding.rvDoctor.setAdapter(adapter);
                    binding.rvDoctor.setVisibility(View.VISIBLE);
                    binding.pbLoading.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<DoctorResponse> call, @NonNull Throwable t) {
                displayError();
            }
        });

    }

    private void displayError() {
        binding.pbLoading.setVisibility(View.GONE);
        binding.rlError.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(Doctor doctor) {

    }
}