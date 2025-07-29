package org.example.hamlol.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordRequestDTO {
    @NotBlank(message = "UUID는 필수입니다.")
    private String uuid;

    @NotBlank(message = "새 비밀번호는 필수입니다.")
    private String newPassword;
}
