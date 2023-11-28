package com.example.clinic_appointment.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.clinic_appointment.adapters.SelectDepartmentAdapter;
import com.example.clinic_appointment.databinding.ActivitySelectDepartmentBinding;
import com.example.clinic_appointment.listeners.DepartmentListener;
import com.example.clinic_appointment.models.Department.Department;
import com.example.clinic_appointment.models.Department.DepartmentResponse;
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
    private HealthFacility selectedHealthFacility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectDepartmentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initiate();
        eventHandling();
    }

    private void initiate() {
        if (getIntent().getStringExtra(Constants.KEY_SOURCE_ACTIVITY) == null) {
            selectedHealthFacility = (HealthFacility) getIntent().getSerializableExtra(Constants.KEY_HEALTH_FACILITY);
            Call<HealthFacilityResponse> call = RetrofitClient.getPublicAppointmentService().getHealthFacilityById(selectedHealthFacility.getId());
            call.enqueue(new Callback<HealthFacilityResponse>() {
                @Override
                public void onResponse(@NonNull Call<HealthFacilityResponse> call, @NonNull Response<HealthFacilityResponse> response) {
                    if (response.body() != null && response.body().isSuccess() && response.body().getHealthFacility().getDepartments().size() > 0) {
                        HealthFacility healthFacility = response.body().getHealthFacility();
                        List<Department> departments = healthFacility.getDepartments();
                        SelectDepartmentAdapter adapter = new SelectDepartmentAdapter(SelectDepartmentActivity.this, departments);
                        binding.rvDepartment.setAdapter(adapter);
                        binding.rvDepartment.setVisibility(View.VISIBLE);
                        binding.pbLoading.setVisibility(View.GONE);
                    } else {
                        binding.tvNotFound.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<HealthFacilityResponse> call, @NonNull Throwable t) {
                    displayError();
                }
            });
        } else {
            Call<DepartmentResponse> call = RetrofitClient.getPublicAppointmentService().getEntireDepartment();
            call.enqueue(new Callback<DepartmentResponse>() {
                @Override
                public void onResponse(@NonNull Call<DepartmentResponse> call, @NonNull Response<DepartmentResponse> response) {
                    if (response.body() != null && response.body().isSuccess() && response.body().getDepartments().size() > 0) {
                        List<Department> departments = response.body().getDepartments();
                        SelectDepartmentAdapter adapter = new SelectDepartmentAdapter(SelectDepartmentActivity.this, departments);
                        binding.rvDepartment.setAdapter(adapter);
                        binding.rvDepartment.setVisibility(View.VISIBLE);
                        binding.pbLoading.setVisibility(View.GONE);
                    } else {
                        binding.tvNotFound.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<DepartmentResponse> call, @NonNull Throwable t) {
                    displayError();
                }
            });
        }
    }

    private void displayError() {
        binding.pbLoading.setVisibility(View.GONE);
        binding.llError.setVisibility(View.VISIBLE);
    }

    private void eventHandling() {
        binding.ivBack.setOnClickListener(v -> onBackPressed());
    }

    @Override
    public void onClick(Department department) {
        if (getIntent().getStringExtra(Constants.KEY_SOURCE_ACTIVITY) == null) {
            Intent intent = new Intent(this, SelectDoctorActivity.class);
            intent.putExtra(Constants.KEY_DEPARTMENT, department);
            intent.putExtra(Constants.KEY_HEALTH_FACILITY, selectedHealthFacility);
            startActivity(intent);
        } else {
            Intent intent = new Intent();
            intent.putExtra(Constants.KEY_DEPARTMENT, department);
            setResult(RESULT_OK, intent);
            onBackPressed();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) ev.getRawX(), (int) ev.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }
}