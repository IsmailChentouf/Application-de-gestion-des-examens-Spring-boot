package com.ensah.projectIT.models;

import java.util.Date;
import java.util.List;
import java.sql.Time;
import java.util.ArrayList;

import jakarta.persistence.*;

@Entity
public class Examen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idExamen;

    @ManyToOne
    @JoinColumn(name = "id_typeExamen")
    private TypeExamen typeExamen;

    @ManyToOne
    @JoinColumn(name = "id_semestre")
    private Semestre semestre;

    @ManyToOne
    @JoinColumn(name = "id_session")
    private Session session;

    @ManyToOne
    @JoinColumn(name = "id_elementP")
    private ElementPedagogique elementP;

    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(name = "heureDebut", columnDefinition = "TIME")
    private Time heureDebut;

    private int dureePrevue; // en minutes
    private int dureeReelle; // en minutes

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "examen_salles",
               joinColumns = @JoinColumn(name = "id_examen"),
               inverseJoinColumns = @JoinColumn(name = "id_salle"))
    private List<Salle> salles = new ArrayList<>();

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "examen_surveillants",
               joinColumns = @JoinColumn(name = "id_examen"),
               inverseJoinColumns = @JoinColumn(name = "id_enseignant"))
    private List<Enseignant> surveillants = new ArrayList<>();

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "examen_controleurs_absence",
               joinColumns = @JoinColumn(name = "id_examen"),
               inverseJoinColumns = @JoinColumn(name = "id_administrateur"))
    private List<Administrateur> controleursAbsence = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "id_coordinateur")
    private Enseignant coordinateur;

    private String anneeUniversitaire;

    @Lob
    @Column(name = "fichierEpreuve")
    private byte[] fichierEpreuve;

    @Lob
    @Column(name = "fichierPV")
    private byte[] fichierPV;

    @Column(columnDefinition = "TEXT")
    private String rapportTextuel = "Rien à signaler";

    @OneToMany(mappedBy = "examen", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Surveillance> listeSurveillance = new ArrayList<>();


    // Getters and Setters

    public Long getIdExamen() {
        return idExamen;
    }

    public void setIdExamen(Long idExamen) {
        this.idExamen = idExamen;
    }

    public TypeExamen getTypeExamen() {
        return typeExamen;
    }

    public void setTypeExamen(TypeExamen typeExamen) {
        this.typeExamen = typeExamen;
    }

    public Semestre getSemestre() {
        return semestre;
    }

    public void setSemestre(Semestre semestre) {
        this.semestre = semestre;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public ElementPedagogique getElementP() {
        return elementP;
    }

    public void setElementP(ElementPedagogique elementP) {
        this.elementP = elementP;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(Time heureDebut) {
        this.heureDebut = heureDebut;
    }

    public int getDureePrevue() {
        return dureePrevue;
    }

    public void setDureePrevue(int dureePrevue) {
        this.dureePrevue = dureePrevue;
    }

    public int getDureeReelle() {
        return dureeReelle;
    }

    public void setDureeReelle(int dureeReelle) {
        this.dureeReelle = dureeReelle;
    }

    public List<Salle> getSalles() {
        return salles;
    }

    public void setSalles(List<Salle> salles) {
        this.salles = salles;
    }

    public List<Enseignant> getSurveillants() {
        return surveillants;
    }

    public void setSurveillants(List<Enseignant> surveillants) {
        this.surveillants = surveillants;
    }

    public List<Administrateur> getControleursAbsence() {
        return controleursAbsence;
    }

    public void setControleursAbsence(List<Administrateur> controleursAbsence) {
        this.controleursAbsence = controleursAbsence;
    }

    public Enseignant getCoordinateur() {
        return coordinateur;
    }

    public void setCoordinateur(Enseignant coordinateur) {
        this.coordinateur = coordinateur;
    }

    public String getAnneeUniversitaire() {
        return anneeUniversitaire;
    }

    public void setAnneeUniversitaire(String anneeUniversitaire) {
        this.anneeUniversitaire = anneeUniversitaire;
    }

    public byte[] getFichierEpreuve() {
        return fichierEpreuve;
    }

    public void setFichierEpreuve(byte[] fichierEpreuve) {
        this.fichierEpreuve = fichierEpreuve;
    }

    public byte[] getFichierPV() {
        return fichierPV;
    }

    public void setFichierPV(byte[] fichierPV) {
        this.fichierPV = fichierPV;
    }

    public String getRapportTextuel() {
        return rapportTextuel;
    }

    public void setRapportTextuel(String rapportTextuel) {
        this.rapportTextuel = rapportTextuel;
    }

    public List<Surveillance> getListeSurveillance() {
        return listeSurveillance;
    }

    public void setListeSurveillance(List<Surveillance> listeSurveillance) {
        this.listeSurveillance = listeSurveillance;
    }
}
