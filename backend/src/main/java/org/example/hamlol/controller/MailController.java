package org.example.hamlol.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.hamlol.dto.ResetPasswordEmailRequestDTO;
import org.example.hamlol.dto.ResetPasswordEmailResponseDTO;

import org.example.hamlol.dto.ResetPasswordRequestDTO;
import org.example.hamlol.service.MemberService;
import org.example.hamlol.service.SendMailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MailController {

    private static final Logger log = LoggerFactory.getLogger(MailController.class);

    private final MemberService memberService;
    private final SendMailService mailService;

    @PostMapping("/send-reset-password")
    @Operation(summary = "비밀번호 재설정 링크 이메일 전송")
    public ResetPasswordEmailResponseDTO sendResetPasswordEmail(
            @Valid @RequestBody ResetPasswordEmailRequestDTO request) {

        String email = request.getEmail();

        try {
            // 이메일 존재 여부 확인
            memberService.checkMemberByEmail(email);

            // UUID 생성 및 메일 전송
            String uuid = mailService.sendResetPasswordEmail(email);

            // 응답 DTO 생성
            return ResetPasswordEmailResponseDTO.builder()
                    .UUID(uuid)
                    .build();

        } catch (IllegalArgumentException e) {
            log.warn("⛔ 존재하지 않는 이메일입니다: {}", email);
            throw e;

        } catch (Exception e) {
            log.error("⚠️ 메일 전송 또는 Redis 저장 중 오류 발생: {}", e.getMessage(), e);

            return ResetPasswordEmailResponseDTO.builder()
                    .UUID("메일은 전송되었으나 인증 정보 저장에 실패했을 수 있습니다.")
                    .build();
        }
    }

    // PasswordController.java 또는 기존 MailController.java에 추가
    @PostMapping("/reset-password")
    @Operation(summary = "UUID로 비밀번호 재설정")
    public void resetPassword(@Valid @RequestBody ResetPasswordRequestDTO request) {
        memberService.resetPassword(request.getUuid(), request.getNewPassword());
    }





}
