package org.example.hamlol.serviceimpl;

import jakarta.transaction.Transactional;
import org.example.hamlol.dto.UserDTO;
import org.example.hamlol.entity.UserEntity;
import org.example.hamlol.repository.UserRepository;
import org.example.hamlol.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void addUser(UserDTO userDTO) {
        // DTO -> Entity 변환
        UserEntity userEntity = userDTO.toEntity();

        // Entity를 DB에 저장
        userRepository.save(userEntity);
    }
}


