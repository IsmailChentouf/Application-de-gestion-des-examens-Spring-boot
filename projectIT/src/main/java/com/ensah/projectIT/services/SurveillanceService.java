package com.ensah.projectIT.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ensah.projectIT.models.Surveillance;
import com.ensah.projectIT.repositories.SurveillanceRepository;

import jakarta.transaction.Transactional;

@Service
public class SurveillanceService {

    @Autowired
    private SurveillanceRepository surveillanceRepository;

    @Transactional
    public void deleteSurveillanceById(Long id) {
        // Vérifiez s'il existe des références dans enseignant_surveillance avant de supprimer
        Surveillance surveillance = surveillanceRepository.findById(id).orElse(null);
        
        if (surveillance != null) {
            // Supprimez d'abord toutes les références dans enseignant_surveillance
            surveillance.getEnseignantSurveillanceList().clear();
            surveillanceRepository.save(surveillance); // Sauvegardez pour appliquer les modifications

            // Supprimez la surveillance elle-même
            surveillanceRepository.deleteById(id);
        }
    }
}
