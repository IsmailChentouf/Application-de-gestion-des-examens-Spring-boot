package com.ensah.projectIT.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ensah.projectIT.models.ElementPedagogique;

public interface ElementPedagogiqueRepository extends JpaRepository<ElementPedagogique, Long> {
	List<ElementPedagogique> findByNiveauIdNiveau(Long idNiveau);

}
