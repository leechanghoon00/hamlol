package org.example.hamlol.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.hamlol.dto.UserDTO;
import org.example.hamlol.jwt.TokenInfo;
import org.example.hamlol.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Tag(name = "addUser API", description = "회원가입하기")

public class Usercontroller {

    private final UserService userService;


    public Usercontroller(UserService userService) {
        this.userService = userService;
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

}
