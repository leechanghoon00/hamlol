package org.example.hamlol.urlenum;

public enum RiotUrlApi {

    FIND_BY_PUUID("https://asia.api.riotgames.com/riot/account/v1/accounts/by-riot-id/{gameName}/{tagLine}"),
    FIND_BY_CHAMP("https://ddragon.leagueoflegends.com/cdn/15.5.1/data/en_US/champion.json"),
    MATCH("https://asia.api.riotgames.com/lol/match/v5/matches/");

    private final String url;

    RiotUrlApi(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
