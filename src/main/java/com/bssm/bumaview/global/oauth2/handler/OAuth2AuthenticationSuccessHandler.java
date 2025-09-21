package com.bssm.bumaview.global.oauth2.handler;

import com.bssm.bumaview.domain.user.domain.Role;
import com.bssm.bumaview.domain.user.domain.User;
import com.bssm.bumaview.domain.user.domain.UserType;
import com.bssm.bumaview.domain.user.domain.repository.UserRepository;
import com.bssm.bumaview.global.jwt.dto.JwtTokenDto;
import com.bssm.bumaview.global.jwt.service.TokenManager;
import com.bssm.bumaview.global.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import com.bssm.bumaview.global.oauth2.service.OAuth2UserPrincipal;
import com.bssm.bumaview.global.oauth2.user.model.OAuth2Provider;
import com.bssm.bumaview.global.oauth2.user.oauth2.OAuth2UserUnlinkManager;
import com.bssm.bumaview.global.oauth2.util.CookieUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import static com.bssm.bumaview.global.oauth2.HttpCookieOAuth2AuthorizationRequestRepository.MODE_PARAM_COOKIE_NAME;
import static com.bssm.bumaview.global.oauth2.HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

@Slf4j
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;
    private final OAuth2UserUnlinkManager oAuth2UserUnlinkManager;
    private final UserRepository userRepository;
    private final TokenManager tokenManager;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        String targetUrl = determineTargetUrl(request, response, authentication);

        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }

        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) {

        Optional<String> redirectUri = CookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);

        String targetUrl = redirectUri.orElse(getDefaultTargetUrl());

        String mode = CookieUtils.getCookie(request, MODE_PARAM_COOKIE_NAME)
                .map(Cookie::getValue)
                .orElse("");

        log.info("OAuth2 SuccessHandler mode = {}", mode);

        OAuth2UserPrincipal principal = getOAuth2UserPrincipal(authentication);

        if (principal == null) {
            return UriComponentsBuilder.fromUriString(targetUrl)
                    .queryParam("error", "Login failed")
                    .build().toUriString();
        }

        if ("login".equalsIgnoreCase(mode)) {

            String email = principal.getUserInfo().getEmail();
            String name = principal.getUserInfo().getName();
            String profile = principal.getUserInfo().getProfileImageUrl();
            OAuth2Provider provider = principal.getUserInfo().getProvider();

            User user = userRepository.findByEmail(email)
                    .orElseGet(() -> userRepository.save(
                            User.builder()
                                    .userType(UserType.valueOf(provider.name()))
                                    .email(email)
                                    .password("")
                                    .name(name)
                                    .profile(profile)
                                    .role(Role.USER)
                                    .build()
                    ));

            JwtTokenDto jwtTokenDto = tokenManager.createJwtTokenDto(user.getId(), user.getRole());

            LocalDateTime expirationTime = jwtTokenDto.getRefreshTokenExpireTime()
                    .toInstant()
                    .atZone(ZoneId.of("Asia/Seoul"))
                    .toLocalDateTime();

            user.updateRefreshToken(jwtTokenDto.getRefreshToken(), expirationTime);
            userRepository.save(user);

            return UriComponentsBuilder.fromUriString(targetUrl)
                    .queryParam("access_token", jwtTokenDto.getAccessToken())
                    .queryParam("refresh_token", jwtTokenDto.getRefreshToken())
                    .build().toUriString();

        } else if ("unlink".equalsIgnoreCase(mode)) {

            String accessToken = principal.getUserInfo().getAccessToken();
            OAuth2Provider provider = principal.getUserInfo().getProvider();

            oAuth2UserUnlinkManager.unlink(provider, accessToken);

            return UriComponentsBuilder.fromUriString(targetUrl)
                    .build().toUriString();

        } else {
            return UriComponentsBuilder.fromUriString(targetUrl)
                    .queryParam("error", "Login failed")
                    .build().toUriString();
        }
    }

    private OAuth2UserPrincipal getOAuth2UserPrincipal(Authentication authentication) {
        Object principal = authentication.getPrincipal();

        if (principal instanceof OAuth2UserPrincipal) {
            return (OAuth2UserPrincipal) principal;
        }
        return null;
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }
}
