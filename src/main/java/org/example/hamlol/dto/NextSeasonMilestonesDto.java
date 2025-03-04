package org.example.hamlol.dto;

import java.util.Map;

public record NextSeasonMilestonesDto(
        Map<String, Integer> requireGradeCounts, // 등급 요구 횟수
        int rewardMarks, // 보상 마크 수
        boolean bonus, // 보너스 여부
        RewardConfigDto rewardConfig // 보상 구성 정보
) {
}
