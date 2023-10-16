package com.bit.lotte.fresh.auth.app.rest;

import com.bit.lotte.fresh.auth.dto.command.AuthUserIdCommand;
import com.bit.lotte.fresh.auth.dto.command.CreateAuthDomainCommand;
import com.bit.lotte.fresh.auth.dto.command.OauthLoginDomainCommand;
import com.bit.lotte.fresh.auth.dto.command.UpdateAuthRoleCommand;
import com.bit.lotte.fresh.auth.dto.response.CreateAuthUserResponse;
import com.bit.lotte.fresh.auth.dto.response.DeleteAuthUserResponse;
import com.bit.lotte.fresh.auth.dto.response.LogOutAuthUserResponse;
import com.bit.lotte.fresh.auth.dto.response.LoginAuthUserResponse;
import com.bit.lotte.fresh.auth.dto.response.UpdateAuthUserRoleResponse;
import com.bit.lotte.fresh.auth.dto.response.UpdateLoginSessionTimeResponse;
import com.bit.lotte.fresh.auth.port.input.AuthUserApplicationService;
import com.bit.lotte.fresh.auth.valueobject.AuthRole;
import com.bit.lotte.fresh.common.instant.LoginSessionTime;
import com.bit.lotte.fresh.common.instant.TokenName;
import com.bit.lotte.fresh.common.util.CookieUtil;
import com.bit.lotte.fresh.common.util.JwtTokenUtil;
import com.bit.lotte.fresh.user.common.valueobject.AuthUserId;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController("/auth")
public class AuthUserApplication {

  private final AuthUserApplicationService applicationService;


  @PostMapping("/")
  public ResponseEntity<CreateAuthUserResponse> createAuthUser(CreateAuthDomainCommand command) {
    return ResponseEntity.ok(applicationService.createAuthUser(command));
  }

  @PostMapping("/login")
  public ResponseEntity<LoginAuthUserResponse> oauthLogin(OauthLoginDomainCommand command) {
    return ResponseEntity.ok(applicationService.oauthLoginAuthUser(command));
  }

  @DeleteMapping("/")
  public ResponseEntity<DeleteAuthUserResponse> deleteAuthUser(
      @AuthenticationPrincipal AuthUserId authUserId) {
    return ResponseEntity.ok(applicationService.deleteAuthUser(new AuthUserIdCommand(authUserId)));

  }

  @PostMapping("/log-out")
  public ResponseEntity<LogOutAuthUserResponse> logOut(
      @AuthenticationPrincipal AuthUserId authUserId) {
    return ResponseEntity.ok(applicationService.logOutAuthUser(new AuthUserIdCommand(authUserId)));
  }

  @PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN') or hasRole('ROLE_CATEGORY_ADMIN')")
  @PutMapping("/admins/target/{targetId}/category/{categoryId}")
  public ResponseEntity<UpdateAuthUserRoleResponse> initUserToCategoryAdmin(
      @AuthenticationPrincipal AuthUserId actorId, @PathVariable AuthUserId targetId,
      @PathVariable int cateogryId) {
    return ResponseEntity.ok(
        applicationService.updateUserToInitCategoryAdmin(new UpdateAuthRoleCommand(actorId,
            targetId,AuthRole.ROLE_CATEGORY_ADMIN),cateogryId));
  }

  @PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN') or hasRole('ROLE_CATEGORY_ADMIN')")
  @PutMapping("/admins/target/{targetId}/role/{role}")
  public ResponseEntity<UpdateAuthUserRoleResponse> updateRole(
      @AuthenticationPrincipal AuthUserId actorId, AuthUserId targetId, AuthRole authRole) {
    return ResponseEntity.ok(
        applicationService.updateRole(new UpdateAuthRoleCommand(actorId,
            targetId,authRole)));
  }

  @PostMapping("/login-extend")
  public ResponseEntity<UpdateLoginSessionTimeResponse> extendLoginSessionTime(
      HttpServletRequest request, HttpServletResponse response,
      @PathVariable AuthUserIdCommand authUserIdCommand) {
    String oldJwtToken = CookieUtil.getCookie(request, TokenName.JWT_COOKIE_NAME);
    String newJwtToken = JwtTokenUtil.getNewTokenFromOldToken(oldJwtToken);
    CookieUtil.setCookie(response, TokenName.JWT_COOKIE_NAME, "Bearer " + newJwtToken,
        LoginSessionTime.LOGIN_SESSION_SEC);
    return ResponseEntity.ok(applicationService.extendLoginTime(authUserIdCommand));
  }


}
