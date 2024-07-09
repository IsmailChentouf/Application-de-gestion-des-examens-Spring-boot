package com.ensah.projectIT.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
public class Surveillance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSurveillance;

    @ManyToOne
    private Enseignant enseignantCoordonneSurveillance;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "enseignant_surveillance",
        joinColumns = @JoinColumn(name = "surveillance_id"),
        inverseJoinColumns = @JoinColumn(name = "enseignant_id")
    )
    private List<Enseignant> enseignantSurveillanceList = new ArrayList<>();

    @ManyToOne
    private Administrateur administrateur;

    @ManyToOne
    private Salle salle;

    @ManyToOne
    private Examen examen;

    // Getters and Setters

    public Long getIdSurveillance() {
        return idSurveillance;
    }

    public void setIdSurveillance(Long idSurveillance) {
        this.idSurveillance = idSurveillance;
    }

    public Enseignant getEnseignantCoordonneSurveillance() {
        return enseignantCoordonneSurveillance;
    }

    public void setEnseignantCoordonneSurveillance(Enseignant enseignantCoordonneSurveillance) {
        this.enseignantCoordonneSurveillance = enseignantCoordonneSurveillance;
    }

    public List<Enseignant> getEnseignantSurveillanceList() {
        return enseignantSurveillanceList;
    }

    public void setEnseignantSurveillanceList(List<Enseignant> enseignantSurveillanceList) {
        this.enseignantSurveillanceList = enseignantSurveillanceList;
    }

    public Administrateur getAdministrateur() {
        return administrateur;
    }

    public void setAdministrateur(Administrateur administrateur) {
        this.administrateur = administrateur;
    }

    public Salle getSalle() {
        return salle;
    }

    public void setSalle(Salle salle) {
        this.salle = salle;
    }

    public Examen getExamen() {
        return examen;
    }

    public void setExamen(Examen examen) {
        this.examen = examen;
    }
}
