package com.example.clinic_appointment.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.clinic_appointment.R;
import com.example.clinic_appointment.adapters.SelectHospitalAdapter;
import com.example.clinic_appointment.databinding.ActivitySelectItemBinding;
import com.example.clinic_appointment.listeners.ItemListener;
import com.example.clinic_appointment.models.Hospital.Hospital;
import com.example.clinic_appointment.models.Hospital.HospitalResponse;
import com.example.clinic_appointment.networking.clients.RetrofitClient;
import com.example.clinic_appointment.utilities.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectItemActivity extends AppCompatActivity implements ItemListener {
    private ActivitySelectItemBinding binding;
    private String itemType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectItemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initiate();
        eventHandling();
    }

    private void initiate() {
        itemType = getItemType();
        if (itemType.equals(Constants.TYPE_HOSPITAL)) {
            getInitiateHospitalList();
        }

    }

    private String getItemType() {
        return getIntent().getStringExtra(Constants.KEY_ITEM_TYPE);
    }

    private void getInitiateHospitalList() {
        Call<HospitalResponse> call = RetrofitClient.getPublicAppointmentService().getFilteredClinic();
        call.enqueue(new Callback<HospitalResponse>() {
            @Override
            public void onResponse(@NonNull Call<HospitalResponse> call, @NonNull Response<HospitalResponse> response) {
                binding.pbLoading.setVisibility(View.VISIBLE);
                binding.tvAllMatch.setText(getString(R.string.from_all_hospital_and_clinic));
                binding.tvResult.setText(getString(R.string.popular_hospital_and_clinic));
                binding.etSearchInput.setHint(R.string.search_hint_select_hospital);
                if (response.body() != null && response.body().isSuccess()) {
                    List<Hospital> hospitals = response.body().getHospitals();
                    SelectHospitalAdapter adapter = new SelectHospitalAdapter(hospitals, SelectItemActivity.this, getApplicationContext());
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
        binding.tvClose.setOnClickListener(v -> onBackPressed());
        binding.tvAllMatch.setOnClickListener(v -> {
            setResult(RESULT_OK, new Intent());
            onBackPressed();
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_default, R.anim.slide_down);
    }

    @Override
    public void onSelect(Hospital hospital) {
        Intent intent = new Intent();
        intent.putExtra(Constants.KEY_SELECTED_ITEM, hospital);
        setResult(RESULT_OK, intent);
        onBackPressed();
    }
}