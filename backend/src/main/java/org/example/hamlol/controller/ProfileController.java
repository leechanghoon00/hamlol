package org.example.hamlol.controller;

import lombok.RequiredArgsConstructor;
import org.example.hamlol.jwt.CustomUser;
import org.example.hamlol.repository.UserRepository;
import org.example.hamlol.service.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;



@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final UserRepository userRepository;
    private final ProfileService profileService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadProfileImage(
            @AuthenticationPrincipal CustomUser user, //로그인된 사용자 정보를 주입받음
            @RequestParam("file") MultipartFile file) { // 클라이언트가 업로드한 파일을 받음
        // 실제 파일 저장 및 db 저장 처리
        String imageUrl = profileService.saveProfileImage(user.getUsername(), file);

        return ResponseEntity.ok(imageUrl);
    }



}
