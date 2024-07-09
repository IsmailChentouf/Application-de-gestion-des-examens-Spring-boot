package com.ensah.projectIT.services;

import com.ensah.projectIT.models.ElementPedagogique;
import com.ensah.projectIT.repositories.ElementPedagogiqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ElementPedagogiqueService {

    private final ElementPedagogiqueRepository elementPedaRepository;

    @Autowired
    public ElementPedagogiqueService(ElementPedagogiqueRepository elementPedaRepository) {
        this.elementPedaRepository = elementPedaRepository;
    }

    public List<ElementPedagogique> getAllElementPedagogique() {
        return elementPedaRepository.findAll();
    }

    public Optional<ElementPedagogique> getElementPedagogiqueById(Long id) {
        return elementPedaRepository.findById(id);
    }

    public ElementPedagogique saveElementPedagogique(ElementPedagogique elementPeda) {
        return elementPedaRepository.save(elementPeda);
    }

    public void updateElementPeda(Long id, ElementPedagogique updatedElementPedagogique) {
        ElementPedagogique elementPeda = elementPedaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid element pedagogique Id:" + id));
        elementPeda.setNomEpd(updatedElementPedagogique.getNomEpd());
        elementPeda.setEnseignantcoordonne(updatedElementPedagogique.getEnseignantcoordonne());
        elementPeda.setNiveau(updatedElementPedagogique.getNiveau());
        elementPeda.setTypeElement(updatedElementPedagogique.getTypeElement());
        elementPedaRepository.save(elementPeda);
    }

    public void deleteElementPeda(Long id) {
        elementPedaRepository.deleteById(id);
    }
    
    public List<ElementPedagogique> getElementPedagogiqueByNiveauId(Long idNiveau) {
        return elementPedaRepository.findByNiveauIdNiveau(idNiveau);
    }
 
}
