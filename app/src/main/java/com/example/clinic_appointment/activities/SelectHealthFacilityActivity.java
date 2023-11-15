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

import com.example.clinic_appointment.adapters.SelectHealthFacilityAdapter;
import com.example.clinic_appointment.databinding.ActivitySelectHealthFacilityBinding;
import com.example.clinic_appointment.listeners.HealthFacilityListener;
import com.example.clinic_appointment.models.HealthFacility.HealthFacilitiesResponse;
import com.example.clinic_appointment.models.HealthFacility.HealthFacility;
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
        eventHandling();
    }

    private void initiate() {
        Call<HealthFacilitiesResponse> call = RetrofitClient.getPublicAppointmentService().getAllHealthFacilities();
        call.enqueue(new Callback<HealthFacilitiesResponse>() {
            @Override
            public void onResponse(@NonNull Call<HealthFacilitiesResponse> call, @NonNull Response<HealthFacilitiesResponse> response) {
                if (response.body() != null && response.body().isSuccess()) {
                    List<HealthFacility> healthFacilities = response.body().getHealthFacilities();
                    SelectHealthFacilityAdapter adapter = new SelectHealthFacilityAdapter(healthFacilities, SelectHealthFacilityActivity.this, getApplicationContext());
                    binding.rvHealthFacility.setAdapter(adapter);
                    binding.rvHealthFacility.setVisibility(View.VISIBLE);
                    binding.pbLoading.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<HealthFacilitiesResponse> call, @NonNull Throwable t) {
                displayError();
            }
        });
    }

    private void displayError() {
        binding.pbLoading.setVisibility(View.GONE);
        binding.rlError.setVisibility(View.VISIBLE);
    }

    private void eventHandling() {
        binding.ivBack.setOnClickListener(v -> onBackPressed());
    }

    @Override
    public void onClick(HealthFacility healthFacility) {
        Intent intent = new Intent(this, SelectDepartmentActivity.class);
        intent.putExtra(Constants.KEY_HEALTH_FACILITY, healthFacility);
        startActivity(intent);
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