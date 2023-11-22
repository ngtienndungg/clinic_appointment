package com.example.clinic_appointment.utilities;

import androidx.annotation.NonNull;

import com.example.clinic_appointment.R;
import com.example.clinic_appointment.activities.SelectHealthFacilityActivity;
import com.example.clinic_appointment.models.Address.VietnamProvinceResponse;
import com.example.clinic_appointment.networking.clients.RetrofitClient;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Constants {
    public static final String KEY_ACCESS_TOKEN = "accessToken";
    public static final String KEY_REFRESH_TOKEN = "refreshToken";
    public static final String KEY_CURRENT_NAME = "currentName";
    public static final String KEY_CURRENT_PHONE_NUMBER = "currentPhoneNumber";
    public static final String KEY_CURRENT_EMAIL = "currentEmail";
    public static final String DOMAIN_LOCAL_HOST = "localhost";
    public static final String PATH_SLASH = "/";
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String HEADER_SET_COOKIE = "Set-Cookie";
    public static final String REGEX_SEMICOLON = ";";
    public static final String KEY_NEW_ACCESS_TOKEN = "newAccessToken";
    public static final String KEY_HEALTH_FACILITY = "healthFacility";
    public static final String KEY_DEPARTMENT = "department";
    public static final String KEY_DOCTOR = "doctor";
    public static final String KEY_DATE = "date";
    public static final String KEY_TIME = "time";
    public static final String KEY_SELECTED_ITEM = "selectedItem";
    public static final String KEY_ITEM_TYPE = "itemType";
    public static final String RETURN_TYPE = "returnType";
    public static final String TYPE_HOSPITAL = "typeHospital";
    public static final String TYPE_DEPARTMENT = "typeDepartment";
    public static final Integer RESULT_ALL_MATCH = 999;
    public static final String KEY_STATUS_CODE = "statusCode";
}
