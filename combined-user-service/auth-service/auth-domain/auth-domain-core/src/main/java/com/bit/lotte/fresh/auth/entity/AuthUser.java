package com.bit.lotte.fresh.auth.entity;

import com.bit.lotte.fresh.auth.exception.AuthUserDomainException;
import com.bit.lotte.fresh.auth.exception.AuthenticationDomainException;
import com.bit.lotte.fresh.auth.exception.AuthorizationAuthDomainException;
import com.bit.lotte.fresh.auth.exception.LoginFailedAuthDomainException;
import com.bit.lotte.fresh.auth.exception.LoginTimeExpiredException;
import com.bit.lotte.fresh.user.common.valueobject.AuthProvider;
import com.bit.lotte.fresh.auth.valueobject.AuthRole;
import com.bit.lotte.fresh.user.common.entity.AggregateRoot;
import com.bit.lotte.fresh.user.common.valueobject.AuthUserId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
public class AuthUser extends AggregateRoot<AuthUserId> {

 private final long LOGIN_SESSION_HOUR_TIME = 24;
 private String email;
 private String password;
 private AuthRole userRole;
 private String description;
 private boolean isLoginSessionExpired;
 private AuthProvider authProvider;
 private ZonedDateTime lastLoginTime;



 public AuthUser oauthUserCreation(AuthUserId id,
     AuthProvider authProvider) {
  if (id == null || authProvider == null) {
   throw new AuthUserDomainException("인증 제공자와, 아이디를 입력해야합니다.");
  }
  return AuthUser.builder().entityId(id).authProvider(authProvider).userRole(AuthRole.ROLE_USER).build();
 }


 public void checkCategoryAdminAuthorization(String actorCategoryAdminDescription,
     String targetCategoryAdminDescription) {
  List<String> actorSubIdList = List.of(actorCategoryAdminDescription.split(","));
  List<String> targetSubIdList = List.of(targetCategoryAdminDescription.split(","));

  if (!actorSubIdList.containsAll(targetSubIdList)) {
   throw new AuthorizationAuthDomainException("자신의 하위 카테고리에 관해서만 해당 카테고리 관리자로 설정할 수 있습니다.");
  }

 }


 public void updateAdminAuthorization(AuthRole actorRole, String actorDescription, AuthRole myRole,
     String myDescription) {

  if(actorRole.equals(AuthRole.ROLE_SYSTEM_ADMIN) && myRole.equals(AuthRole.ROLE_SYSTEM_ADMIN)){
   throw new AuthUserDomainException("시스템 관리자는 시스템 관리자를 업데이트할 수 없습니다.");
  }
  if (actorRole.equals(AuthRole.ROLE_USER)) {
   throw new AuthUserDomainException("권한이 없습니다.");
  } else if (actorRole.equals(AuthRole.ROLE_CATEGORY_ADMIN)) {
   checkCategoryAdminAuthorization(actorDescription, myDescription);
   userRole = myRole;
   description = myDescription;
  } else if (actorRole.equals(AuthRole.ROLE_SYSTEM_ADMIN)) {
   this.userRole = myRole;
  }
 }

 public void loginProcessor(AuthUserId id, String email, String password,
     AuthProvider authProvider) {
  if (authProvider.equals(AuthProvider.NONE)) {
    systemLogin(email, password);
  }
   oauthLogin(id);
 }

 public void oauthLogin(AuthUserId userId) {
  if (!userId.equals(this.getEntityId())) {
   throw new LoginFailedAuthDomainException("존재하지 않는 소셜 로그인 계정입니다.");
  }
 }

 public void systemLogin(String email, String password) {
  if (!email.equals(this.email) && password.equals(this.password)) {
   throw new LoginFailedAuthDomainException("존재하지 시스템 로그인 계정입니다.");
  }
 }

 public void isExpired(ZonedDateTime currentTime) {
  if (currentTime.minusHours(LOGIN_SESSION_HOUR_TIME).compareTo(lastLoginTime) > 0) {
   throw new LoginTimeExpiredException("로그인 시간이 만료되었습니다.");
  }
 }

 public void extendLoginSession(AuthUserId userId) {
  if (userId.equals(this.getEntityId())) {
   isExpired(ZonedDateTime.now());
   lastLoginTime = ZonedDateTime.now().plusHours(1L);
  } else {
   throw new AuthenticationDomainException(" 일치 하지 않는 유저 정보입니다.");
  }
 }

 public void logOut(AuthUserId authUserId) {
  if (!this.getEntityId().equals(authUserId)) {
   throw new AuthenticationDomainException("일치 하지 않는 유저 정보입니다.");
  }
 }

 public void setCategoryAdminSubIdList(Set<Integer> subCategoryAdminListNumber) {
  if (userRole.equals(AuthRole.ROLE_CATEGORY_ADMIN)) {
   this.setCategoryAdminDetailDescription(subCategoryAdminListNumber);
  } else {
   throw new AuthorizationAuthDomainException("카테고리 관리자가 아닙니다.");
  }

 }

 /**
  * @param subCategoryAdminListNumber 카테고리 admin의 아이디는 description에 등록된다. 해당 컨벤션은 아이디,아이디,아이디 형식으로
  *                                   이뤄진다.
  */
 public void setCategoryAdminDetailDescription(Set<Integer> subCategoryAdminListNumber) {
  StringBuilder sb = new StringBuilder();
  for (Integer i : subCategoryAdminListNumber) {
   sb.append(i);
   sb.append(",");
  }
  this.description = sb.toString();
 }


}

