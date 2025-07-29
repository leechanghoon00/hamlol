package org.example.hamlol.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
//백엔드에서 응답
public class ResetPasswordEmailResponseDTO {
    private String UUID;

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }
}
