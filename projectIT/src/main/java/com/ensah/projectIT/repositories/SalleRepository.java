package com.ensah.projectIT.repositories;

import java.sql.Time;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ensah.projectIT.models.Salle;

public interface SalleRepository extends JpaRepository<Salle, Long> {

	@Query("SELECT s FROM Salle s WHERE s.IdSalle NOT IN " +
	           "(SELECT salle.IdSalle FROM Examen e JOIN e.salles salle WHERE e.date = :date AND e.heureDebut = :heureDebut)")
	    List<Salle> findSallesDisponibles(Date date, Time heureDebut);
}


