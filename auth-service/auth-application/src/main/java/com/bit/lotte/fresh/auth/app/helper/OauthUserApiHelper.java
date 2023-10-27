package com.bit.lotte.fresh.auth.app.helper;

import com.bit.lotte.fresh.auth.KakaoUserInfo;
import com.bit.lotte.fresh.auth.valueobject.OauthUserInfo;
import com.bit.lotte.fresh.user.common.valueobject.AuthProvider;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class OauthUserApiHelper{

  public OauthUserInfo getUserInfo(AuthProvider provider, String token) {
    if (provider.equals(AuthProvider.KAKAO)) {
      return kakaoUserInfo(token);
    }
    return null;
  }

  private KakaoUserInfo kakaoUserInfo(String accessToken) {
    RestTemplate restTemplate = new RestTemplate();
    String userInfoUrl = "https://kapi.kakao.com/v2/user/me";

    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", "Bearer " + accessToken);

    HttpEntity<String> entity = new HttpEntity<>(headers);

    ResponseEntity<KakaoUserInfo> responseEntity = restTemplate.exchange(
        userInfoUrl,
        HttpMethod.GET,
        entity,
        KakaoUserInfo.class
    );

    if (responseEntity.getStatusCode().is2xxSuccessful()) {
      return responseEntity.getBody();
    } else {
      return null;
    }
  }
}