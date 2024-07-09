package com.ensah.projectIT.repositories;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ensah.projectIT.models.Examen;

public interface ExamenRepository extends JpaRepository<Examen, Long> {

	 @Query("SELECT e FROM Examen e JOIN e.elementP ep WHERE ep.IdEpd = :elementPId AND e.date = :date AND e.heureDebut = :heureDebut")
	    List<Examen> findExamsByElementPIdAndDateAndTime(
	            @Param("elementPId") Long elementPId,
	            @Param("date") java.sql.Date date,
	            @Param("heureDebut") java.sql.Time heureDebut);


}
