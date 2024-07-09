package com.ensah.projectIT.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ensah.projectIT.models.Examen;
import com.ensah.projectIT.repositories.ExamenRepository;

@Service
public class ExamenService {

    private final ExamenRepository examenRepository;

    @Autowired
    public ExamenService(ExamenRepository examenRepository) {
        this.examenRepository = examenRepository;
    }

    @Transactional
    public void deleteExamen(Long id) {
        Examen examen = examenRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("L'examen avec l'ID " + id + " n'existe pas."));
        
        examenRepository.delete(examen);
    }
}
