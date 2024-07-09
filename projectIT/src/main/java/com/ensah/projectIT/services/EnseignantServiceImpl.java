package com.ensah.projectIT.services;

import com.ensah.projectIT.models.Departement;
import com.ensah.projectIT.models.Enseignant;
import com.ensah.projectIT.repositories.EnseignantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnseignantServiceImpl implements EnseignantService {

    private final EnseignantRepository enseignantRepository;

    @Autowired
    public EnseignantServiceImpl(EnseignantRepository enseignantRepository) {
        this.enseignantRepository = enseignantRepository;
    }

    @Override
    public List<Enseignant> getAllEnseignants() {
        return enseignantRepository.findAll();
    }

    @Override
    public Optional<Enseignant> getEnseignantById(Long id) {
        return enseignantRepository.findById(id);
    }

    @Override
    public Enseignant saveEnseignant(Enseignant enseignant) {
        return enseignantRepository.save(enseignant);
    }
    
    @Override
    public void updateEnseignant(Long id, Enseignant updatedEnseignant) {
        Enseignant enseignant = enseignantRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid enseignant Id:" + id));
        enseignant.setFirstName(updatedEnseignant.getFirstName());
        enseignant.setLastName(updatedEnseignant.getLastName());
        enseignant.setEmail(updatedEnseignant.getEmail());
        enseignant.setFiliere(updatedEnseignant.getFiliere());
        enseignant.setDepartement(updatedEnseignant.getDepartement());
        enseignantRepository.save(enseignant);
    }

    @Override
    public void deleteEnseignant(Long id) {
        enseignantRepository.deleteById(id);
    }

	@Override
	public List<Enseignant> getEnseignantsByDepartement(Departement departement) {
		return enseignantRepository.findByDepartement(departement);
		
	}



	
}
