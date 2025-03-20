package org.example.hamlol.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.hamlol.entity.UserEntity;
import org.example.hamlol.urlenum.UserType;

@Getter
@Setter
public class UserDTO {
    private Long userId; //회원번호
    private String username; //이름
    private String password; //비밀번호
    private String email;
    private String number;
    private UserType userType; // ADMIN 또는 USER


    // DTO를 Entity로 변환하는 메서드
    public UserEntity toEntity() {
        UserEntity userEntity = new UserEntity();
        // userId는 자동 생성되므로 설정하지 않음
        userEntity.setUserName(this.username);  // DTO에서 username을 가져와 Entity에 설정
        userEntity.setPassword(this.password);  // DTO에서 password를 가져와 Entity에 설정
        userEntity.setEmail(this.email);
        userEntity.setNumber(this.number);
        userEntity.setUserType(this.userType != null ? this.userType : UserType.USER);

        return userEntity;
    }
}
