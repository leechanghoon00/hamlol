package org.example.hamlol.dto;

public record MatchDTO(
        String matchId,         // 매치 ID (PK)
        Integer gameDuration,   // 게임 시간
        String gamemode         // 게임 모드
) {}
