package com.bssm.bumaview.global.oauth2.user.oauth2;

import com.bssm.bumaview.global.oauth2.user.model.OAuth2Provider;

import java.util.Map;

public interface OAuth2UserInfo {

    OAuth2Provider getProvider();

    String getAccessToken();

    Map<String, Object> getAttributes();

    String getId();

    String getEmail();

    String getName();

    String getFirstName();

    String getLastName();

    String getNickname();

    String getProfileImageUrl();
}