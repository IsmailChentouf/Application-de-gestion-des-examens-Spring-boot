package com.ensah.projectIT.services;

import com.ensah.projectIT.models.Departement;
import com.ensah.projectIT.models.Niveau;
import com.ensah.projectIT.repositories.NiveauRepository;
import com.ensah.projectIT.services.NiveauService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NiveauServiceImpl implements NiveauService {

    @Autowired
    private NiveauRepository niveauRepository;

    @Override
    public Niveau getNiveauById(Long id) {
        Optional<Niveau> niveauOptional = niveauRepository.findById(id);
        return niveauOptional.orElse(null);
    }

    @Override
    public List<Niveau> getAllNiveaux() {
        return niveauRepository.findAll();
    }

    @Override
    public List<Niveau> getNiveauxByFiliereId(Long filiereId) {
        return niveauRepository.findByFiliereIdFiliere(filiereId); // Assuming you have a method in NiveauRepository
    }

    @Override
    public Niveau saveNiveau(Niveau niveau) {
        return niveauRepository.save(niveau);
    }

  


    @Override
    public void deleteNiveau(Long id) {
        niveauRepository.deleteById(id);
    }

	
}
