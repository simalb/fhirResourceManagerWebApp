package com.example.fhirResourceManagerWebApp;

import java.io.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.ws.rs.*;

@Path("")
public class FhirResourceTransfer {

    @GET
    public String doGet() {
        return "partita!";

    }

}