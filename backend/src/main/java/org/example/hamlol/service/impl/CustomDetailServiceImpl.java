package org.example.hamlol.service.impl;

import org.example.hamlol.entity.AccountEntity;
import org.example.hamlol.entity.UserEntity;
import org.example.hamlol.jwt.CustomUser;
import org.example.hamlol.repository.AccountRepository;
import org.example.hamlol.repository.UserRepository;
import org.example.hamlol.service.CustomDetailService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomDetailServiceImpl implements CustomDetailService {
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    public CustomDetailServiceImpl(UserRepository userRepository, AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }


    @Override
    public UserDetails gameNamebyemail(String email) {
        // email로 entity 조회하기
        UserEntity user = userRepository.findByEmail(email).orElseThrow(()
                -> new UsernameNotFoundException(email + "업승ㅁ"));
// user(email)로 account안에 있는지 확인
        AccountEntity account = accountRepository.findByUserEntity(user);
        // 있다면 gameName과tagLine을 가져오고 아니면 빈 문자열
        String gameName;
        String tagLine;
        if (account != null) {
            gameName = account.getGameName();
            tagLine = account.getTagLine();
        } else {
            gameName = "";
            tagLine = "";
        }
        //GrantedAuthority(권한들)을 리스트로 만듬
        List<GrantedAuthority> authorities = new ArrayList<>();

        //사용자 타입(user,ADMIN)을 꺼내 ROLE_타입 형태의 GrantedAuthority로 추가함
        // SimpleGrantedAuthority : GrantedAuthority의 기본 구현체로 안에있는 역할이나 권한을 문자열로 저장함
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getUserType().name()));

        // 저장
        return new CustomUser(
                user.getEmail(),
                gameName,
                tagLine,
                authorities
        );
    }
}