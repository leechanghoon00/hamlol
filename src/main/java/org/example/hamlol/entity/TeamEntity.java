package org.example.hamlol.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "table_team",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"match_id", "team_id"})})
public class TeamEntity {


    @Id
    @Column(name = "match_id", nullable = false)
    private String matchId;

    @Column(name = "team_id", nullable = false)
    private Integer teamId;

    @Column(name = "win")
    private Boolean win;

    @Column(name = "bans")
    private String bans; // JSON 문자열

    @Column(name = "baron_kills")
    private Integer baronKills;

    @Column(name = "champion_kills")
    private Integer championKills;

    @Column(name = "dragon_kills")
    private Integer dragonKills;

    @Column(name = "horde_kills")
    private Integer hordeKills;

    @Column(name = "inhibitor_kills")
    private Integer inhibitorKills;

    @Column(name = "riftHerald_kills")
    private Integer riftHeraldKills;

    @Column(name = "tower_kills")
    private Integer towerKills;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id",insertable = false, updatable = false)
    private MatchEntity match;


    @OneToMany(mappedBy = "team")
    private List<PlayerEntity> players;

    public TeamEntity(String matchId, Integer teamId, Boolean win, String bans,
                      Integer baronKills, Integer championKills, Integer dragonKills,
                      Integer hordeKills, Integer inhibitorKills, Integer riftHeraldKills,
                      Integer towerKills, MatchEntity match) {
        this.matchId = matchId;
        this.teamId = teamId;
        this.win = win;
        this.bans = bans;
        this.baronKills = baronKills;
        this.championKills = championKills;
        this.dragonKills = dragonKills;
        this.hordeKills = hordeKills;
        this.inhibitorKills = inhibitorKills;
        this.riftHeraldKills = riftHeraldKills;
        this.towerKills = towerKills;
        this.match = match;
    }

    public TeamEntity() {

    }
}
