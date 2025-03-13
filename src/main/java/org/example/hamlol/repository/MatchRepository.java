package org.example.hamlol.repository;

import org.example.hamlol.entity.MatchEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<MatchEntity, String> {
}
