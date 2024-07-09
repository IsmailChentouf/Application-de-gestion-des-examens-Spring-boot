package com.ensah.projectIT.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ensah.projectIT.models.Surveillance;

public interface SurveillanceRepository extends JpaRepository<Surveillance, Long> {
}
