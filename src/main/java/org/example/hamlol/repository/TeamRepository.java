package org.example.hamlol.repository;

import org.example.hamlol.entity.TeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<TeamEntity,String > {
}
