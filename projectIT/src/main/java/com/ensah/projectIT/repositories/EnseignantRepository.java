package com.ensah.projectIT.repositories;


import java.sql.Date;
import java.sql.Time;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ensah.projectIT.models.Departement;
import com.ensah.projectIT.models.Enseignant;

public interface EnseignantRepository extends JpaRepository<Enseignant, Long> {

	List<Enseignant> findByDepartement(Departement departement);
	   @Query("SELECT e FROM Enseignant e WHERE e.groupe IS NULL")
	    List<Enseignant> findEnseignantsWithoutGroup();
	   @Query("SELECT e FROM Enseignant e WHERE e.id NOT IN " +
	           "(SELECT s.id FROM Examen ex JOIN ex.surveillants s WHERE ex.date = :date AND ex.heureDebut = :heureDebut) " +
	           "AND e.id NOT IN " +
	           "(SELECT ex.coordinateur.id FROM Examen ex WHERE ex.date = :date AND ex.heureDebut = :heureDebut)")
	    List<Enseignant> findAvailableSurveillants(@Param("date") Date date, @Param("heureDebut") Time heureDebut);
	  @Query("SELECT COUNT(e) = 0 FROM Enseignant e " +
           "JOIN e.listeSurveillanceSurveille s " +
           "WHERE e.id = :enseignantId AND s.examen.date = :date " +
           "AND s.examen.heureDebut = :heureDebut")
    boolean isAvailableSurveillant(@Param("enseignantId") Long enseignantId,
                                   @Param("date") Date date,
                                   @Param("heureDebut") Time heureDebut);

	}
