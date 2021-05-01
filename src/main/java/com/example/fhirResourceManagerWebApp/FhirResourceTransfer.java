package com.example.fhirResourceManagerWebApp;

import com.example.service.TransferFhirPatientHandlerBean;

import javax.inject.Inject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("")
public class FhirResourceTransfer {

    public static final String TEST_URI = "http://hapi.fhir.org/baseR4/Patient";


    @Inject
    TransferFhirPatientHandlerBean transferFhirPatientHandlerBean;

    /*@GET
    public String doGet() {
        return "partita!";

    }*/

    @GET
    public String doGet() {
        System.out.println("FhirResourceTransfer - doGet");
        return "Welcome to Fhir Resource Transfer Servlet";
    }

    @GET
    @Path(value="{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getFhirResource(@PathParam("id") String id) {
        System.out.println("FhirResourceTransfer - patient id to find in the fhir test server: " + id);
        if(transferFhirPatientHandlerBean.transferFhirPatient(TEST_URI + id)) {
            return "Patient: " + id +" transferred on local DB";
        }
        return "Patient: " + id + " transfer failed";
    }

    @POST
    @Consumes(value="application/json")
    public String doPost(String requestBody) {
        System.out.println("FhirResourceTransfer - doPost");

        return transferFhirPatientHandlerBean.createPatientOnPublicFhirServer(requestBody);
    }

}