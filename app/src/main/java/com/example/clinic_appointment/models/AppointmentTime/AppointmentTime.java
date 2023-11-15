package com.example.clinic_appointment.models.AppointmentTime;

import com.google.gson.annotations.SerializedName;

public class AppointmentTime {
    @SerializedName("time")
    private String timeNumber;
    @SerializedName("full")
    private boolean isFull;
    @SerializedName("maxNumber")
    private int maxAvailability;

    public String getTimeNumber() {
        return timeNumber;
    }

    public void setTimeNumber(String timeNumber) {
        this.timeNumber = timeNumber;
    }

    public boolean isFull() {
        return isFull;
    }

    public void setFull(boolean full) {
        isFull = full;
    }

    public int getMaxAvailability() {
        return maxAvailability;
    }

    public void setMaxAvailability(int maxAvailability) {
        this.maxAvailability = maxAvailability;
    }
}
