package org.example.hamlol.entity;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "table_lol")
public class AccountEntity {

    @Id
    @Column(nullable = false, unique = true)
    private String gameName;  // 롤 닉네임
    @Column(nullable = false)
    private String tagLine;   // 롤 태그
    @Column(nullable = false)
    private String puuid;     // Riot API에서 받은 puuid

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;
    @Column(name = "user_name")
    private String userName;



    public AccountEntity(String puuid, String gameName, String tagLine, UserEntity userEntity,String userName){
        this.puuid = puuid;
        this.gameName = gameName;
        this.tagLine = tagLine;
        this.userEntity = userEntity;
        this.userName = userName;
    }

    public AccountEntity() {

    }

}
