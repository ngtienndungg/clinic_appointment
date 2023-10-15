package com.example.clinic_appointment.models.Department;

import com.example.clinic_appointment.utilities.Searchable;
import com.google.gson.annotations.SerializedName;

public class Department extends Searchable {
    @SerializedName("_id")
    private String id;
    @SerializedName("name")
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
