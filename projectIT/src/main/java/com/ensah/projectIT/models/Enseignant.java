package com.ensah.projectIT.models;

import java.util.List;
import java.util.ArrayList;
import jakarta.persistence.*;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Enseignant extends Personnel {

    /*@OneToMany(mappedBy = "enseignantCoordonneSurveillance", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE })
    private List<Surveillance> listeSurveillanceCoordonne = new ArrayList<>();*/

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE })
    @JoinTable(
        name = "enseignant_surveillance", // Name of the join table
        joinColumns = @JoinColumn(name = "enseignant_id"),
        inverseJoinColumns = @JoinColumn(name = "surveillance_id")
    )
    private List<Surveillance> listeSurveillanceSurveille = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "id_filiere")
    private Filiere filiere;

    @ManyToOne
    @JoinColumn(name = "id_departement")
    private Departement departement;

    @ManyToOne 
    @JoinColumn(name = "id_groupe")
    private Groupe groupe;

    @OneToMany(mappedBy = "enseignantcoordonne", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private List<ElementPedagogique> ListeElementsEpdCoordonne = new ArrayList<>();

    /*public List<Surveillance> getListeSurveillanceCoordonne() {
        return listeSurveillanceCoordonne;
    }*/

    /*public void setListeSurveillanceCoordonne(List<Surveillance> listeSurveillanceCoordonne) {
        this.listeSurveillanceCoordonne = listeSurveillanceCoordonne;
    }*/

    public List<Surveillance> getListeSurveillanceSurveille() {
        return listeSurveillanceSurveille;
    }

    public void setListeSurveillanceSurveille(List<Surveillance> listeSurveillanceSurveille) {
        this.listeSurveillanceSurveille = listeSurveillanceSurveille;
    }

    public Filiere getFiliere() {
        return filiere;
    }

    public void setFiliere(Filiere filiere) {
        this.filiere = filiere;
    }

    public Departement getDepartement() {
        return departement;
    }

    public void setDepartement(Departement departement) {
        this.departement = departement;
    }

    public Groupe getGroupe() {
        return groupe;
    }

    public void setGroupe(Groupe groupe) {
        this.groupe = groupe;
    }
}
