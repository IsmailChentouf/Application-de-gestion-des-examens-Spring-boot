// GroupeController.java
package com.ensah.projectIT.controllers;

import com.ensah.projectIT.models.Groupe;
import com.ensah.projectIT.models.Enseignant;
import com.ensah.projectIT.services.GroupeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/groupes")
public class GroupeController {

    @Autowired
    private GroupeService groupeService;

    @GetMapping
    public String listGroupes(Model model) {
        List<Groupe> groupes = groupeService.getAllGroupes();
        model.addAttribute("groupes", groupes);
        return "groupes";
    }

    @GetMapping("/new")
    public String showGroupeForm(Model model) {
        model.addAttribute("groupe", new Groupe());
        return "groupe_form";
    }

    @PostMapping
    public String saveGroupe(@ModelAttribute("groupe") Groupe groupe) {
        groupeService.saveGroupe(groupe);
        return "redirect:/groupes";
    }

    @GetMapping("/{id}")
    public String showGroupe(@PathVariable Long id, Model model) {
        Groupe groupe = groupeService.getGroupeById(id).orElse(null);
        List<Enseignant> enseignants = groupeService.getEnseignantsWithoutGroup();        
        model.addAttribute("groupe", groupe);
        model.addAttribute("enseignants", enseignants);
        return "groupe_detail";
    }

    @PostMapping("/{id}/addEnseignant")
    public String addEnseignantToGroupe(@PathVariable Long id, @RequestParam Long enseignantId) {
        Enseignant enseignant = groupeService.getAllEnseignants().stream()
                                             .filter(e -> e.getId().equals(enseignantId))
                                             .findFirst()
                                             .orElseThrow(() -> new IllegalArgumentException("Invalid enseignant Id: " + enseignantId));
        groupeService.addEnseignantToGroupe(id, enseignant);
        return "redirect:/groupes/" + id;
    }

    @PostMapping("/{id}/delete")
    public String deleteGroupe(@PathVariable Long id) {
        // Get the groupe to be deleted
        Groupe groupeToDelete = groupeService.getGroupeById(id).orElse(null);
        
        // Check if the groupe exists
        if (groupeToDelete != null) {
            // Dissociate enseignants from the groupe
            for (Enseignant enseignant : groupeToDelete.getListeEnseignants()) {
                enseignant.setGroupe(null);
            }
            
            // Delete the groupe
            groupeService.deleteGroupe(id);
        }
        
        return "redirect:/groupes";
    }
}
