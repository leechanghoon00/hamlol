package org.example.hamlol.service;

import org.springframework.web.multipart.MultipartFile;

public interface ProfileService {
    // username으로 사용자명을 받고 file로 파일을 받아 이미지 저장후 문자열을 반환하는 메서드
    String saveProfileImage(String username, MultipartFile file);

}
