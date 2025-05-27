package org.example.hamlol.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameRecordRequest {
    private String riotIdGameName;
    private String riotIdTagline;
    private String matchId;
    private int page =0; // 기본 0부터 페이지 시작
    private int size = 5; // 한페이지당 5

}
