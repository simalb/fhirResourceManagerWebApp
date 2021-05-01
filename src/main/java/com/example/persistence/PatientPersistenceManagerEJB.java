package com.example.persistence;

import com.example.persistence.entity.PatientEntity;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Arrays;


//TODO - understand wy it does not work
@Singleton
//@Startup
//@Local
public class PatientPersistenceManagerEJB implements PatientPersistenceManager{

    //@PersistenceContext
    //private static EntityManager em;

    //@PersistenceUnit
    //private EntityManagerFactory emf;

     @PostConstruct
    public void init() {
        System.out.println("*** Starting  PatientTransferManager execution.");
        //EntityManagerFactory factory = Persistence.createEntityManagerFactory("PatientTransferService");
        //em = factory.createEntityManager();

        //h2 native query to show tables and columns
        //runNativeQuery("SHOW TABLES");
        //runNativeQuery("SHOW COLUMNS from Patients");
    }

    @PreDestroy
    public void stop() {
        //em.close();

        System.out.println("*** Ending  PatientTransferManager execution.");
    }

    public void createPatient(PatientEntity patient)  {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("PatientTransferService");
        EntityManager em = factory.createEntityManager();

        em.getTransaction().begin();
        em.persist(patient);
        em.getTransaction().commit();

        em.close();

        System.out.println("New patient created: " + patient.toString());
    }

    public PatientEntity getPatientFromDbTableByUrl(@NotNull final String url) {
        System.out.println("Requested patient from DB: " + url);

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("PatientTransferService");
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
    public void printTabels()  {

        //h2 native query to show tables and columns
        runNativeQuery("SHOW TABLES");
        runNativeQuery("SHOW COLUMNS from Patients");
    }

    private void runNativeQuery(String s) {
        System.out.println("--------\n" + s);

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("PatientTransferService");
        EntityManager em = factory.createEntityManager();

        Query query = em.createNativeQuery(s);
        for (Object o : query.getResultList()) {
            System.out.println(Arrays.toString((Object[]) o));
        }
        em.close();
    }
}