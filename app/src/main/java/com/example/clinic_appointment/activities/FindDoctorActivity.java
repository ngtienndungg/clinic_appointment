package com.example.clinic_appointment.activities;

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

import com.example.clinic_appointment.R;
import com.example.clinic_appointment.databinding.ActivityFindDoctorBinding;
import com.example.clinic_appointment.models.Department.Department;
import com.example.clinic_appointment.models.Hospital.HealthFacility;
import com.example.clinic_appointment.utilities.Constants;

import java.util.Objects;

public class FindDoctorActivity extends AppCompatActivity {
    private ActivityFindDoctorBinding binding;
    private final ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                Intent intent = result.getData();
                Integer resultCode = result.getResultCode();
                if (resultCode == Activity.RESULT_OK) {
                    String returnType = Objects.requireNonNull(intent).getStringExtra(Constants.RETURN_TYPE);
                    if (Objects.equals(returnType, Constants.TYPE_HOSPITAL)) {
                        HealthFacility selectedHealthFacility = (HealthFacility) intent.getSerializableExtra(Constants.KEY_SELECTED_ITEM);
                        binding.etHospitalOrClinic.setText(Objects.requireNonNull(selectedHealthFacility).getName());
                    } else if (Objects.equals(returnType, Constants.TYPE_DEPARTMENT)) {
                        Department selectedDepartment = (Department) intent.getSerializableExtra(Constants.KEY_SELECTED_ITEM);
                        binding.etDepartment.setText(Objects.requireNonNull(selectedDepartment).getName());
                    }
                } else if (resultCode.equals(Constants.RESULT_ALL_MATCH)) {
                    String returnType = Objects.requireNonNull(intent).getStringExtra(Constants.RETURN_TYPE);
                    if (Objects.equals(returnType, Constants.TYPE_HOSPITAL)) {
                        Objects.requireNonNull(binding.etHospitalOrClinic.getText()).clear();
                    } else if (Objects.equals(returnType, Constants.TYPE_DEPARTMENT)) {
                        Objects.requireNonNull(binding.etDepartment.getText()).clear();
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFindDoctorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        eventHandling();
    }

    private void eventHandling() {
        binding.ivBack.setOnClickListener(v -> onBackPressed());
        binding.etHospitalOrClinic.setOnClickListener(v -> openSelectItem(Constants.TYPE_HOSPITAL));
        binding.etDepartment.setOnClickListener(v -> openSelectItem(Constants.TYPE_DEPARTMENT));
    }

    private void openSelectItem(String itemType) {
        Intent intent = new Intent(this, SelectItemActivity.class);
        intent.putExtra(Constants.KEY_ITEM_TYPE, itemType);
        mStartForResult.launch(intent);
        overridePendingTransition(R.anim.slide_up, R.anim.slide_default);
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