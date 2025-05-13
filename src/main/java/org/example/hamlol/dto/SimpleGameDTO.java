package org.example.hamlol.dto;

public record SimpleGameDTO(
        String matchId,
        Long gameDuration,  // 게임 진행 시간
        String gamemode,
        Long gameCreation , //게임 시작 시간
        String teamPosition, // 팀내 포지션
        Boolean win,
        String championName,
        String summoner1Id,
        String summoner2Id,
        Integer kills,
        Integer deaths,
        Integer assists,
        float kda,
        String item0,                             // 아이템 슬롯 0
        String item1,                             // 아이템 슬롯 1
        String item2,                             // 아이템 슬롯 2
        String item3,                             // 아이템 슬롯 3
        String item4,                             // 아이템 슬롯 4
        String item5,                             // 아이템 슬롯 5
        String item6,                             // 아이템 슬롯 6
        Integer totalMinionsKilled, //총 CS
        String riotIdGameName,
        String riotIdTagline,
        Integer championLevel
) {}