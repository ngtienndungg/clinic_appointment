package com.example.clinic_appointment.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.clinic_appointment.adapters.SelectTimeAdapter;
import com.example.clinic_appointment.databinding.ActivitySelectTimeBinding;
import com.example.clinic_appointment.listeners.AppointmentTimeListener;
import com.example.clinic_appointment.models.AppointmentTime.AppointmentTime;
import com.example.clinic_appointment.models.Department.Department;
import com.example.clinic_appointment.models.Doctor.Doctor;
import com.example.clinic_appointment.models.HealthFacility.HealthFacility;
import com.example.clinic_appointment.models.Schedule.Schedule;
import com.example.clinic_appointment.utilities.Constants;

import java.util.List;
import java.util.Objects;

public class SelectTimeActivity extends AppCompatActivity implements AppointmentTimeListener {
    private ActivitySelectTimeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectTimeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initiate();
        eventHandling();
    }

    private void initiate() {
        List<AppointmentTime> appointmentTimes;
        Schedule schedule = (Schedule) getIntent().getSerializableExtra(Constants.KEY_DATE);
        appointmentTimes = Objects.requireNonNull(schedule).getAppointmentTimes();
        SelectTimeAdapter adapter = new SelectTimeAdapter(appointmentTimes, this);
        int numberOfColumns = 3;
        binding.rvAppointmentTimes.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        binding.rvAppointmentTimes.setAdapter(adapter);
        binding.rvAppointmentTimes.setVisibility(View.VISIBLE);
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

    private void eventHandling() {
        binding.ivBack.setOnClickListener(v -> onBackPressed());
    }

    @Override
    public void onClick(AppointmentTime appointmentTime) {
        Intent intent = new Intent(this, ConfirmationActivity.class);
        Doctor selectedDoctor = (Doctor) getIntent().getSerializableExtra(Constants.KEY_DOCTOR);
        Department selectedDepartment = (Department) getIntent().getSerializableExtra(Constants.KEY_DEPARTMENT);
        HealthFacility selectedHealthFacility = (HealthFacility) getIntent().getSerializableExtra(Constants.KEY_HEALTH_FACILITY);
        Schedule selectedSchedule = (Schedule) getIntent().getSerializableExtra(Constants.KEY_DATE);
        intent.putExtra(Constants.KEY_DATE, selectedSchedule);
        intent.putExtra(Constants.KEY_DOCTOR, selectedDoctor);
        intent.putExtra(Constants.KEY_DEPARTMENT, selectedDepartment);
        intent.putExtra(Constants.KEY_HEALTH_FACILITY, selectedHealthFacility);
        intent.putExtra(Constants.KEY_TIME, appointmentTime.getTimeNumber());
        startActivity(intent);
    }
}