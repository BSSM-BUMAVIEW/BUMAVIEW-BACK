package com.bssm.bumaview.domain.user.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private Date birth;

    @Column(nullable = false)
    private String name;

    private String refreshToken;

    private LocalDateTime tokenExpirationTime;

    private String profile;

    private UserType userType;

    @Builder
    public User(UserType userType, String email, String password, String name, String profile, Role role) {

        this.userType = userType;
        this.email = email;
        this.password = password;
        this.name = name;
        this.profile = profile;
        this.role = role;
    }

}
