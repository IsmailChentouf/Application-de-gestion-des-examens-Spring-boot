package com.ensah.projectIT.repositories;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ensah.projectIT.models.Administrateur;

public interface AdministrateurRepository extends JpaRepository<Administrateur, Long> {
    //Optional<Administrateur> findByEmail(String email); // Corrected method name to match the field name
    @Query("SELECT a FROM Administrateur a WHERE a.id NOT IN (" +
            "SELECT c.id FROM Examen e JOIN e.controleursAbsence c " +
            "WHERE e.date = :date AND e.heureDebut = :heureDebut)")
    List<Administrateur> findAvailableCoordoneAbsence(@Param("date") Date date, @Param("heureDebut") Time heureDebut);
    Administrateur findByEmail(String email);
		
	
}
