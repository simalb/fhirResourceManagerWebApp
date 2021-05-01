package com.example.service.utils.json;

import com.example.persistence.entity.PatientEntity;
import com.example.persistence.utils.ConverterUtility;
import com.example.service.utils.json.object.Patient;
import com.google.gson.Gson;

public class JsonManager {

    private JsonManager() {
    }

    public static Patient getPatientFromJsonObject(String json) {
        return new Gson().fromJson(json, Patient.class);
    }

    public static String getJsonObjectFromPatientEntity(PatientEntity patientEntity) {
        return new Gson().toJson(ConverterUtility.convertPatientEntityToPatientJsonObject(patientEntity));
    }
}
