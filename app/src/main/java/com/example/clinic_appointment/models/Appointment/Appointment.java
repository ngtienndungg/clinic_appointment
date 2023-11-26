package com.example.clinic_appointment.models.Appointment;

import com.example.clinic_appointment.models.Schedule.DetailSchedule;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Appointment implements Serializable {
    @SerializedName("_id")
    private String id;
    @SerializedName("patientID")
    private String patientId;
    @SerializedName("status")
    private String status;
    @SerializedName("scheduleID")
    private DetailSchedule schedule;
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

    public DetailSchedule getSchedule() {
        return schedule;
    }

    public void setSchedule(DetailSchedule schedule) {
        this.schedule = schedule;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
