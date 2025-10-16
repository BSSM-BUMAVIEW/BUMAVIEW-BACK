package com.bssm.bumaview.domain.auth;

import com.bssm.bumaview.domain.user.domain.Role;
import com.bssm.bumaview.global.jwt.dto.JwtTokenDto;
import com.bssm.bumaview.global.jwt.service.TokenManager;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequest request) {
        userService.signup(request.getEmail(), request.getPassword(), request.getName());
        return ResponseEntity.ok("회원가입 성공 ✅");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(userService.login(request.getEmail(), request.getPassword()));
    }

    @Getter
    public static class SignupRequest {
        private String email;
        private String password;
        private String name;
    }

    @Getter
    public static class LoginRequest {
        private String email;
        private String password;
    }
}