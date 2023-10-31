package com.example.clinic_appointment.activities;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.clinic_appointment.adapters.SelectDepartmentAdapter;
import com.example.clinic_appointment.databinding.ActivitySelectDepartmentBinding;
import com.example.clinic_appointment.listeners.DepartmentListener;
import com.example.clinic_appointment.models.Department.Department;
import com.example.clinic_appointment.models.Department.DepartmentResponse;
import com.example.clinic_appointment.networking.clients.RetrofitClient;

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
        Call<DepartmentResponse> call = RetrofitClient.getPublicAppointmentService().getFilteredDepartment();
        call.enqueue(new Callback<DepartmentResponse>() {
            @Override
            public void onResponse(@NonNull Call<DepartmentResponse> call, @NonNull Response<DepartmentResponse> response) {
                if (response.body() != null && response.body().isSuccess()) {
                    List<Department> departments = response.body().getDepartments();
                    SelectDepartmentAdapter adapter = new SelectDepartmentAdapter(SelectDepartmentActivity.this, departments);
                    binding.rvDepartment.setAdapter(adapter);
                    binding.rvDepartment.setVisibility(View.VISIBLE);
                    binding.pbLoading.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<DepartmentResponse> call, @NonNull Throwable t) {
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

    }
}