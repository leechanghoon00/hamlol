package org.example.hamlol.controller;


import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.example.hamlol.dto.RestPasswordRequestDTO;
import org.example.hamlol.dto.RestPasswordResponseDTO;
import org.example.hamlol.service.MemberService;
import org.example.hamlol.service.SendMailService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MailController {


    private final MemberService memberService;
    private final SendMailService mailService;

    @PostMapping("/send-reset-password")
    @Operation(summary = "UUID 생성 및 이메일 전송")
    public RestPasswordResponseDTO sendResetPassword(
            @Valid @RequestBody// 요청 본문 (JSoN)을 DTO 매핑
            RestPasswordRequestDTO request) {

        //Json에서 email 추출해서 저장
        String email = request.getEmail();
        // 이메일이 DB에 있는지 확인
        memberService.checkMemberByEmail(email);
        // 있으면 메일 전송
        String uuid = mailService.sendResetPasswordEmail(email);

        return RestPasswordResponseDTO.builder()
                .UUID(uuid)
                .build();
    }



}



