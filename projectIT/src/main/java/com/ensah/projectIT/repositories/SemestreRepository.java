package com.ensah.projectIT.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ensah.projectIT.models.Semestre;

public interface SemestreRepository extends JpaRepository<Semestre, Long> {
}
