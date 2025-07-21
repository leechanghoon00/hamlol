package org.example.hamlol.service.impl;

import org.example.hamlol.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.hamlol.service.MemberService;
import org.springframework.stereotype.Service;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final UserRepository userRepository;

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
    public void resetPassword(String uuid, String newPassword) {

    }
}