package org.example.hamlol.repository;

import org.example.hamlol.entity.ChampEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ChampRepository extends JpaRepository<ChampEntity, String> {

}
