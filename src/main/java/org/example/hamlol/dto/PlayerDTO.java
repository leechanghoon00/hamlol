package org.example.hamlol.dto;

public record PlayerDTO(
        String matchId,                           // 매치 ID (외래키)
        String teamType,                          // 팀 타입 ('blue' 또는 'red')
        String riotIdGameName,                    // 참가자 롤 닉네임 (PK)
        String championName,                        // 플레이한 챔피언의 ID
        Integer damageDealtToBuildings,           // 타워 딜량
        Integer goldEarned,                       // 획득 골드
        String individualPosition,                // 개별 포지션
        String item0,                             // 아이템 슬롯 0
        String item1,                             // 아이템 슬롯 1
        String item2,                             // 아이템 슬롯 2
        String item3,                             // 아이템 슬롯 3
        String item4,                             // 아이템 슬롯 4
        String item5,                             // 아이템 슬롯 5
        String item6,                             // 아이템 슬롯 6
        Integer kills,                            // 킬 수
        Integer deaths,                           // 데스 수
        Integer assists,                          // 어시 수
        float kda,                                // KDA (DECIMAL(5,2))
        String riotIdTagline,                     // 소환사 태그라인
        String summoner1Id,                       // 첫 번째 소환사 스펠 ID
        String summoner2Id,                       // 두 번째 소환사 스펠 ID
        String teamPosition,                      // 팀 내 포지션
        Integer totalDamageDealtToChampions,      // 챔피언 대상 피해량
        Integer totalDamageTaken,                 // 받은 총 피해량
        Integer totalHealsOnTeammates,            // 팀원 회복량
        Integer totalMinionsKilled,               // 총 미니언 CS
        Integer visionScore,                      // 시야 점수
        Integer visionWardsBoughtInGame,          // 구매한 제어 와드 수
        Integer wardsPlaced,                      // 설치한 와드 수
        Integer wardsKilled,                      // 파괴한 와드 수
        Boolean win   ,                            // 승리 여부
        String primaryStyle1,//주룬 1
        String primaryStyle2,//주룬 2
        String primaryStyle3,//주룬 3
        String primaryStyle4,//주룬 4
        String subStyle1, //부룬1
        String subStyle2 //부룬 2
) {}
