package com.example.persistence;

import com.example.persistence.entity.PatientEntity;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;


@Singleton
@Startup
//@Local
public class PatientPersistenceManagerEJB implements PatientPersistenceManager {


    public PatientPersistenceManagerEJB() {
    }

    //@PersistenceContext
    //private static EntityManager em;

    //@PersistenceUnit(name="fhirResourceManagerWebApp")
    //EntityManagerFactory factory;

    private EntityManagerFactory factory;

    @PostConstruct
    public void init() {
        System.out.println("*** Starting  PatientTransferManager execution.");

        factory = Persistence.createEntityManagerFactory("fhirResourceManagerWebApp");
        EntityManager em = factory.createEntityManager();

        // read the existing entries and write to console
        printPatientEntityList(em);

        em.close();
    }

    @PreDestroy
    public void stop() {
        System.out.println("*** Ending  PatientTransferManager execution.");
    }

    public void createPatient(PatientEntity patient)  {
        EntityManager em = factory.createEntityManager();

        em.getTransaction().begin();
        em.persist(patient);
        em.getTransaction().commit();

        em.close();

        System.out.println("New patient created: " + patient.toString());
    }

    public PatientEntity getPatientFromDbTableByUrl(@NotNull final String url) {
        System.out.println("Requested patient from DB: " + url);

        EntityManager em = factory.createEntityManager();

        TypedQuery<PatientEntity> q = em.createQuery("select p from PatientEntity p where p.url=:url", PatientEntity.class);
        q.setParameter("url", url);

        try {
            PatientEntity patientEntity = q.getSingleResult();
            System.out.println("Requested patient found: " + patientEntity.toString());
            em.close();
            return patientEntity;
        } catch (final NoResultException nre) {
            System.out.println("ERROR TO MANAGE - Requested patient not found DB: " + url);
            em.close();
            return null;
        }
    }

    public void printPatientEntityList() {
        EntityManager em = factory.createEntityManager();
        printPatientEntityList(em);
        em.close();
    }

    private void printPatientEntityList(EntityManager em) {
        Query q = em.createQuery("select p from PatientEntity p");

        System.out.println("*** Print patients in local DB:");
        List<PatientEntity> patientList = q.getResultList();
        for (PatientEntity patientEntity : patientList) {
            System.out.println("*** " + patientEntity.toString());
        }
        System.out.println("*** Patients present in table: " + patientList.size());

    }
}
