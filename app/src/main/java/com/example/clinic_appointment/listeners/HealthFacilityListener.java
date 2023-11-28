package com.example.clinic_appointment.listeners;

import com.example.clinic_appointment.models.HealthFacility.HealthFacility;

import java.util.List;

public interface HealthFacilityListener {
    void onClick(HealthFacility healthFacility);
    void onProvinceSelect();
}
