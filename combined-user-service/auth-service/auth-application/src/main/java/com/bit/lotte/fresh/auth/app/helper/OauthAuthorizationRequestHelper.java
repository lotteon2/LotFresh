package com.bit.lotte.fresh.auth.app.helper;

import com.bit.lotte.fresh.user.common.valueobject.AuthProvider;


import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class OauthAuthorizationRequestHelper {

  public String callAccessToken(AuthProvider authProvider, String code, String restApiKey, String redirectUrl){
    if(authProvider.equals(AuthProvider.KAKAO)){
      return getAccessToken(code,restApiKey,redirectUrl);
    }
    return null;
  }

public String getAccessToken(String code, String restApiKey, String redirectUrl) {
    String accessToken = "";

    String authorizationUrl = "https://kauth.kakao.com/oauth/token";

    RestTemplate restTemplate = new RestTemplate();

    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(authorizationUrl)
            .queryParam("grant_type", "authorization_code")
            .queryParam("client_id", restApiKey)
            .queryParam("redirect_uri", redirectUrl)
            .queryParam("code", code);


    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", "Bearer " + code);

    HttpEntity<String> requestEntity = new HttpEntity<>(headers);

    ResponseEntity<String> responseEntity = restTemplate.exchange(
            builder.toUriString(),
            HttpMethod.POST,
            requestEntity,
            String.class
    );

    if (responseEntity.getStatusCode() == HttpStatus.OK) {
        String response = responseEntity.getBody();

        String accessTokenRegex = "\"access_token\":\"(.*?)\"";
        String refreshTokenRegex = "\"refresh_token\":\"(.*?)\"";

        accessToken = extractToken(response, accessTokenRegex);
    }

    return accessToken;
}

private String extractToken(String response, String regex) {
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(response);

    if (matcher.find()) {
        return matcher.group(1);
    }

    return "";
}
}
