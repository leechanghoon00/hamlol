package org.example.hamlol.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.hamlol.entity.UserEntity;
import org.example.hamlol.repository.UserRepository;
import org.example.hamlol.service.ProfileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FilenameUtils;



import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final UserRepository userRepository;


    @Override
    public String saveProfileImage(String username, MultipartFile file) {
        //FilenameUtils.getExtension(file.getOriginalFilename()) = .뒤에있는 문자열 추출
        String ext = FilenameUtils.getExtension(file.getOriginalFilename());
        //  jpg|jpeg|png 만 등록 가능
        if (!ext.matches("(?i)^(jpg|jpeg|png)$")) {
            throw new IllegalArgumentException("지원하지 않는 파일 형식입니다.");
        }
        //UUID + 사용자명으로 고유한 파일명 생성
        String uuid = UUID.randomUUID().toString();
        // 최종 저장될 파일명을 구성: [UUID]_[사용자명].[확장자]
        String fileName = uuid + "_" + username + "." + ext;
        // 파일ㅇ이 저장될 전체 경로 객체 생성
        Path savePath = Paths.get("uploads/profile", fileName);
        try {
            // 디렉토리가 존재하지않으면 새로 생성
            Files.createDirectories(savePath.getParent());
            // 기존 파일 삭제
            Files.deleteIfExists(savePath);

            // 실제 파일 내용을 지정한 경로에 저장
            Files.write(savePath, file.getBytes());
        } catch (IOException e) {
            // 예외
            throw new RuntimeException("프로필 이미지 저장 실패", e);
        }
        // DB에서 사용자 조회
        UserEntity user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("해당 사용자를 찾을 수 없습니다."));
        user.setProfileImageUrl("/images/profile/" + fileName);

        userRepository.save(user);

        return user.getProfileImageUrl();
    }
}
