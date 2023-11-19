package com.example.clinic_appointment.models.Booking;

import com.example.clinic_appointment.models.Schedule.Schedule;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Booking implements Serializable {
    @SerializedName("patientID")
    private String patientId;
    @SerializedName("status")
    private String status;
    @SerializedName("scheduleID")
    private Schedule schedule;
    @SerializedName("time")
    private String appointmentTime;

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }
}
