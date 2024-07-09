// GroupeService.java
package com.ensah.projectIT.services;

import com.ensah.projectIT.models.Groupe;
import com.ensah.projectIT.models.Enseignant;
import com.ensah.projectIT.repositories.GroupeRepository;
import com.ensah.projectIT.repositories.EnseignantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupeService {

    @Autowired
    private GroupeRepository groupeRepository;

    @Autowired
    private EnseignantRepository enseignantRepository;

    public List<Groupe> getAllGroupes() {
        return groupeRepository.findAll();
    }

    public Optional<Groupe> getGroupeById(Long id) {
        return groupeRepository.findById(id);
    }

    public void saveGroupe(Groupe groupe) {
        groupeRepository.save(groupe);
    }

    public void addEnseignantToGroupe(Long groupeId, Enseignant enseignant) {
        Groupe groupe = groupeRepository.findById(groupeId).orElseThrow(() -> new IllegalArgumentException("Invalid groupe Id: " + groupeId));
        enseignant.setGroupe(groupe);
        enseignantRepository.save(enseignant);
    }

    public void deleteGroupe(Long id) {
        groupeRepository.deleteById(id);
    }

    public List<Enseignant> getAllEnseignants() {
        return enseignantRepository.findAll();
    }
    public List<Enseignant> getEnseignantsWithoutGroup() {
        return enseignantRepository.findEnseignantsWithoutGroup();
    }
}
