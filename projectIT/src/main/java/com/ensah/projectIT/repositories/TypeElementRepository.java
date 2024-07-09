package com.ensah.projectIT.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ensah.projectIT.models.TypeElement;

public interface TypeElementRepository extends JpaRepository<TypeElement, Long> {
}
