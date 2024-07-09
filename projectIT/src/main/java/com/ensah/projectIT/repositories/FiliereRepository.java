package com.ensah.projectIT.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ensah.projectIT.models.Filiere;

public interface FiliereRepository extends JpaRepository<Filiere, Long> {

}
