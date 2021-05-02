package com.example.service;

import com.example.persistence.PatientPersistenceManager;
import com.example.persistence.PatientPersistenceManagerEJB;
import com.example.persistence.entity.PatientEntity;
import com.example.persistence.utils.ConverterUtility;
import com.example.service.http.connection.HttpOperationHandler;
import com.example.service.http.connection.ResultHandler;
import com.example.service.utils.exceptions.HttpURLConnectionFailException;
import com.example.service.utils.json.JsonManager;
import com.example.service.utils.json.object.Patient;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
@Startup
public class TransferFhirPatientHandlerBean implements TransferFhirPatientHandler {

    @Inject
    PatientPersistenceManager patientPersistenceManagerEJB;

    @PostConstruct
    public void init() {
        System.out.println("*** Starting fhirResourceManager execution.");

    }

    @PreDestroy
    public void stop() {
        System.out.println("*** Ending  fhirResourceManager execution.");
    }

    @Override
    //JAVA TEST - 1 READ AND TRANSFER A FHIR RESOURCE
    public boolean transferFhirPatient(String fhirUrl) {

        HttpOperationHandler httpOperationHandler = new HttpOperationHandler();

        try {
            //Read out patient from fhir server
            ResultHandler resultHandler = httpOperationHandler.get(fhirUrl);

            if(!resultHandler.isResultOk()) {
                System.out.println("getCompletePatientEntity - failure");
                return false;
            }

            Patient patient = JsonManager.getPatientFromJsonObject(resultHandler.getResultMessage());

            System.out.println("getCompletePatientEntity - patient: " + patient.toString());
            PatientEntity patientEntity = ConverterUtility.getCompletePatientEntity(patient, fhirUrl);

            //Create a copy in sql table
            // TODO- STILL NOT WORKING
            System.out.println("\n *** STILL NOT WORKING - persist patient on DB *** \n");
            patientPersistenceManagerEJB.createPatient(patientEntity);

            return true;

        } catch (HttpURLConnectionFailException e) {
            System.out.println("TO BE MANAGED - Error occurred: " + e.getMessage());
            return false;
        }
    }

    @Override
    //JAVA TEST - 2 GET THE COPY
    public String transferredPatient(String fhirUrl) {

        //Convert to Json Object
        String jsonObjectPatient = JsonManager.getJsonObjectFromPatientEntity(patientPersistenceManagerEJB.getPatientFromDbTableByUrl(fhirUrl));
        System.out.println("transferedPatient: " + jsonObjectPatient);

        return jsonObjectPatient;
    }

    @Override
    //JAVA TEST - 4 FHIR TEST SERVER
    public String createPatientOnPublicFhirServer(String fhirPatientJson) {
        HttpOperationHandler httpOperationHandler = new HttpOperationHandler();

        try {
            //Create patient to public fhir test server
            String resultMessage = httpOperationHandler.post(PUBLIC_TEST_SERVER_URI, fhirPatientJson).getResultMessage();
            System.out.println("createPatientOnPublicFhirServer: " + resultMessage);
            return resultMessage;

        } catch (HttpURLConnectionFailException e) {
            System.out.println("TO BE MANAGED - Error occurred: " + e.getMessage());
            return "{}";
        }
    }

}
