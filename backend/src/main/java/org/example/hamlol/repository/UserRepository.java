package org.example.hamlol.repository;

import org.apache.catalina.User;
import org.example.hamlol.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUserName(String userName);
    Optional<UserEntity> findByEmail(String email);




}
