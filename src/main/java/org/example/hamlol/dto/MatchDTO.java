package org.example.hamlol.dto;

import java.time.LocalDateTime;

public record MatchDTO(
        String matchId,         // 매치 ID (PK)
        double gameDuration,   // 게임 시간 (게임 진행 시간)
        String gamemode,        // 게임 모드
        LocalDateTime gameCreation    // 게임 시작 시간 (밀리초-> KST로 변환 필요)
) {}
