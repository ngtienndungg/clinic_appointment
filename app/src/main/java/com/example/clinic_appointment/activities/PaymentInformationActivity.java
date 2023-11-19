package com.example.clinic_appointment.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.clinic_appointment.databinding.ActivityPaymentInformationBinding;
import com.example.clinic_appointment.models.Department.Department;
import com.example.clinic_appointment.models.Doctor.Doctor;
import com.example.clinic_appointment.models.HealthFacility.HealthFacility;
import com.example.clinic_appointment.models.Schedule.Schedule;
import com.example.clinic_appointment.utilities.Constants;
import com.example.clinic_appointment.utilities.CustomConverter;
import com.example.clinic_appointment.utilities.SharedPrefs;

import java.util.Objects;

public class PaymentInformationActivity extends AppCompatActivity {
    private ActivityPaymentInformationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaymentInformationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initiate();
    }

    @SuppressLint("SetTextI18n")
    private void initiate() {
        Doctor selectedDoctor = (Doctor) getIntent().getSerializableExtra(Constants.KEY_DOCTOR);
        Department selectedDepartment = (Department) getIntent().getSerializableExtra(Constants.KEY_DEPARTMENT);
        HealthFacility selectedHealthFacility = (HealthFacility) getIntent().getSerializableExtra(Constants.KEY_HEALTH_FACILITY);
        Schedule selectedSchedule = (Schedule) getIntent().getSerializableExtra(Constants.KEY_DATE);
        String selectedTime = getIntent().getStringExtra(Constants.KEY_TIME);
        binding.tvHealthFacility.setText(Objects.requireNonNull(selectedHealthFacility).getName());
        binding.tvDepartment.setText(Objects.requireNonNull(selectedDepartment).getName());
        binding.tvDoctor.setText(Objects.requireNonNull(selectedDoctor).getDoctorInformation().getFullName());
        binding.tvDate.setText(CustomConverter.getFormattedDate(Objects.requireNonNull(selectedSchedule).getDate()));
        binding.tvPrice.setText(Objects.requireNonNull(selectedSchedule).getPrice() + " VND");
        binding.tvTime.setText(CustomConverter.getStringAppointmentTime(Objects.requireNonNull(selectedTime)));
        binding.tvPatientName.setText(SharedPrefs.getInstance().getData(Constants.KEY_CURRENT_NAME, String.class));
        binding.tvPatientEmail.setText(SharedPrefs.getInstance().getData(Constants.KEY_CURRENT_EMAIL, String.class));
        binding.tvPatientPhoneNumber.setText(SharedPrefs.getInstance().getData(Constants.KEY_CURRENT_PHONE_NUMBER, String.class));
        binding.tvUtilityPrice.setText(selectedSchedule.getPrice() / 10 + " VND");
        binding.tvTotalPrice.setText(selectedSchedule.getPrice() + selectedSchedule.getPrice() / 10 + " VND");
    }
}