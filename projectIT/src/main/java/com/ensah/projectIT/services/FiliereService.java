package com.ensah.projectIT.services;

import com.ensah.projectIT.models.Filiere;
import com.ensah.projectIT.repositories.FiliereRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FiliereService {

    @Autowired
    private FiliereRepository filiereRepository;

    public List<Filiere> getAllFilieres() {
        return filiereRepository.findAll();
    }

    public Filiere getFiliereById(Long id) {
        Optional<Filiere> filiereOptional = filiereRepository.findById(id);
        return filiereOptional.orElse(null);
    }
  

}
