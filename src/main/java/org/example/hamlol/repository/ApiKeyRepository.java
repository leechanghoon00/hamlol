package org.example.hamlol.repository;

import org.example.hamlol.entity.ApiKeyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ApiKeyRepository extends JpaRepository<ApiKeyEntity, String> {
}
