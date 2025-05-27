package org.example.hamlol.repository;

import org.example.hamlol.entity.MatchEntity;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public interface MatchRepository extends JpaRepository<MatchEntity, String> ,GameRecordRepository{

}
