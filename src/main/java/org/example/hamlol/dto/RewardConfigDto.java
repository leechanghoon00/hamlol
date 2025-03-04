package org.example.hamlol.dto;

public record RewardConfigDto(
        String rewardValue, // 보상 값
        String rewardType, // 보상 타입
        int maximumReward // 최대 보상 수
) {
}
