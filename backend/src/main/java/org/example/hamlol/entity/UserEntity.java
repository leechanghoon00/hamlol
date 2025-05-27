package org.example.hamlol.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.example.hamlol.urlenum.UserType;

@Data
@Entity
@Table(name = "table_user",
        uniqueConstraints = {
        @UniqueConstraint(name = "UNIQUE_username", columnNames = {"username"})
})
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String userName;
    private String password;
    private String email;
    private String number;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type", nullable = false)
    private UserType userType;
}

