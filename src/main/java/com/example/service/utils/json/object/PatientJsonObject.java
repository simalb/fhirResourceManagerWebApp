package com.example.service.utils.json.object;

import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class PatientJsonObject {

    //private UUID internalId;
    private int internalId;

    private String url;

    //private Date creationDate;

    private String family;
    //private List<String> given = null;
    private String prefix = "";
    private String suffix = "";
    private String gender;
    private String birthDate;
}
