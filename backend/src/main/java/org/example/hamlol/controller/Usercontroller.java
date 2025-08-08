package org.example.hamlol.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.hamlol.dto.UserDTO;
import org.example.hamlol.entity.UserEntity;
import org.example.hamlol.jwt.CustomUser;
import org.example.hamlol.jwt.TokenInfo;
import org.example.hamlol.repository.UserRepository;
import org.example.hamlol.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Tag(name = "addUser API", description = "회원가입하기")

public class Usercontroller {

    private final UserService userService;
    private final UserRepository userRepository;

    public Usercontroller(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    //회원가입
    @PostMapping("/adduser")
    public ResponseEntity<String> addUser(@RequestBody UserDTO userDTO){
        try {
            userService.addUser(userDTO); //회원가입
            return ResponseEntity.ok("성공");
        }catch (Exception e){
            return ResponseEntity.badRequest().body("실패 : "+e.getMessage());
        }
    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity<TokenInfo> login(@RequestBody UserDTO userDTO) {
        TokenInfo tokenInfo = userService.login(userDTO.getEmail(), userDTO.getPassword());
        return ResponseEntity.ok(tokenInfo);
    }

    // 현재 사용자 정보 조회
    @GetMapping("/user/me")
    public ResponseEntity<Map<String, String>> getCurrentUser(@AuthenticationPrincipal CustomUser user) {
        try {
            UserEntity userEntity = userRepository.findByEmail(user.getUsername())
                    .orElseThrow(() -> new RuntimeException("해당 사용자를 찾을 수 없습니다."));

            Map<String, String> response = new HashMap<>();
            response.put("profileImageUrl", userEntity.getProfileImageUrl() != null ? userEntity.getProfileImageUrl() : "");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println("사용자 정보 조회 실패: " + e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }

}
