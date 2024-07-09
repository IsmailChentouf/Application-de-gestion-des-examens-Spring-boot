package com.ensah.projectIT.services;

import java.util.List;
import java.util.Optional;

import com.ensah.projectIT.models.Departement;
import com.ensah.projectIT.models.Enseignant;


public interface EnseignantService {

	void updateEnseignant(Long id, Enseignant updatedEnseignant);
	List<Enseignant> getAllEnseignants();

	Optional<Enseignant> getEnseignantById(Long id);

	Enseignant saveEnseignant(Enseignant enseignant);

	void deleteEnseignant(Long id);
	List<Enseignant> getEnseignantsByDepartement(Departement departement);
	

}
