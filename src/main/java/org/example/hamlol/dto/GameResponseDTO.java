package org.example.hamlol.dto;

public record GameResponseDTO(

        Long gameDuration,
        String gamemode,
        Long gameCreation,
        String item0,
        String item1,
        String item2,
        String item3,
        String item4,
        String item5,
        String item6,
        Integer kills,
        Integer deaths,
        Integer assists,
        float kda,
        String summoner1Id,
        String summoner2Id,
        Boolean win,
        String primaryStyle1,
        String primaryStyle2,
        String primaryStyle3,
        String primaryStyle4,
        String subStyle1,
        String subStyle2



) {
}