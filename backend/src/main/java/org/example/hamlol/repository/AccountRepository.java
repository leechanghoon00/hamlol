package org.example.hamlol.repository;

import org.example.hamlol.entity.AccountEntity;
import org.example.hamlol.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, String> {

    AccountEntity findByUserEntity(UserEntity userEntity);


}
