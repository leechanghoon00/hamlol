package org.example.hamlol.service.impl;

import org.example.hamlol.repository.UserRepository;
import org.example.hamlol.service.RedisService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.hamlol.service.MemberService;
import org.springframework.stereotype.Service;
import org.example.hamlol.entity.UserEntity;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final UserRepository userRepository;
    private final RedisService redisService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void checkMemberByEmail(String email) {
        // exists(존재여부를 판단)
        boolean exists = userRepository.findByEmail(email).isPresent();
        // 없으면 출력
        if (!exists) {
            throw new IllegalArgumentException("존재하지 않는 이메일입니다.");
        }

    }

    @Override
    @Transactional
    public void resetPassword(String uuid, String newPassword) {
        // Redis에서 이메일 가져오기
        String email = redisService.getValues(uuid);
        if (email == null) {
            throw new IllegalArgumentException("유효하지 않거나 만료된 인증 링크입니다.");
        }

        // DB에서 사용자 조회
        UserEntity  user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // 비밀번호 암호화 후 저장
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);

        // Redis에서 UUID 제거
        redisService.deleteValues(uuid);
    }

}