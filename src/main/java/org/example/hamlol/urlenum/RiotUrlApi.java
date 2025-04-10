package org.example.hamlol.urlenum;

public enum RiotUrlApi {
    // 롤닉과 태그를 통해 puuid 찾는 api
    FIND_BY_PUUID("https://asia.api.riotgames.com/riot/account/v1/accounts/by-riot-id/{gameName}/{tagLine}"),
    // 롤챔프 전체 받는 api
    FIND_BY_CHAMP("https://ddragon.leagueoflegends.com/cdn/15.5.1/data/ko_KR/champion.json"),
    // 게임코드로 전적받는 api
    MATCH("https://asia.api.riotgames.com/lol/match/v5/matches/{matchId}"),
    // 스펠 검색 api
    FIND_BY_SPELL("https://ddragon.leagueoflegends.com/cdn/15.7.1/data/en_US/summoner.json"),
    // 룬 검색 api
    FIND_BY_RUNES("https://ddragon.leagueoflegends.com/cdn/15.7.1/data/ko_KR/runesReforged.json"),
    // 룬만들때 앞에 붙임
    MAKE_RUN("https://ddragon.leagueoflegends.com/cdn/img/");
    private final String url;

    RiotUrlApi(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
