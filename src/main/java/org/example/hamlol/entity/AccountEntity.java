package org.example.hamlol.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Table(name = "lol_table")
public class AccountEntity {

    @Id
    private String gameName;  // 롤 닉네임

    private String tagLine;   // 롤 태그
    private String puuid;     // Riot API에서 받은 puuid








}
