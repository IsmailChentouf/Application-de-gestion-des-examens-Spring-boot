package com.ensah.projectIT.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ensah.projectIT.models.Departement;

public interface DepartementRepository extends JpaRepository<Departement, Long> {
}
