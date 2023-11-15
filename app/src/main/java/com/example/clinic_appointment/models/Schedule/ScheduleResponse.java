package com.example.clinic_appointment.models.Schedule;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ScheduleResponse {
    @SerializedName("success")
    private boolean isSuccess;
    @SerializedName("data")
    private List<Schedule> schedules;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }
}
