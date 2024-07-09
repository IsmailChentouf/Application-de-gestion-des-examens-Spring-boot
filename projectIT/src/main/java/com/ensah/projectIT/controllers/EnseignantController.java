package com.ensah.projectIT.controllers;

import com.ensah.projectIT.models.Enseignant;
import com.ensah.projectIT.services.DepartementService;
import com.ensah.projectIT.services.EnseignantService;
import com.ensah.projectIT.services.FiliereService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class EnseignantController {

    @Autowired
    private EnseignantService enseignantService;

    @Autowired
    private FiliereService filiereService;

    @Autowired
    private DepartementService departementService;

    @GetMapping("/Enseignants")
    public String Enseignants(Model model) {
        model.addAttribute("listEnseignants", enseignantService.getAllEnseignants());
        return "Enseignants";
    }

    @GetMapping("/add")
    public String showAddEnseignantForm(Model model) {
        Enseignant enseignant = new Enseignant();
        model.addAttribute("enseignant", enseignant);
        model.addAttribute("listFilieres", filiereService.getAllFilieres());
        model.addAttribute("listDepartements", departementService.getAllDepartements());
        return "addEnseignant";
    }

    @PostMapping("/add")
    public String addEnseignant(@ModelAttribute("enseignant") Enseignant enseignant) {
        enseignantService.saveEnseignant(enseignant);
        return "redirect:/Enseignants";
    }
 
    @GetMapping("/update/{id}")
    public String showUpdateEnseignantForm(@PathVariable("id") Long id, Model model) {
        Optional<Enseignant> optionalEnseignant = enseignantService.getEnseignantById(id);
        if (optionalEnseignant.isPresent()) {
            model.addAttribute("enseignant", optionalEnseignant.get());
            model.addAttribute("listFilieres", filiereService.getAllFilieres());
            model.addAttribute("listDepartements", departementService.getAllDepartements());
            return "addEnseignant";
        } else {
            return "not_found";
        }
    }

    @PostMapping("/update/{id}")
    public String updateEnseignant(@PathVariable("id") Long id, @ModelAttribute("enseignant") Enseignant updatedEnseignant) {
        enseignantService.updateEnseignant(id, updatedEnseignant);
        return "redirect:/Enseignants";
    } 

    @GetMapping("/delete/{id}")
    public String deleteEnseignant(@PathVariable("id") Long id) {
        enseignantService.deleteEnseignant(id);
        return "redirect:/Enseignants";
    }
}
