package org.example.hamlol.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RestPasswordResponseDTO {
    private String UUID;

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }
}
