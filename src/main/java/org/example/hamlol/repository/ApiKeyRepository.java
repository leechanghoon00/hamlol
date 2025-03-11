package org.example.hamlol.repository;

import org.example.hamlol.entity.ApiKeyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApiKeyRepository extends JpaRepository<ApiKeyEntity, String> {
}
