package com.ensah.projectIT.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ensah.projectIT.models.Session;

public interface SessionRepository extends JpaRepository<Session, Long> {
}
