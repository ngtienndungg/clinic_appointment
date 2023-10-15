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
import com.example.clinic_appointment.models.Hospital.Hospital;
import com.example.clinic_appointment.utilities.Constants;

import java.util.Objects;

public class FindDoctorActivity extends AppCompatActivity {
    private ActivityFindDoctorBinding binding;
    private final ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent intent = result.getData();
                    if (intent != null) {
                        if (intent.getSerializableExtra(Constants.KEY_SELECTED_ITEM) == null) {
                            Objects.requireNonNull(binding.etHospitalOrClinic.getText()).clear();
                        } else {
                            Hospital selectedHospital = (Hospital) intent.getSerializableExtra(Constants.KEY_SELECTED_ITEM);
                            if (selectedHospital != null) {
                                binding.etHospitalOrClinic.setText(selectedHospital.getName());
                            }
                        }
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
        binding.ivBack.setOnClickListener(v -> {
            onBackPressed();
        });
        binding.etHospitalOrClinic.setOnClickListener(v -> {
                    Intent intent = new Intent(this, SelectItemActivity.class);
                    intent.putExtra(Constants.KEY_ITEM_TYPE, Constants.TYPE_HOSPITAL);
                    mStartForResult.launch(intent);
                    overridePendingTransition(R.anim.slide_up, R.anim.slide_default);
                }
        );
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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