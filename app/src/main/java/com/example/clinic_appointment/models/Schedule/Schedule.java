package com.example.clinic_appointment.models.Schedule;

import com.example.clinic_appointment.models.AppointmentTime.AppointmentTime;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Schedule implements Serializable {
    @SerializedName("_id")
    private String scheduleId;
    @SerializedName("doctorID")
    private String doctorId;
    @SerializedName("cost")
    private long price;
    @SerializedName("timeType")
    private List<AppointmentTime> appointmentTimes;
}
