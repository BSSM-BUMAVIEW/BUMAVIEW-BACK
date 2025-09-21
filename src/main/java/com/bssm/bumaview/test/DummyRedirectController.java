package com.bssm.bumaview.test;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DummyRedirectController {

    @GetMapping("/oauth2/callback")
    public ResponseEntity<String> callback(
            @RequestParam String access_token,
            @RequestParam String refresh_token
    ) {
        return ResponseEntity.ok("✅ 로그인 성공!\n\nAccess Token: " + access_token +
                "\nRefresh Token: " + refresh_token);
    }
}
