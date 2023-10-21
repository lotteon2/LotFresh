package com.bit.lotte.fresh.auth;

import com.bit.lotte.fresh.auth.valueobject.OauthUserInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoUserInfo extends OauthUserInfo {


}