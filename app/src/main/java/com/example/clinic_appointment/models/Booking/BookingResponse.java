package com.example.clinic_appointment.models.Booking;

import com.google.gson.annotations.SerializedName;

public class BookingResponse {
    @SerializedName("success")
    private boolean isSuccess;
    @SerializedName("data")
    private Booking booking;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }
}
