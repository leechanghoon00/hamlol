package org.example.hamlol.service.impl;

import jakarta.transaction.Transactional;
import org.example.hamlol.dto.UserDTO;
import org.example.hamlol.entity.UserEntity;
import org.example.hamlol.jwt.JwtTokenProvider;
import org.example.hamlol.jwt.TokenInfo;
import org.example.hamlol.repository.UserRepository;
import org.example.hamlol.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    // UserRepository를 final 필드로 선언하여 의존성을 주입빋아 DB작업에 사용
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final JwtTokenProvider jwtTokenProvider;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, AuthenticationManager authenticationManager, BCryptPasswordEncoder bCryptPasswordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    @Transactional
    public void addUser(UserDTO userDTO) { // 클라이언트로부터 받은 UserDTO를 기반으로 새로운 정보를 데이터 베이스에 저장

        // 평문 비밀번호를 암호화
        String encryptedPassword = bCryptPasswordEncoder.encode(userDTO.getPassword());
        userDTO.setPassword(encryptedPassword);

        // DTO -> Entity 변환 후 DB 저장
        UserEntity userEntity = userDTO.toEntity();
        userRepository.save(userEntity);
    }

    @Override
    public TokenInfo login(String email, String password) {
        // DB에서 사용자 조회
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("사용자 없음: " + email));

        // 디버깅 로그 추가
        System.out.println("DB 암호화된 비밀번호: " + userEntity.getPassword());
        System.out.println("입력한 평문 비밀번호: " + password);

        // 비밀번호 비교: 평문과 DB에 저장된 암호화된 비밀번호 비교
        if (!bCryptPasswordEncoder.matches(password, userEntity.getPassword())) {
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }

        // 비밀번호가 일치하면 인증 처리
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(email, password);

        Authentication authentication = authenticationManager.authenticate(token);

        // 인증 성공 시, SecurityContext에 인증 정보를 저장
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 디버깅 로그 추가
        System.out.println("인증 완료, 로그인 성공");
        //jwt토큰 생성
        return jwtTokenProvider.generateToken(authentication.getAuthorities(), email);

    }


    // UserDetailsService 인터페이스 구현: 이메일로 사용자 정보 조회
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + email));
        return User.builder()
                .username(userEntity.getEmail())
                .password(userEntity.getPassword())
                .roles("USER") // 필요에 따라 추가 권한 설정
                .build();
    }

}


