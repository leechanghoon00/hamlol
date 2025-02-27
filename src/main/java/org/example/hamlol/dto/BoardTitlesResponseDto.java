package org.example.hamlol.dto;


import lombok.Builder;

import java.util.List;

@Builder
public record BoardTitlesResponseDto(
        List<String> titles
) {
}
