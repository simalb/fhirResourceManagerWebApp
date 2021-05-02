package com.example.persistence;

import com.example.persistence.entity.PatientEntity;

import javax.validation.constraints.NotNull;

public interface PatientPersistenceManager {

    void createPatient(PatientEntity patient);

    PatientEntity getPatientFromDbTableByUrl(@NotNull final String url);

    void printPatientEntityList();
}
