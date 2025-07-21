package org.example.hamlol.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.example.hamlol.dto.RestPasswordRequestDTO;
import org.example.hamlol.dto.RestPasswordResponseDTO;
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
    @Operation(summary = "UUID 생성 및 이메일 전송")
    public RestPasswordResponseDTO sendResetPassword(
            @Valid @RequestBody// 요청 본문 (JSoN)을 DTO 매핑
            RestPasswordRequestDTO request) {

        //Json에서 email 추출해서 저장
        String email = request.getEmail();


        try {
            // 이메일 존재 확인
            memberService.checkMemberByEmail(email);

            // 이메일 전송 (UUID 생성 + Redis 저장 시도)
            String uuid = mailService.sendResetPasswordEmail(email);

            // 정상 응답 반환
            return RestPasswordResponseDTO.builder()
                    .UUID(uuid)
                    .build();

        } catch (IllegalArgumentException e) {
            // 존재하지 않는 이메일일 경우만 예외 반환
            log.warn("⛔ 존재하지 않는 이메일입니다: {}", email);
            throw e;
        } catch (Exception e) {
            // Redis 저장 실패 같은 기타 예외는 로그만 남기고 "메일 전송 성공"으로 간주
            log.error("⚠️ 메일 전송 후 Redis 저장 실패 등 오류 발생: {}", e.getMessage(), e);

            // Redis 실패 시에도 UUID는 메일 내용에 포함됐기 때문에 사용 가능하므로 반환함
            return RestPasswordResponseDTO.builder()
                    .UUID("메일 전송은 완료되었으나 인증 링크 저장에 실패했을 수 있습니다.")
                    .build();
        }
    }
}