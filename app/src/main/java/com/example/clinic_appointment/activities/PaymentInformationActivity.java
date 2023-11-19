package com.example.clinic_appointment.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.clinic_appointment.R;
import com.example.clinic_appointment.databinding.ActivityPaymentInformationBinding;
import com.example.clinic_appointment.models.Department.Department;
import com.example.clinic_appointment.models.Doctor.Doctor;
import com.example.clinic_appointment.models.HealthFacility.HealthFacility;
import com.example.clinic_appointment.models.Schedule.Schedule;
import com.example.clinic_appointment.utilities.Constants;

import java.util.Objects;

public class PaymentInformationActivity extends AppCompatActivity {
    private ActivityPaymentInformationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaymentInformationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    private void initiate() {
        Doctor selectedDoctor = (Doctor) getIntent().getSerializableExtra(Constants.KEY_DOCTOR);
        Department selectedDepartment = (Department) getIntent().getSerializableExtra(Constants.KEY_DEPARTMENT);
        HealthFacility selectedHealthFacility = (HealthFacility) getIntent().getSerializableExtra(Constants.KEY_HEALTH_FACILITY);
        Schedule selectedSchedule = (Schedule) getIntent().getSerializableExtra(Constants.KEY_DATE);
        String selectedTime = getIntent().getStringExtra(Constants.KEY_TIME);
        binding.tvHealthFacility.setText(Objects.requireNonNull(selectedHealthFacility).getName());
        binding.tvDepartment.setText(Objects.requireNonNull(selectedDepartment).getName());
        binding.tvDoctor.setText(Objects.requireNonNull(selectedDoctor).getDoctorInformation().getFullName());
    }
}