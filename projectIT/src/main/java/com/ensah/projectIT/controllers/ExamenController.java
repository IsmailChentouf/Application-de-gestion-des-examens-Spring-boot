package com.ensah.projectIT.controllers;

import com.ensah.projectIT.models.*;

import com.ensah.projectIT.repositories.*;
import com.ensah.projectIT.services.AdministrateurService;
import com.ensah.projectIT.services.ExamenService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Date;
import java.sql.Time;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ExamenController {
	@Autowired
	private GroupeRepository groupeRepository;
	@Autowired
	private AdministrateurRepository administrateurRepository;
    @Autowired
    private EnseignantRepository enseignantRepository;

    @Autowired
    private SalleRepository salleRepository;

    @Autowired
    private SurveillanceRepository surveillanceRepository;

    @Autowired
    private ExamenRepository examenRepository;

    @Autowired
    private TypeExamenRepository typeExamenRepository;

    @Autowired
    private SemestreRepository semestreRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private ElementPedagogiqueRepository elementPedagogiqueRepository;

    @Autowired
    private AdministrateurService adminService;
    @Autowired
    private ExamenService examenService;
    @GetMapping("/examen/add")
    public String showAddExamenForm(Model model) {
        List<TypeExamen> types = typeExamenRepository.findAll();
        List<Semestre> semestres = semestreRepository.findAll();
        List<Session> sessions = sessionRepository.findAll();
        List<ElementPedagogique> elements = elementPedagogiqueRepository.findAll();

        model.addAttribute("types", types);
        model.addAttribute("semestres", semestres);
        model.addAttribute("sessions", sessions);
        model.addAttribute("elements", elements);
        return "addExamen";
    }
    
    @PostMapping("/examen/add")
    public String addExamen(@RequestParam("typeExamenId") Long typeExamenId,
                            @RequestParam("semestreId") Long semestreId,
                            @RequestParam("sessionId") Long sessionId,
                            @RequestParam("elementPId") Long elementPId,
                            @RequestParam("date") Date date,
                            @RequestParam("heureDebut") String heureDebut,
                            @RequestParam("anneeUniversitaire") String anneeUniversitaire,
                            Model model) {

        boolean error0 = false;
        String error1 = "";
        String error2 = "";
        String error3 = "";

        Time parsedHeureDebut = Time.valueOf(heureDebut + ":00");
        List<Examen> existingExams = examenRepository.findExamsByElementPIdAndDateAndTime(elementPId, date, parsedHeureDebut);

        java.util.Date utilDate = new java.util.Date(date.getTime());
        Instant instant = utilDate.toInstant();
        ZoneId defaultZoneId = ZoneId.systemDefault();
        LocalDate localDate = instant.atZone(defaultZoneId).toLocalDate();
        LocalDateTime examDateTime = LocalDateTime.of(localDate, LocalTime.parse(heureDebut));
        LocalDateTime currentDateTime = LocalDateTime.now();

        if (examDateTime.isBefore(currentDateTime)) {
            error0 = true;
            error1 = "The exam date must be in the future.";
        }

        LocalTime startTime = LocalTime.parse(heureDebut);
        LocalTime earliestTime = LocalTime.of(8, 0);
        LocalTime latestTime = LocalTime.of(18, 0);

        if (startTime.isBefore(earliestTime) || startTime.isAfter(latestTime)) {
            error0 = true;
            error2 = "The start time must be between 08:00 and 18:00.";
        }

        if (hasOverlap(existingExams, parsedHeureDebut)) {
            error0 = true;
            error3 = "There is already an exam scheduled at this time for the selected level.";
        }

        if (error0) {
            List<TypeExamen> types = typeExamenRepository.findAll();
            List<Semestre> semestres = semestreRepository.findAll();
            List<Session> sessions = sessionRepository.findAll();
            List<ElementPedagogique> elements = elementPedagogiqueRepository.findAll();

            model.addAttribute("types", types);
            model.addAttribute("semestres", semestres);
            model.addAttribute("sessions", sessions);
            model.addAttribute("elements", elements);

            // Add error messages to the model
            model.addAttribute("error1", error1);
            model.addAttribute("error2", error2);
            model.addAttribute("error3", error3);

            // Return to the form view
            return "addExamen";
        }

        model.addAttribute("semestreId", semestreId);
        model.addAttribute("sessionId", sessionId);
        model.addAttribute("elementPId", elementPId);
        model.addAttribute("date", date);
        model.addAttribute("heureDebut", heureDebut);
        model.addAttribute("anneeUniversitaire", anneeUniversitaire);
        model.addAttribute("typeExamenId", typeExamenId);

        List<Salle> sallesDisponibles = salleRepository.findSallesDisponibles(date, parsedHeureDebut);
        model.addAttribute("salles", sallesDisponibles);
        return "selectSalles";
    }

    private boolean hasOverlap(List<Examen> existingExams, Time newStartTime) {
        // Check if any of the existing exams overlap with the new exam's start time
        return existingExams.stream()
                .anyMatch(exam -> {
                    Time existingStartTime = exam.getHeureDebut();
                    int existingDuration = exam.getDureePrevue();
                    Time existingEndTime = new Time(existingStartTime.getTime() + existingDuration * 60000); // Convert duration to milliseconds

                    // Determine if there is an overlap
                    return (newStartTime.after(existingStartTime) && newStartTime.before(existingEndTime))
                            || (newStartTime.equals(existingStartTime));
                });
    }

    @PostMapping("/examen/createExam")
    public String processCreateExam(
    		 @RequestParam("salleIds") List<Long> salleIds,
             @RequestParam("nombreSurveillants") List<Integer> nombreSurveillants,
             @RequestParam("surveillantSelection") String surveillantSelection,
             @RequestParam("semestreId") Long semestreId,
             @RequestParam("sessionId") Long sessionId,
             @RequestParam("elementPId") Long elementPId,
             @RequestParam("date") Date date,
             @RequestParam("heureDebut") String heureDebut,
             @RequestParam("anneeUniversitaire") String anneeUniversitaire,
             @RequestParam("typeExamenId") Long typeExamenId)  {
    	
    	Examen examen = new Examen();
        TypeExamen typeExamen = typeExamenRepository.findById(typeExamenId).orElseThrow();
        Semestre semestre = semestreRepository.findById(semestreId).orElseThrow();
        Session session = sessionRepository.findById(sessionId).orElseThrow();
        ElementPedagogique elementP = elementPedagogiqueRepository.findById(elementPId).orElseThrow();

        examen.setTypeExamen(typeExamen);
        examen.setSemestre(semestre);
        examen.setSession(session);
        examen.setElementP(elementP);
        examen.setDate(date);

        // Parse the time string to java.sql.Time
        Time parsedHeureDebut = Time.valueOf(heureDebut + ":00");
        examen.setHeureDebut(parsedHeureDebut);

        examen.setAnneeUniversitaire(anneeUniversitaire);
        if ("module".equals(elementP.getTypeElement().getTitre())) {
            examen.setDureePrevue(120); // 2 hours in minutes
        } else {
            examen.setDureePrevue(90); // 1.5 hours in minutes
        }
        examen.setCoordinateur(elementP.getEnseignantcoordonne());

        examenRepository.save(examen);
        List<Administrateur> availableCoordoneAbsence = administrateurRepository.findAvailableCoordoneAbsence(date, parsedHeureDebut);
        List<Enseignant> availableSurveillants=null;
        List<Groupe> availableGroups = groupeRepository.findAll();
        
        if(("aleatoire").equals(surveillantSelection)) {
        	
        	 availableSurveillants = enseignantRepository.findAvailableSurveillants(date, parsedHeureDebut);
            Collections.shuffle(availableSurveillants); // Shuffle the list for randomness
        	
        }else {
        	
        	for (Groupe groupe : availableGroups) {
                List<Enseignant> groupSurveillants = groupe.getListeEnseignants().stream()
                    .filter(enseignant -> enseignantRepository.isAvailableSurveillant(enseignant.getId(), date, parsedHeureDebut))
                    .collect(Collectors.toList());

                if (groupSurveillants.size() >= nombreSurveillants.stream().mapToInt(Integer::intValue).sum()) {
                    availableSurveillants = groupSurveillants;
                    break;
                }
            }
        	
        }
        
        int k=0;
        // Associer les salles et les surveillants
        for (int i = 0; i < salleIds.size(); i++) {
            Salle salle = salleRepository.findById(salleIds.get(i)).orElseThrow();
            examen.getSalles().add(salle);
            Surveillance surveillance=new Surveillance();
            Administrateur CoordoonateurAbcense=availableCoordoneAbsence.get(k);
            examen.getControleursAbsence().add(CoordoonateurAbcense);
            surveillance.setExamen(examen);
            surveillance.setAdministrateur(CoordoonateurAbcense);
            surveillance.setSalle(salle);
            // Nombre de surveillants
            int nombre = nombreSurveillants.get(i);
            for (int j = 0; j < nombre; j++) {
                Enseignant surveillant = availableSurveillants.get(k);
                k++;
                examen.getSurveillants().add(surveillant);
                surveillant.getListeSurveillanceSurveille().add(surveillance);
                }
            surveillanceRepository.save(surveillance);
        }

        

        // Rediriger vers la liste des examens ou une page de confirmation
        return "redirect:/examen/list";
    }
    
    @PostMapping("/examen/printSallesAndSurveillants")
    public String printSallesAndSurveillants(
            @RequestParam("salleIds") List<Long> salleIds,
            @RequestParam("nombreSurveillants") List<Integer> nombreSurveillants,
            @RequestParam("surveillantSelection") String surveillantSelection,
            Model model) {

        // Afficher les informations dans la console
        for (int i = 0; i < salleIds.size(); i++) {
            Long salleId = salleIds.get(i);
            Integer nombreSurveillantsForSalle = nombreSurveillants.get(i);
            System.out.println("Salle ID: " + salleId + ", Nombre de surveillants: " + nombreSurveillantsForSalle);
        }

        // Rediriger vers la même page ou une autre page après affichage
        return "redirect:/examen/selectSalles";
    }

    

   /* public String addExamen(@RequestParam("typeExamenId") Long typeExamenId,
                            @RequestParam("semestreId") Long semestreId,
                            @RequestParam("sessionId") Long sessionId,
                            @RequestParam("elementPId") Long elementPId,
                            @RequestParam("date") Date date,
                            @RequestParam("heureDebut") String heureDebut,
                            @RequestParam("anneeUniversitaire") String anneeUniversitaire,
                            @RequestParam("nombreDeSurveillants") int nombreDeSurveillants) {

        Examen examen = new Examen();
        List<Salle> listesalles = salleRepository.findAll();
        TypeExamen typeExamen = typeExamenRepository.findById(typeExamenId).orElseThrow();
        Semestre semestre = semestreRepository.findById(semestreId).orElseThrow();
        Session session = sessionRepository.findById(sessionId).orElseThrow();
        ElementPedagogique elementP = elementPedagogiqueRepository.findById(elementPId).orElseThrow();

        examen.setTypeExamen(typeExamen);
        examen.setSemestre(semestre);
        examen.setSession(session);
        examen.setElementP(elementP);
        examen.setDate(date);

        // Parse the time string to java.sql.Time
        Time parsedHeureDebut = Time.valueOf(heureDebut + ":00");
        examen.setHeureDebut(parsedHeureDebut);

        examen.setAnneeUniversitaire(anneeUniversitaire);
        if ("module".equals(elementP.getTypeElement().getTitre())) {
            examen.setDureePrevue(120); // 2 hours in minutes
        } else {
            examen.setDureePrevue(90); // 1.5 hours in minutes
        }
        examen.setCoordinateur(elementP.getEnseignantcoordonne());

        examenRepository.save(examen);

        for (Salle salle : listesalles) {
            boolean salleAffecte = false;

            for (Surveillance surveillance1 : salle.getListeSurveillances()) {
                if (surveillance1.getExamen().getHeureDebut().equals(parsedHeureDebut)) {
                    salleAffecte = true;
                    break;
                }
            }
            if (!salleAffecte && !examen.getSalles().contains(salle)) {
                Surveillance surveillance = new Surveillance();
                surveillance.setSalle(salle);
                surveillance.setExamen(examen);
                surveillanceRepository.save(surveillance);
                examen.getSalles().add(salle);
                break;
            }
        }

        List<Administrateur> listeAdmins = adminService.getAllAdministrateurs();

        for (Administrateur admin : listeAdmins) {
            boolean adminAffecte = false;

            for (Surveillance surveillance1 : admin.getListeSurveillance()) {
                if (surveillance1.getExamen().getHeureDebut().equals(parsedHeureDebut)) {
                    adminAffecte = true;
                    break;
                }
            }

            if (!adminAffecte && !examen.getControleursAbsence().contains(admin)) {
                Surveillance surveillance = new Surveillance();
                surveillance.setAdministrateur(admin);
                surveillance.setExamen(examen);
                surveillanceRepository.save(surveillance);
                examen.getControleursAbsence().add(admin);
                break;
            }
        }

        List<Enseignant> availableSurveillants = enseignantRepository.findAvailableSurveillants(date, parsedHeureDebut);
        Collections.shuffle(availableSurveillants); // Shuffle the list for randomness

        for (int i = 0; i < Math.min(nombreDeSurveillants, availableSurveillants.size()); i++) {
            Enseignant surveillant = availableSurveillants.get(i);
            Surveillance surveillance = new Surveillance();
            surveillance.setEnseignantCoordonneSurveillance(surveillant);
            surveillance.setExamen(examen);
            surveillanceRepository.save(surveillance);
            surveillant.getListeSurveillanceSurveille().add(surveillance);
            examen.getSurveillants().add(surveillant);
        }


        return "redirect:/examen/list";
    }*/

    @GetMapping("/examen/list")
    public String listExamens(Model model) {
        List<Examen> examens = examenRepository.findAll();
        model.addAttribute("examens", examens);
        return "listExamen";
    }

    @GetMapping("/examen/details/{id}")
    public String showExamenDetails(@PathVariable("id") Long id, Model model) {
        Examen examen = examenRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid exam Id:" + id));
        // Chargement des relations nécessaires si elles ne sont pas chargées automatiquement
        examen.getListeSurveillance().size(); // Force le chargement des surveillances
        model.addAttribute("examen", examen);
        return "examenDetails";
    }
    @PostMapping("/examen/delete/{id}")
    public String deleteExamen(@PathVariable("id") Long id) {
        examenService.deleteExamen(id);
        return "redirect:/examen/list"; // Redirect to the list of exams after deletion
    }

}
