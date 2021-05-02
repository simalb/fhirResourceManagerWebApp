package com.example.fhirResourceManagerWebApp;

import com.example.service.TransferFhirPatientHandler;

import javax.inject.Inject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("")
public class FhirResourceTransfer {

    public static final String TEST_URI = "http://hapi.fhir.org/baseR4/Patient";

    @Inject
    TransferFhirPatientHandler transferFhirPatientHandlerEJB;

    @GET
    public String doGet() {
        System.out.println("FhirResourceTransfer - doGet");
        return "Welcome to Fhir Resource Transfer Servlet";
    }

    //JAVA TEST - 1 READ AND TRANSFER A FHIR RESOURCE
    @POST
    @Path(value="{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String transferFhirPatientToLocalDbById(@PathParam("id") String id) {
        System.out.println("FhirResourceTransfer - transferFhirPatientToLocalDbById - patient id to find in the fhir test server: " + id);
        if(transferFhirPatientHandlerEJB.transferFhirPatient(TEST_URI + "/" + id)) {
            return "Patient: " + id +" transferred on local DB";
        }
        return "Patient: " + id + " transfer failed";
    }

    //JAVA TEST - 2 GET THE COPY
    @GET
    @Path(value="{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getTransferredPatientFromDbById(@PathParam("id") String id) {
        System.out.println("FhirResourceTransfer - getTransferredPatientFromDbById - get patient from DB: " + id);
        return transferFhirPatientHandlerEJB.transferredPatient(TEST_URI + "/" + id);
    }

    //JAVA TEST - 4 FHIR TEST SERVER
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String createPatientOnPublicFhirServerByJson(String requestBody) {
        System.out.println("FhirResourceTransfer - createPatientOnPublicFhirServerByJson");

        return transferFhirPatientHandlerEJB.createPatientOnPublicFhirServer(requestBody);
    }




}