package org.example.hamlol.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RiotAuthResponse {
    private boolean success;
    private String message;
    private String accessToken;
    private String riotId;
    private String summonerName;
}



