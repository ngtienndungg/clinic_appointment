package com.example.clinic_appointment.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.clinic_appointment.databinding.ActivityConfirmationBinding;
import com.example.clinic_appointment.models.Department.Department;
import com.example.clinic_appointment.models.Doctor.Doctor;
import com.example.clinic_appointment.models.HealthFacility.HealthFacility;
import com.example.clinic_appointment.models.Schedule.DetailSchedule;
import com.example.clinic_appointment.utilities.Constants;
import com.example.clinic_appointment.utilities.CustomConverter;
import com.example.clinic_appointment.utilities.SharedPrefs;

import java.util.Objects;

public class ConfirmationActivity extends AppCompatActivity {
    private ActivityConfirmationBinding binding;
    private Doctor selectedDoctor;
    private Department selectedDepartment;
    private HealthFacility selectedHealthFacility;
    private DetailSchedule selectedSchedule;
    private String timeNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConfirmationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initiate();
        eventHandling();
    }

    @SuppressLint("SetTextI18n")
    private void initiate() {
        selectedDoctor = (Doctor) getIntent().getSerializableExtra(Constants.KEY_DOCTOR);
        selectedDepartment = (Department) getIntent().getSerializableExtra(Constants.KEY_DEPARTMENT);
        selectedHealthFacility = (HealthFacility) getIntent().getSerializableExtra(Constants.KEY_HEALTH_FACILITY);
        selectedSchedule = (DetailSchedule) getIntent().getSerializableExtra(Constants.KEY_DATE);
        timeNumber = getIntent().getStringExtra(Constants.KEY_TIME);
        binding.tvHealthFacility.setText(Objects.requireNonNull(selectedHealthFacility).getName());
        binding.tvDepartment.setText(Objects.requireNonNull(selectedDepartment).getName());
        binding.tvDoctor.setText(Objects.requireNonNull(selectedDoctor).getDoctorInformation().getFullName());
        binding.tvUid.setText(SharedPrefs.getInstance().getData(Constants.KEY_CURRENT_UID, String.class));
        binding.tvDate.setText(CustomConverter.getFormattedDate(selectedSchedule.getDate()));
        binding.tvPrice.setText(Objects.requireNonNull(selectedSchedule).getPrice() + " VND");
        binding.tvTime.setText(CustomConverter.getStringAppointmentTime(timeNumber));
    }

    private void eventHandling() {
        binding.ivBack.setOnClickListener(v -> onBackPressed());
        binding.tvConfirm.setOnClickListener(v -> {
            Intent intent = new Intent(this, SelectPaymentMethodActivity.class);
            intent.putExtra(Constants.KEY_DATE, selectedSchedule);
            intent.putExtra(Constants.KEY_DOCTOR, selectedDoctor);
            intent.putExtra(Constants.KEY_DEPARTMENT, selectedDepartment);
            intent.putExtra(Constants.KEY_HEALTH_FACILITY, selectedHealthFacility);
            intent.putExtra(Constants.KEY_TIME, timeNumber);
            startActivity(intent);
        });
    }
}