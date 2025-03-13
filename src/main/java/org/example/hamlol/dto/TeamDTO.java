package org.example.hamlol.dto;

public record TeamDTO(
        String matchId,         // 매치 ID (외래키)
        Integer teamId,         // 팀 ID (예: 1 또는 2)
        Boolean win,            // 승리 여부
        String bans,            // 밴 정보 (JSON 형식의 문자열)
        Integer baronKills,     // 바론 처치 횟수
        Integer championKills,  // 챔피언 처치 횟수
        Integer dragonKills,    // 드래곤 처치 횟수
        Integer hordeKills,     // 유충 처치 횟수
        Integer inhibitorKills, // 억제기 파괴 횟수
        Integer riftHeraldKills,// 전령 처치 횟수
        Integer towerKills      // 타워 파괴 횟수
) {}
