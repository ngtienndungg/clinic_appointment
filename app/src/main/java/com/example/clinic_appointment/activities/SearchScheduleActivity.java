package com.example.clinic_appointment.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;

import com.example.clinic_appointment.R;
import com.example.clinic_appointment.databinding.ActivitySearchScheduleBinding;
import com.example.clinic_appointment.models.Department.Department;
import com.example.clinic_appointment.models.HealthFacility.HealthFacility;
import com.example.clinic_appointment.utilities.Constants;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class SearchScheduleActivity extends AppCompatActivity {
    private HealthFacility selectedHealthFacility;
    private Department selectedDepartment;
    private long dateFrom = 0;
    private long dateTo = 0;
    private ActivitySearchScheduleBinding binding;
    private final ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                Intent intent = result.getData();
                Integer resultCode = result.getResultCode();
                if (resultCode == Activity.RESULT_OK) {
                    if (Objects.requireNonNull(intent).getSerializableExtra(Constants.KEY_HEALTH_FACILITY) != null) {
                        selectedHealthFacility = (HealthFacility) intent.getSerializableExtra(Constants.KEY_HEALTH_FACILITY);
                        binding.etHealthFacility.setText(Objects.requireNonNull(selectedHealthFacility).getName());
                    } else if (Objects.requireNonNull(intent).getSerializableExtra(Constants.KEY_DEPARTMENT) != null) {
                        selectedDepartment = (Department) intent.getSerializableExtra(Constants.KEY_DEPARTMENT);
                        binding.etDepartment.setText(Objects.requireNonNull(selectedDepartment).getName());
                    }
                } else if (resultCode.equals(Constants.RESULT_ALL_MATCH)) {
                    String returnType = Objects.requireNonNull(intent).getStringExtra(Constants.RETURN_TYPE);
                    if (Objects.equals(returnType, Constants.TYPE_HOSPITAL)) {
                        Objects.requireNonNull(binding.etHealthFacility.getText()).clear();
                    } else if (Objects.equals(returnType, Constants.TYPE_DEPARTMENT)) {
                        Objects.requireNonNull(binding.etDepartment.getText()).clear();
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchScheduleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        eventHandling();
    }

    @SuppressLint("SetTextI18n")
    private void eventHandling() {
        binding.ivBack.setOnClickListener(v -> onBackPressed());
        binding.etHealthFacility.setOnClickListener(v -> launchSelectActivity(SelectHealthFacilityActivity.class));
        binding.etDepartment.setOnClickListener(v -> launchSelectActivity(SelectDepartmentActivity.class));
        binding.etFromDate.setOnClickListener(v -> {
            Calendar currentDate = Calendar.getInstance();
            MaterialDatePicker<Pair<Long, Long>> materialDatePicker = MaterialDatePicker.Builder.dateRangePicker()
                    .setTitleText(getString(R.string.please_select_date))
                    .setCalendarConstraints(new CalendarConstraints.Builder()
                            .setStart(currentDate.getTimeInMillis())
                            .setEnd(getTwoMonthLater(currentDate))
                            .build())
                    .setTheme(com.google.android.material.R.style.ThemeOverlay_MaterialComponents_MaterialCalendar)
                    .build();
            materialDatePicker.addOnPositiveButtonClickListener(selection -> {
                dateFrom = selection.first;
                dateTo = selection.second;
                binding.etFromDate.setText(getDateFormatted(dateFrom) + " - " + getDateFormatted(dateTo));
            });
            materialDatePicker.show(getSupportFragmentManager(), materialDatePicker.toString());
        });
    }

    private long getTwoMonthLater(Calendar calendar) {
        calendar.add(Calendar.MONTH, 2);
        return calendar.getTimeInMillis();
    }

    private String getDateFormatted(Long selectedDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd 'tháng' MM, yyyy", Locale.getDefault());
        return sdf.format(new Date(selectedDate));
    }

    private void launchSelectActivity(Class<?> activityClass) {
        Intent intent = new Intent(this, activityClass);
        intent.putExtra(Constants.KEY_SOURCE_ACTIVITY, "SearchSchedule");
        mStartForResult.launch(intent);
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