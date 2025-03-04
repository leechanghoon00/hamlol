package org.example.hamlol.dto;

import java.util.List;

public record ChampionMasteryDto(
        String puuid,
        long championPointsUntilNextLevel, // 다음 레벨에 필요한 포인트
        boolean chestGranted, // 현재 시즌에서 챔피언에 대해 체스트가 부여되었는지 여부
        long championId, // 챔프 ID
        long lastPlayTime, // 마지막 플레이 시간
        int championLevel, // 챔피언 레벨
        int championPoints, // 총 챔피언 포인트
        long championPointsSinceLastLevel, // 현재 레벨 이후 획득한 챔피언 포인트
        int markRequiredForNextLevel, // 다음 레벨로 가기 위해 필요한 마크 수
        int championSeasonMilestone, // 시즌 마일스톤
        NextSeasonMilestonesDto nextSeasonMilestone, // 다음 시즌 마일스톤
        int tokensEarned, // 현재 챔피언 레벨에서 획득한 토큰 수
        List<String> milestoneGrades // 마일스톤 등급 리스트
) {
}
