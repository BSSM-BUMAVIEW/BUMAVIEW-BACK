package com.bssm.bumaview.domain.auth;

import com.bssm.bumaview.domain.user.domain.Role;
import com.bssm.bumaview.domain.user.domain.User;
import com.bssm.bumaview.domain.user.domain.UserType;
import com.bssm.bumaview.domain.user.domain.repository.UserRepository;
import com.bssm.bumaview.global.jwt.dto.JwtTokenDto;
import com.bssm.bumaview.global.jwt.service.TokenManager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenManager tokenManager;

    /**
     * 회원가입
     */
    public void signup(String email, String password, String name) {
        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        User newUser = User.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .name(name)
                .role(Role.USER)
                .userType(UserType.LOCAL)
                .build();

        userRepository.save(newUser);
    }

    /**
     * 로그인 + JWT 발급
     */
    public JwtTokenDto login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다."));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 올바르지 않습니다.");
        }

        JwtTokenDto jwtTokenDto = tokenManager.createJwtTokenDto(user.getId(), user.getRole());



        userRepository.save(user);

        return jwtTokenDto;
    }
}