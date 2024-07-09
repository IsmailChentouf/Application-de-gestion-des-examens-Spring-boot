package com.ensah.projectIT.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ensah.projectIT.models.Groupe;

public interface GroupeRepository extends JpaRepository<Groupe, Long> {
}
