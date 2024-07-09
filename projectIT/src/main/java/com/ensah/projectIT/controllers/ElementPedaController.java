package com.ensah.projectIT.controllers;

import com.ensah.projectIT.models.Departement;
import com.ensah.projectIT.models.ElementPedagogique;
import com.ensah.projectIT.models.Enseignant;
import com.ensah.projectIT.models.Filiere;
import com.ensah.projectIT.models.Niveau;
import com.ensah.projectIT.models.TypeElement;
import com.ensah.projectIT.services.ElementPedagogiqueService;
import com.ensah.projectIT.services.EnseignantService;
import com.ensah.projectIT.services.FiliereService;
import com.ensah.projectIT.services.NiveauService;
import com.ensah.projectIT.services.TypeElementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/ElementP")
public class ElementPedaController {
	
	@Autowired

	private NiveauService  niveauService;
    @Autowired
    private ElementPedagogiqueService elementPedagogiqueService;

    @Autowired
    private EnseignantService enseignantService;
    
    @Autowired
    private FiliereService filiereService;

    @Autowired
    private TypeElementService typeElementService;

    
    @GetMapping("/elementPeda")
    public String elementPeda(Model model) {
        List<Filiere> listFilieres = filiereService.getAllFilieres();
        for(Filiere f: listFilieres) {
        	System.out.println(f.getNiveaux());
        }
        System.out.println();
        model.addAttribute("listFilieres", listFilieres);
        return "elementPeda";}
    
    @GetMapping("/affichageElementsPedagogiques/{idNiveau}")
    public String affichageElementsPedagogiques(@PathVariable Long idNiveau, Model model) {
        List<ElementPedagogique> elementsByNiveau = elementPedagogiqueService.getElementPedagogiqueByNiveauId(idNiveau);
        model.addAttribute("listElementsPedagogiques", elementsByNiveau);
        model.addAttribute("IDNiveau",idNiveau);
        return "affichageElementsPedagogiques";
    }

    @GetMapping("/add/{IDNiveau}")
    public String elementPedaForm(Model model,@PathVariable("IDNiveau") Long IDNiveau) {
    	Niveau niveau = niveauService.getNiveauById(IDNiveau);
    	Departement departement = niveau.getDepartement();
    	List<Enseignant> listCoordonnateurs = enseignantService.getEnseignantsByDepartement(departement);
        List<TypeElement> typeElements = typeElementService.getAllTypeElements();
        
        model.addAttribute("elementPedagogique", new ElementPedagogique());
        model.addAttribute("listCoordonnateurs", listCoordonnateurs);
        model.addAttribute("typeElements", typeElements);
        return "addElementPedagogique";
    }

    @PostMapping("/add/{IDNiveau}")
    public String addElementP(@ModelAttribute ElementPedagogique elementPedagogique,@PathVariable("IDNiveau") Long IDNiveau) {
       
		Niveau niveau = niveauService.getNiveauById(IDNiveau);
        elementPedagogique.setNiveau(niveau);
        elementPedagogiqueService.saveElementPedagogique(elementPedagogique);
        return "redirect:/ElementP/affichageElementsPedagogiques/"+IDNiveau;
    }

    @GetMapping("/update/{id}/{ID}")
    public String updateElementPForm(@PathVariable("id") Long id, Model model,@PathVariable("ID") Long IDNiveau) {
        Optional<ElementPedagogique> elementPedagogiqueOptional = elementPedagogiqueService.getElementPedagogiqueById(id);
        if (elementPedagogiqueOptional.isPresent()) {
            ElementPedagogique elementPedagogique = elementPedagogiqueOptional.get();
            List<Enseignant> listCoordonnateurs = enseignantService.getAllEnseignants();
            List<TypeElement> typeElements = typeElementService.getAllTypeElements();
            
            model.addAttribute("elementPedagogique", elementPedagogique);
            model.addAttribute("listCoordonnateurs", listCoordonnateurs);
            model.addAttribute("typeElements", typeElements);
            model.addAttribute("IDNiveau",IDNiveau);
            return "addElementPedagogique";
        } else {
            return "redirect:/ElementP/affichageElementsPedagogiques/"+IDNiveau;
        }
    }
    @GetMapping("/delete/{id}/{ID}")
    public String deleteElementP(@PathVariable("id") Long id,@PathVariable("ID") long ID) {
        elementPedagogiqueService.deleteElementPeda(id);
        return "redirect:/ElementP/affichageElementsPedagogiques/"+ID;
    }


}
