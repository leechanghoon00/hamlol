package org.example.hamlol.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

// 백엔드에 요청
public class ResetPasswordEmailRequestDTO {
    @Email
    @NotBlank
    private String email;

    public @Email @NotBlank String getEmail() {
        return email;
    }

    public void setEmail(@Email @NotBlank String email) {
        this.email = email;
    }
}
