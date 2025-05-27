package org.example.hamlol.service;

import org.example.hamlol.dto.UserDTO;
import org.example.hamlol.jwt.TokenInfo;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    // 회원가입
    void addUser(UserDTO userDTO);

    // 로그인
    TokenInfo login(String email, String password);

}
