package com.example.clinic_appointment.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.clinic_appointment.databinding.ActivityConfirmationBinding;
import com.example.clinic_appointment.models.Department.Department;
import com.example.clinic_appointment.models.Doctor.Doctor;
import com.example.clinic_appointment.models.HealthFacility.HealthFacility;
import com.example.clinic_appointment.models.Schedule.Schedule;
import com.example.clinic_appointment.utilities.Constants;
import com.example.clinic_appointment.utilities.SharedPrefs;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Objects;

public class ConfirmationActivity extends AppCompatActivity {
    private ActivityConfirmationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConfirmationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initiate();
    }

    @SuppressLint("SetTextI18n")
    private void initiate() {
        Doctor selectedDoctor = (Doctor) getIntent().getSerializableExtra(Constants.KEY_DOCTOR);
        Department selectedDepartment = (Department) getIntent().getSerializableExtra(Constants.KEY_DEPARTMENT);
        HealthFacility selectedHealthFacility = (HealthFacility) getIntent().getSerializableExtra(Constants.KEY_HEALTH_FACILITY);
        Schedule selectedSchedule = (Schedule) getIntent().getSerializableExtra(Constants.KEY_DATE);
        String timeNumber = getIntent().getStringExtra(Constants.KEY_TIME);

        binding.tvHealthFacility.setText(Objects.requireNonNull(selectedHealthFacility).getName());
        binding.tvDepartment.setText(Objects.requireNonNull(selectedDepartment).getName());
        binding.tvDoctor.setText(Objects.requireNonNull(selectedDoctor).getDoctorInformation().getFullName());
        binding.tvUid.setText(SharedPrefs.getInstance().getData(Constants.KEY_ACCESS_TOKEN, String.class));
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd - MM - yyyy", Locale.getDefault());
        String formattedDate = dateFormat.format(Objects.requireNonNull(selectedSchedule).getDate());
        binding.tvDate.setText(formattedDate);
        binding.tvPrice.setText(Objects.requireNonNull(selectedSchedule).getPrice() + " VND");
        switch (Objects.requireNonNull(timeNumber)) {
            case "1":
                binding.tvTime.setText("07:00 - 08:00");
                break;
            case "2":
                binding.tvTime.setText("08:00 - 09:00");
                break;
            case "3":
                binding.tvTime.setText("09:00 - 10:00");
                break;
            case "4":
                binding.tvTime.setText("10:00 - 11:00");
                break;
            case "5":
                binding.tvTime.setText("11:00 - 12:00");
                break;
            case "6":
                binding.tvTime.setText("13:00 - 14:00");
                break;
            case "7":
                binding.tvTime.setText("14:00 - 15:00");
                break;
            case "8":
                binding.tvTime.setText("15:00 - 16:00");
                break;
            case "9":
                binding.tvTime.setText("16:00 - 17:00");
                break;
            case "10":
                binding.tvTime.setText("17:00 - 18:00");
                break;
            case "11":
                binding.tvTime.setText("18:00 - 19:00");
                break;
            case "12":
                binding.tvTime.setText("19:00 - 20:00");
                break;
            case "13":
                binding.tvTime.setText("20:00 - 21:00");
                break;
            default:
                binding.tvTime.setText("00:00 - 00:00");
                break;
        }

    }
}