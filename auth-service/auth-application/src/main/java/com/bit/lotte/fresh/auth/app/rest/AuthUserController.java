package com.bit.lotte.fresh.auth.app.rest;

import com.bit.lotte.fresh.auth.app.helper.OauthAuthorizationRequestHelper;
import com.bit.lotte.fresh.auth.app.helper.OauthUserApiHelper;
import com.bit.lotte.fresh.auth.common.util.RedisTokenBlacklistUtil;
import com.bit.lotte.fresh.auth.common.util.TokenParserUtil;
import com.bit.lotte.fresh.auth.service.dto.response.GetAdminInfoListResponse;
import com.bit.lotte.fresh.auth.valueobject.Secret;
import com.bit.lotte.fresh.auth.service.dto.command.AuthUserIdCommand;
import com.bit.lotte.fresh.auth.service.dto.command.CreateAuthDomainCommand;
import com.bit.lotte.fresh.auth.service.dto.command.LoginAuthDomainCommand;
import com.bit.lotte.fresh.auth.service.dto.command.UpdateAuthRoleCommand;
import com.bit.lotte.fresh.auth.service.dto.response.CreateAuthUserResponse;
import com.bit.lotte.fresh.auth.service.dto.response.DeleteAuthUserResponse;
import com.bit.lotte.fresh.auth.service.dto.response.LogOutAuthUserResponse;
import com.bit.lotte.fresh.auth.service.dto.response.LoginAuthUserResponse;
import com.bit.lotte.fresh.auth.service.dto.response.UpdateAuthUserRoleResponse;
import com.bit.lotte.fresh.auth.service.dto.response.UpdateLoginSessionTimeResponse;
import com.bit.lotte.fresh.auth.service.port.input.AuthUserApplicationService;
import com.bit.lotte.fresh.auth.valueobject.AuthRole;
import com.bit.lotte.fresh.auth.valueobject.OauthUserInfo;
import com.bit.lotte.fresh.auth.common.instant.TokenName;
import com.bit.lotte.fresh.auth.common.util.JwtTokenUtil;
import com.bit.lotte.fresh.user.common.valueobject.AuthProvider;
import com.bit.lotte.fresh.user.common.valueobject.AuthUserId;
import io.jsonwebtoken.JwtException;
import java.io.IOException;
import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AuthUserController {

  private final AuthUserApplicationService applicationService;
  private final OauthAuthorizationRequestHelper oauthAuthorizationRequestHelper;
  private final OauthUserApiHelper oauthUserApiHelper;
  private final RedisTokenBlacklistUtil redisTokenBlacklistUtil;


  private AuthProvider getAuthProviderFromUri(HttpServletRequest request) {
    if (request.getRequestURI().contains("kakao")) {
      return AuthProvider.KAKAO;
    }
    return AuthProvider.NONE;
  }

  @PostMapping("/auth")
  public ResponseEntity<CreateAuthUserResponse> createAuthUser(
      @Valid @RequestBody CreateAuthDomainCommand command) {
    log.info("The request is passed through the filter");
    return ResponseEntity.ok(applicationService.createAuthUser(command));
  }

  @PostMapping("/auth/login")
  public ResponseEntity<LoginAuthUserResponse> login(
      @RequestBody LoginAuthDomainCommand command) {
    return ResponseEntity.ok(applicationService.oauthLoginAuthUser(command));
  }

  @GetMapping("/auth/oauth/kakao/login")
  public ResponseEntity<LoginAuthUserResponse> oauthLogin(
      @RequestParam String code, HttpServletRequest request) {
    AuthProvider provider = getAuthProviderFromUri(request);
    String accessToken = oauthAuthorizationRequestHelper.callAccessToken(provider, code,
        Secret.KAKAO_REST_KEY, Secret.KAKAO_REDIRECT);
    OauthUserInfo info = oauthUserApiHelper.getUserInfo(provider, accessToken);
    LoginAuthDomainCommand loginCommand = new LoginAuthDomainCommand(new AuthUserId(info.getId()),
        provider);
    log.info("social authUserId:" + info.getId());
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<LoginAuthDomainCommand> requestEntity = new HttpEntity<>(loginCommand, headers);

    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<LoginAuthUserResponse> responseEntity = restTemplate.postForEntity(
        "http://localhost:8082/auth/login",
        requestEntity,
        LoginAuthUserResponse.class
    );
    return responseEntity.hasBody() ? responseEntity : null;
  }


  @DeleteMapping("/auth")
  public ResponseEntity<DeleteAuthUserResponse> deleteAuthUser(
      @AuthenticationPrincipal AuthUserId authUserId) {

    return ResponseEntity.ok(
        applicationService.deleteAuthUser(new AuthUserIdCommand((authUserId))));

  }

  @PostMapping("/auth/logout")
  public ResponseEntity<LogOutAuthUserResponse> logOut(
      @AuthenticationPrincipal AuthUserId authUserId, HttpServletRequest request) {
    log.info("userId: " + authUserId);
    redisTokenBlacklistUtil.blacklistToken(request.getHeader(TokenName.AUTHENTICATION_TOKEN_NAME));
    return ResponseEntity.ok(applicationService.logOutAuthUser(new AuthUserIdCommand(authUserId)));
  }

  /**
   * @param actorId
   * @param targetId
   * @param categoryId 해당 기능은 유저에서 카테고리 관리자를 업데이트 하는 기능입니다. 일반적인 권한을 업데이트하는 것과 다르게 카테고리 관리자는 관리자는 하위
   *                   카테고리자를 관리할 수 있는지를 확인해야하므로 PathVariable이 하나 추가됩니다.
   */

  //Rest를 두 개 설정하는 건 좋아보이는데, service를 하나로 작성할 수 있지 않나 => SRP principle
  //->안 되는 이유 카테고리 id를 입력해야한다. => 파라미터 입력값이 다르니
  @PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN') or hasRole('ROLE_CATEGORY_ADMIN')")
  @PutMapping("/auth/admins/target/{targetId}/category/{categoryId}")
  public ResponseEntity<UpdateAuthUserRoleResponse> updateCategoryAdmin(
      @AuthenticationPrincipal AuthUserId actorId, @NotNull @PathVariable String targetId,
      @NotNull @PathVariable int categoryId) {
    return ResponseEntity.ok(
        applicationService.updateCategoryAdmin(new UpdateAuthRoleCommand(actorId,
            new AuthUserId(Long.valueOf(targetId)), AuthRole.ROLE_CATEGORY_ADMIN), categoryId));
  }


  @PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN') or hasRole('ROLE_CATEGORY_ADMIN')")
  @PutMapping("/auth/admins/target/{targetId}/role/{role}")
  public ResponseEntity<UpdateAuthUserRoleResponse> updateToSystemAdmin(
      @AuthenticationPrincipal AuthUserId actorId, @PathVariable String targetId,
      @PathVariable AuthRole role) {
    return ResponseEntity.ok(
        applicationService.updateRole(new UpdateAuthRoleCommand(actorId,
            new AuthUserId(Long.valueOf(targetId)), role)));
  }

  @PostMapping("/auth/login-extend")
  public ResponseEntity<UpdateLoginSessionTimeResponse> extendLoginSessionTime(
      HttpServletRequest request, HttpServletResponse response,
      @AuthenticationPrincipal AuthUserId authUserId) throws AuthenticationException, IOException {
    String ordinalToken = request.getHeader(TokenName.AUTHENTICATION_TOKEN_NAME);
    log.info("ordinal token: " + ordinalToken);
    redisTokenBlacklistUtil.blacklistToken(ordinalToken);
    try {
      String newJwtToken = JwtTokenUtil.getNewTokenFromOldToken(
          TokenParserUtil.extractToken(request));
      log.info("new token:" + newJwtToken);
      response.setHeader(TokenName.AUTHENTICATION_TOKEN_NAME, newJwtToken);
    } catch (JwtException e) {
      response.sendError(401, e.getMessage());
    }
    return ResponseEntity.ok(applicationService.extendLoginTime(new AuthUserIdCommand(authUserId)));
  }

  @PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN') or hasRole('ROLE_CATEGORY_ADMIN')")
  @GetMapping("/auth")
  public ResponseEntity<GetAdminInfoListResponse> getAdminList()
      throws AuthenticationException, IOException {
    return ResponseEntity.ok(applicationService.getAuthUserList());

  }


}
