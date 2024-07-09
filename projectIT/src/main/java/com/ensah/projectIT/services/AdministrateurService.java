package com.ensah.projectIT.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ensah.projectIT.models.Administrateur;
import com.ensah.projectIT.models.Surveillance;
import com.ensah.projectIT.repositories.AdministrateurRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AdministrateurService {

    @Autowired
    private AdministrateurRepository administrateurRepository;

    public List<Administrateur> getAllAdministrateurs() {
        return administrateurRepository.findAll();
    }

    public Optional<Administrateur> getAdministrateurById(Long id) {
        return administrateurRepository.findById(id);
    }

    @Transactional
    public Administrateur createAdministrateur(Administrateur administrateur) {
        return administrateurRepository.save(administrateur);
    }

    @Transactional
    public Administrateur updateAdministrateur(Administrateur administrateur) {
        if (administrateurRepository.existsById(administrateur.getId())) {
            return administrateurRepository.save(administrateur);
        }
        return null;
    }

    @Transactional
    public void deleteAdministrateur(Long id) {
        if (administrateurRepository.existsById(id)) {
            administrateurRepository.deleteById(id);
        }
    }

    @Transactional
    public void addSurveillance(Long adminId, Surveillance surveillance) {
        Optional<Administrateur> administrateurOpt = administrateurRepository.findById(adminId);
        if (administrateurOpt.isPresent()) {
            Administrateur administrateur = administrateurOpt.get();
            administrateur.getListeSurveillance().add(surveillance);
            surveillance.setAdministrateur(administrateur);
            administrateurRepository.save(administrateur);
        }
    }

    @Transactional
    public void removeSurveillance(Long adminId, Surveillance surveillance) {
        Optional<Administrateur> administrateurOpt = administrateurRepository.findById(adminId);
        if (administrateurOpt.isPresent()) {
            Administrateur administrateur = administrateurOpt.get();
            administrateur.getListeSurveillance().remove(surveillance);
            surveillance.setAdministrateur(null);
            administrateurRepository.save(administrateur);
        }
    }
}
