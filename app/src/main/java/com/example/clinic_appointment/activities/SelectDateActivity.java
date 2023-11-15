package com.example.clinic_appointment.activities;

import static com.kizitonwose.calendar.core.ExtensionsKt.daysOfWeek;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.clinic_appointment.R;
import com.example.clinic_appointment.databinding.ActivitySelectDateBinding;
import com.example.clinic_appointment.utilities.CalendarView.DayViewContainer;
import com.kizitonwose.calendar.core.CalendarDay;
import com.kizitonwose.calendar.core.DayPosition;
import com.kizitonwose.calendar.view.MonthDayBinder;

import java.time.DayOfWeek;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class SelectDateActivity extends AppCompatActivity {
    private ActivitySelectDateBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectDateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initiate();
        eventHandling();
    }

    void initiate() {
        binding.cvCalendar.setDayBinder(new MonthDayBinder<DayViewContainer>() {
            @NonNull
            @Override
            public DayViewContainer create(@NonNull View view) {
                return new DayViewContainer(view);
            }

            @Override
            public void bind(@NonNull DayViewContainer dayViewContainer, CalendarDay calendarDay) {
                if (calendarDay.getPosition() == DayPosition.MonthDate) {
                    dayViewContainer.getView().setVisibility(View.VISIBLE);
                    dayViewContainer.textView.setText(String.valueOf(calendarDay.getDate().getDayOfMonth()));
                } else {
                    dayViewContainer.getView().setVisibility(View.INVISIBLE);
                }
            }
        });

        YearMonth currentMonth = YearMonth.now();
        YearMonth endMonth = currentMonth.plusMonths(12);
        List<DayOfWeek> daysOfWeek = daysOfWeek();

        ViewGroup titlesContainer = findViewById(R.id.titlesContainer);
        for (int index = 0; index < titlesContainer.getChildCount(); index++) {
            View child = titlesContainer.getChildAt(index);
            if (child instanceof TextView) {
                TextView textView = (TextView) child;
                DayOfWeek dayOfWeek = DayOfWeek.valueOf(String.valueOf(daysOfWeek.get(index)));
                String title = dayOfWeek.getDisplayName(TextStyle.NARROW_STANDALONE, Locale.getDefault());
                textView.setText(title);
            }
        }
        binding.cvCalendar.setup(currentMonth, endMonth, daysOfWeek.get(0));
        binding.cvCalendar.scrollToMonth(currentMonth);

    }

    private void eventHandling() {
        binding.cvCalendar.setMonthScrollListener(calendarMonth -> {
            String currentMonthYear = calendarMonth.getYearMonth().getMonth().getDisplayName(TextStyle.FULL_STANDALONE, Locale.getDefault()) + " - " + calendarMonth.getYearMonth().getYear();
            binding.tvMonth.setText(currentMonthYear);
            return null;
        });
        binding.ivNextMonth.setOnClickListener(v -> binding.cvCalendar.smoothScrollToMonth(Objects.requireNonNull(binding.cvCalendar.findFirstVisibleMonth()).getYearMonth().plusMonths(1)));
        binding.ivPreviousMonth.setOnClickListener(v -> binding.cvCalendar.smoothScrollToMonth(Objects.requireNonNull(binding.cvCalendar.findFirstVisibleMonth()).getYearMonth().minusMonths(1)));
    }
}