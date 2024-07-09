package com.ensah.projectIT.services;

import com.ensah.projectIT.models.Departement;
import com.ensah.projectIT.models.Niveau;

import java.util.List;
import java.util.Optional;

public interface NiveauService {

    Niveau getNiveauById(Long id);

    List<Niveau> getAllNiveaux();

    List<Niveau> getNiveauxByFiliereId(Long filiereId);

    Niveau saveNiveau(Niveau niveau);

    void deleteNiveau(Long id);
    

}
