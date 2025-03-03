package org.example.hamlol.dto;

import java.util.List;

public class ChampionMasteryDto {

    private String puuid;
    private long championPointsUntilNextLevel; // 다음 레벨에 필요한 포인트
    private boolean chestGranted; // 현재 시즌에서 챔피언에 대해 체스트가 부여되었는지 여부
    private long championId; // 챔프 ID
    private long lastPlayTime; // 마지막 플레이 시간 
    private int championLevel; // 챔피언 레벨
    private int championPoints; // 총 챔피언 포인트
    private long championPointsSinceLastLevel; // 현재 레벨 이후 획득한 챔피언 포인트
    private int markRequiredForNextLevel; // 다음 레벨로 가기 위해 필요한 마크 수
    private int championSeasonMilestone; // 시즌 마일스톤
    private NextSeasonMilestonesDto nextSeasonMilestone; // 다음 시즌 마일스톤
    private int tokensEarned; // 현재 챔피언 레벨에서 획득한 토큰 수
    private List<String> milestoneGrades; // 마일스톤 등급 리스트

    public String getPuuid() {
        return puuid;
    }

    public void setPuuid(String puuid) {
        this.puuid = puuid;
    }

    public long getChampionPointsUntilNextLevel() {
        return championPointsUntilNextLevel;
    }

    public void setChampionPointsUntilNextLevel(long championPointsUntilNextLevel) {
        this.championPointsUntilNextLevel = championPointsUntilNextLevel;
    }

    public boolean isChestGranted() {
        return chestGranted;
    }

    public void setChestGranted(boolean chestGranted) {
        this.chestGranted = chestGranted;
    }

    public long getChampionId() {
        return championId;
    }

    public void setChampionId(long championId) {
        this.championId = championId;
    }

    public long getLastPlayTime() {
        return lastPlayTime;
    }

    public void setLastPlayTime(long lastPlayTime) {
        this.lastPlayTime = lastPlayTime;
    }

    public int getChampionLevel() {
        return championLevel;
    }

    public void setChampionLevel(int championLevel) {
        this.championLevel = championLevel;
    }

    public int getChampionPoints() {
        return championPoints;
    }

    public void setChampionPoints(int championPoints) {
        this.championPoints = championPoints;
    }

    public long getChampionPointsSinceLastLevel() {
        return championPointsSinceLastLevel;
    }

    public void setChampionPointsSinceLastLevel(long championPointsSinceLastLevel) {
        this.championPointsSinceLastLevel = championPointsSinceLastLevel;
    }

    public int getMarkRequiredForNextLevel() {
        return markRequiredForNextLevel;
    }

    public void setMarkRequiredForNextLevel(int markRequiredForNextLevel) {
        this.markRequiredForNextLevel = markRequiredForNextLevel;
    }

    public int getChampionSeasonMilestone() {
        return championSeasonMilestone;
    }

    public void setChampionSeasonMilestone(int championSeasonMilestone) {
        this.championSeasonMilestone = championSeasonMilestone;
    }

    public NextSeasonMilestonesDto getNextSeasonMilestone() {
        return nextSeasonMilestone;
    }

    public void setNextSeasonMilestone(NextSeasonMilestonesDto nextSeasonMilestone) {
        this.nextSeasonMilestone = nextSeasonMilestone;
    }

    public int getTokensEarned() {
        return tokensEarned;
    }

    public void setTokensEarned(int tokensEarned) {
        this.tokensEarned = tokensEarned;
    }

    public List<String> getMilestoneGrades() {
        return milestoneGrades;
    }

    public void setMilestoneGrades(List<String> milestoneGrades) {
        this.milestoneGrades = milestoneGrades;
    }
}
