package org.example.hamlol.controller;

import org.example.hamlol.dto.UserDTO;
import org.example.hamlol.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class Usercontroller {

    private final UserService userService;


    public Usercontroller(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/adduser")
    public ResponseEntity<String> addUser(@RequestBody UserDTO userDTO){
        try {
            userService.addUser(userDTO); //회원가입
            return ResponseEntity.ok("성공");
        }catch (Exception e){
            return ResponseEntity.badRequest().body("실패 : "+e.getMessage());
        }
    }
}
