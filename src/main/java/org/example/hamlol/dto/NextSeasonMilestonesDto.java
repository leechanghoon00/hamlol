package org.example.hamlol.dto;

import java.util.Map;

public class NextSeasonMilestonesDto {
    private Map<String, Integer> requireGradeCounts; // 등급 요구 횟수
    private int rewardMarks; // 보상 마크 수
    private boolean bonus; // 보너스 여부
    private RewardConfigDto rewardConfig; // 보상 구성 정보

    public RewardConfigDto getRewardConfig() {
        return rewardConfig;
    }

    public void setRewardConfig(RewardConfigDto rewardConfig) {
        this.rewardConfig = rewardConfig;
    }

    public boolean isBonus() {
        return bonus;
    }

    public void setBonus(boolean bonus) {
        this.bonus = bonus;
    }

    public int getRewardMarks() {
        return rewardMarks;
    }

    public void setRewardMarks(int rewardMarks) {
        this.rewardMarks = rewardMarks;
    }

    public Map<String, Integer> getRequireGradeCounts() {
        return requireGradeCounts;
    }

    public void setRequireGradeCounts(Map<String, Integer> requireGradeCounts) {
        this.requireGradeCounts = requireGradeCounts;
    }
}
