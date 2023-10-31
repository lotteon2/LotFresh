package com.lotfresh.userservice.domain.auth.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@ToString
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class OAuth2Attribute {

  private Map<String, Object> attributes;
  private String attributeKey;
  private String email;
  private String nickname;
  private String profile;

  public static OAuth2Attribute of(
      String provider, String attributeKey, Map<String, Object> attributes) {
    switch (provider) {
      case "kakao":
        return ofKakao(attributeKey, attributes);
      default:
        throw new RuntimeException();
    }
  }

  private static OAuth2Attribute ofKakao(String attributeKey, Map<String, Object> attributes) {
    Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
    Map<String, Object> kakaoProfile = (Map<String, Object>) kakaoAccount.get("profile");

    return OAuth2Attribute.builder()
        .nickname((String) kakaoProfile.get("nickname"))
        .email((String) kakaoAccount.get("email"))
        .attributes(kakaoAccount)
        .attributeKey(attributeKey)
        .build();
  }

  public Map<String, Object> convertToMap() {
    Map<String, Object> map = new HashMap<>();
    map.put("key", attributeKey);
    map.put("nickname", nickname);
    map.put("email", email);

    return map;
  }
}
