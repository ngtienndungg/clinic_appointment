package com.example.clinic_appointment.activities;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.clinic_appointment.databinding.ActivitySelectTimeBinding;
import com.example.clinic_appointment.models.Schedule.Schedule;
import com.example.clinic_appointment.utilities.Constants;

public class SelectTimeActivity extends AppCompatActivity {
    private ActivitySelectTimeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectTimeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initiate();
    }

    private void initiate() {
        Schedule schedule = (Schedule) getIntent().getSerializableExtra(Constants.KEY_SELECTED_DATE);
        Log.d("IntentTest", schedule.getDate().toString());
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