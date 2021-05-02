package com.example.persistence.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
@Table(name = "patients")
public class PatientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "internalId")
    //private UUID internalId;
    private int internalId;

    @Column(name = "url", nullable = false, unique = true)
    private String url;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creationDate")
    private Date creationDate;

    @Column(name = "family")
    private String family;

    @Column(name = "given")
    @ElementCollection
    private List<String> given = new ArrayList<>();

    @Column(name = "prefix")
    private String prefix = "";

    @Column(name = "suffix")
    private String suffix = "";

    @Column(name = "gender")
    private String gender;

    @Column(name = "birthDate")
    private String birthDate;
}
