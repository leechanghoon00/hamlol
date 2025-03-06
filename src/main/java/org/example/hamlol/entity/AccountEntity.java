package org.example.hamlol.entity;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "lol_table")
public class AccountEntity {

    @Id
    @Column(nullable = false, unique = true)
    private String gameName;  // 롤 닉네임
    @Column(nullable = false)
    private String tagLine;   // 롤 태그
    @Column(nullable = false)
    private String puuid;     // Riot API에서 받은 puuid








}
