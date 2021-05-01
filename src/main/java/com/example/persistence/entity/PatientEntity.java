package com.example.persistence.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
@Table(name = "patients")
public class PatientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //private UUID internalId;
    private int internalId;

    @Transient
    //@Column(name = "url", nullable = false, unique = true)
    private String url;

    @Transient
    //@Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    private String family;

    @Transient
    private List<String> given = null;

    @Transient
    private String prefix = "";
    @Transient
    private String suffix = "";

    private String gender;

    @Transient
    private String birthDate;

}
