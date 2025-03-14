package org.example.hamlol.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "table_player",
// 데이터 무결성을 유지핳기위해 사용
uniqueConstraints = {@UniqueConstraint(columnNames = {"match_id","riotIdGameName"})})

public class PlayerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "player_id")
    private Long playerId;

    @Column(name = "match_id", nullable = false)
    private String matchId;

    @Column(name = "riotIdGameName", nullable = false)
    private String riotIdGameName;

    @Column(name = "team_type", nullable = false)
    private String teamType;


    @Column(name = "champion_id")
    private String championId;

    @Column(name = "damage_dealt_to_buildings")
    private Integer damageDealtToBuildings;

    @Column(name = "gold_earned")
    private Integer goldEarned;

    @Column(name = "individual_position")
    private String individualPosition;

    @Column(name = "item0")
    private String item0;

    @Column(name = "item1")
    private String item1;

    @Column(name = "item2")
    private String item2;

    @Column(name = "item3")
    private String item3;

    @Column(name = "item4")
    private String item4;

    @Column(name = "item5")
    private String item5;

    @Column(name = "item6")
    private String item6;

    @Column(name = "kills")
    private Integer kills;

    @Column(name = "deaths")
    private Integer deaths;

    @Column(name = "assists")
    private Integer assists;

    @Column(name = "kda")
    private float kda;

    @Column(name = "riot_id_tagline")
    private String riotIdTagline;

    @Column(name = "summoner1_id")
    private String summoner1Id;

    @Column(name = "summoner2_id")
    private String summoner2Id;

    @Column(name = "team_position")
    private String teamPosition;

    @Column(name = "total_damage_dealt_to_champions")
    private Integer totalDamageDealtToChampions;

    @Column(name = "total_damage_taken")
    private Integer totalDamageTaken;

    @Column(name = "total_heals_on_teammates")
    private Integer totalHealsOnTeammates;

    @Column(name = "total_minions_killed")
    private Integer totalMinionsKilled;

    @Column(name = "vision_score")
    private Integer visionScore;

    @Column(name = "vision_wards_bought_in_game")
    private Integer visionWardsBoughtInGame;

    @Column(name = "wards_placed")
    private Integer wardsPlaced;

    @Column(name = "wards_killed")
    private Integer wardsKilled;

    @Column(name = "win")
    private Boolean win;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_type", referencedColumnName = "team_type", insertable = false, updatable = false)
    private TeamEntity team;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id", insertable = false, updatable = false)
    private MatchEntity match;



    public PlayerEntity(String matchId, String teamType, String riotIdGameName, String championId,
                        Integer damageDealtToBuildings, Integer goldEarned, String individualPosition,
                        String item0, String item1, String item2, String item3, String item4,
                        String item5, String item6, Integer kills, Integer deaths, Integer assists,
                        float kda, String riotIdTagline, String summoner1Id, String summoner2Id,
                        String teamPosition, Integer totalDamageDealtToChampions, Integer totalDamageTaken,
                        Integer totalHealsOnTeammates, Integer totalMinionsKilled, Integer visionScore,
                        Integer visionWardsBoughtInGame, Integer wardsPlaced, Integer wardsKilled,
                        Boolean win, MatchEntity match) {
        this.matchId = matchId;
        this.teamType = teamType;
        this.riotIdGameName = riotIdGameName;
        this.championId = championId;
        this.damageDealtToBuildings = damageDealtToBuildings;
        this.goldEarned = goldEarned;
        this.individualPosition = individualPosition;
        this.item0 = item0;
        this.item1 = item1;
        this.item2 = item2;
        this.item3 = item3;
        this.item4 = item4;
        this.item5 = item5;
        this.item6 = item6;
        this.kills = kills;
        this.deaths = deaths;
        this.assists = assists;
        this.kda = kda;
        this.riotIdTagline = riotIdTagline;
        this.summoner1Id = summoner1Id;
        this.summoner2Id = summoner2Id;
        this.teamPosition = teamPosition;
        this.totalDamageDealtToChampions = totalDamageDealtToChampions;
        this.totalDamageTaken = totalDamageTaken;
        this.totalHealsOnTeammates = totalHealsOnTeammates;
        this.totalMinionsKilled = totalMinionsKilled;
        this.visionScore = visionScore;
        this.visionWardsBoughtInGame = visionWardsBoughtInGame;
        this.wardsPlaced = wardsPlaced;
        this.wardsKilled = wardsKilled;
        this.win = win;
        this.match = match;
    }

    public PlayerEntity() {

    }
}