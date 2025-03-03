package org.example.hamlol.dto;

public class RewardConfigDto {
    private String rewardValue; // 보상 값
    private String rewardType; // 보상 타입
    private int maximumReward; // 최대 보상 수

    public String getRewardValue() {
        return rewardValue;
    }

    public void setRewardValue(String rewardValue) {
        this.rewardValue = rewardValue;
    }

    public String getRewardType() {
        return rewardType;
    }

    public void setRewardType(String rewardType) {
        this.rewardType = rewardType;
    }

    public int getMaximumReward() {
        return maximumReward;
    }

    public void setMaximumReward(int maximumReward) {
        this.maximumReward = maximumReward;
    }
}
