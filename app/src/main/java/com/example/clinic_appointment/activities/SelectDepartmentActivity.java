package com.example.clinic_appointment.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.clinic_appointment.adapters.SelectDepartmentAdapter;
import com.example.clinic_appointment.databinding.ActivitySelectDepartmentBinding;
import com.example.clinic_appointment.listeners.DepartmentListener;
import com.example.clinic_appointment.models.Department.Department;
import com.example.clinic_appointment.models.HealthFacility.HealthFacility;
import com.example.clinic_appointment.models.HealthFacility.HealthFacilityResponse;
import com.example.clinic_appointment.networking.clients.RetrofitClient;
import com.example.clinic_appointment.utilities.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectDepartmentActivity extends AppCompatActivity implements DepartmentListener {

    private ActivitySelectDepartmentBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectDepartmentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initiate();
    }

    private void initiate() {
        HealthFacility selectedHF = (HealthFacility) getIntent().getSerializableExtra(Constants.KEY_HEALTH_FACILITY);
        Call<HealthFacilityResponse> call = RetrofitClient.getPublicAppointmentService().getHealthFacilityById(selectedHF.getId());
        call.enqueue(new Callback<HealthFacilityResponse>() {
            @Override
            public void onResponse(@NonNull Call<HealthFacilityResponse> call, @NonNull Response<HealthFacilityResponse> response) {
                if (response.body() != null && response.body().isSuccess()) {
                    HealthFacility healthFacility = response.body().getHealthFacility();
                    List<Department> departments = healthFacility.getDepartments();
                    SelectDepartmentAdapter adapter = new SelectDepartmentAdapter(SelectDepartmentActivity.this, departments);
                    binding.rvDepartment.setAdapter(adapter);
                    binding.rvDepartment.setVisibility(View.VISIBLE);
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
    public void onClick(Department department) {
        Intent intent = new Intent(this, SelectDoctorActivity.class);
        intent.putExtra(Constants.KEY_DEPARTMENT, department);
        intent.putExtra(Constants.KEY_HEALTH_FACILITY, getIntent().getSerializableExtra(Constants.KEY_HEALTH_FACILITY));
        startActivity(intent);
    }
}