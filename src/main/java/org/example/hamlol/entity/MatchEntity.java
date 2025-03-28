package org.example.hamlol.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Getter
@Entity
@Table(name = "table_match")
public class MatchEntity {
    @Id
    @Column(name = "match_id", nullable = false)
    private String matchId;

    @Column(name = "gameDuration")
    private Long gameDuration;

    @Column(name = "gamemode")
    private String gamemode;

    @Column(name = "gameCreation")
    private Long gameCreation;

    @OneToMany(mappedBy = "match")
    private List<TeamEntity> teams;

    @OneToMany(mappedBy = "match")
    private List<PlayerEntity> players;

    public MatchEntity(String matchId, Long gameDuration, String gamemode, Long gameCreation) {
        this.matchId = matchId;
        this.gameDuration = gameDuration;
        this.gamemode = gamemode;
        this.gameCreation = gameCreation;
    }

    public MatchEntity() {

    }
}
