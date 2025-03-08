package org.example.hamlol.service.impl;

import jakarta.transaction.Transactional;
import org.example.hamlol.dto.UserDTO;
import org.example.hamlol.entity.UserEntity;
import org.example.hamlol.repository.UserRepository;
import org.example.hamlol.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    // UserRepository를 final 필드로 선언하여 의존성을 주입빋아 DB작업에 사용
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void addUser(UserDTO userDTO) { // 클라이언트로부터 받은 UserDTO를 기반으로 새로운 정보를 데이터 베이스에 저장
        // DTO -> Entity 변환
        UserEntity userEntity = userDTO.toEntity();

        // Entity를 DB에 저장
        userRepository.save(userEntity);
    }
}


