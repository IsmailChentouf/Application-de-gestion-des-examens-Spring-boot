package com.ensah.projectIT.models;

import java.util.List;
import java.util.ArrayList;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class ElementPedagogique {
		@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long IdEpd;
	private String nomEpd;
	@ManyToOne
	@JoinColumn(name="id_EnseignantCoordonne")
	private Enseignant enseignantcoordonne;
	@ManyToOne 
	@JoinColumn(name="id_niveau")
	private Niveau niveau;
	@ManyToOne
	@JoinColumn(name="id_TypeElement")
	private TypeElement typeElement;
	
	@OneToMany(mappedBy="elementP",cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private List<Examen> Examens =new ArrayList();
	public Long getIdEpd() {
		return IdEpd;
	}
	public void setIdEpd(Long idEpd) {
		IdEpd = idEpd;
	}
	public String getNomEpd() {
		return nomEpd;
	}
	public void setNomEpd(String nomEpd) {
		this.nomEpd = nomEpd;
	}
	
	
	public Niveau getNiveau() {
		return niveau;
	}
	public void setNiveau(Niveau niveau) {
		this.niveau = niveau;
	}
	public TypeElement getTypeElement() {
		return typeElement;
	}
	public void setTypeElement(TypeElement typeElement) {
		this.typeElement = typeElement;
	}
	
	
	public Enseignant getEnseignantcoordonne() {
		return enseignantcoordonne;
	}
	public void setEnseignantcoordonne(Enseignant enseignantcoordonne) {
		this.enseignantcoordonne = enseignantcoordonne;
	}
	public ElementPedagogique(Long idEpd, String nomEpd, Enseignant enseignantEnsiger, Enseignant enseignantcoordonne,
			Niveau niveau, TypeElement typeElement) {
		super();
		IdEpd = idEpd;
		this.nomEpd = nomEpd;
		this.enseignantcoordonne = enseignantcoordonne;
		this.niveau = niveau;
		this.typeElement = typeElement;
	}
	
	public ElementPedagogique () {}
	

	
}
