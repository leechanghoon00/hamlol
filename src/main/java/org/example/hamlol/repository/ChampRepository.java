package org.example.hamlol.repository;

import org.example.hamlol.entity.ChampEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChampRepository extends JpaRepository<ChampEntity, String> {

}
