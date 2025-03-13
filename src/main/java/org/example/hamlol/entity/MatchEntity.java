package org.example.hamlol.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "table_match")
public class MatchEntity {
    @Id
    @Column(name = "match_id", nullable = false)
    private String matchId;

    @Column(name = "gameDuration")
    private Integer gameDuration;

    @Column(name = "gamemode")
    private String gamemode;

    @OneToMany(mappedBy = "match")
    private List<TeamEntity> teams;

    @OneToMany(mappedBy = "match")
    private List<PlayerEntity> players;

    public MatchEntity(String matchId, Integer gameDuration, String gamemode) {
        this.matchId = matchId;
        this.gameDuration = gameDuration;
        this.gamemode = gamemode;
    }

    public MatchEntity() {

    }
}
