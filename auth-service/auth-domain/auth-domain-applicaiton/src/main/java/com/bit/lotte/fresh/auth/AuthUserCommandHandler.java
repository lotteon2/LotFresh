package com.bit.lotte.fresh.auth;

import com.bit.lotte.fresh.auth.dto.command.AuthUserIdCommand;
import com.bit.lotte.fresh.auth.dto.command.CreateAuthDomainCommand;
import com.bit.lotte.fresh.auth.dto.command.LoginAuthDomainCommand;
import com.bit.lotte.fresh.auth.entity.AuthUser;
import com.bit.lotte.fresh.auth.event.CreateAuthDomainEvent;
import com.bit.lotte.fresh.auth.event.DeleteAuthDomainEvent;
import com.bit.lotte.fresh.auth.event.LoginAuthDomainEvent;
import com.bit.lotte.fresh.auth.event.LoginSessionExtendAuthDomainEvent;
import com.bit.lotte.fresh.auth.event.LogoutAuthDomainEvent;
import com.bit.lotte.fresh.auth.event.UpdateUserAuthRoleDomainEvent;
import com.bit.lotte.fresh.auth.exception.AuthUserDomainException;
import com.bit.lotte.fresh.auth.mapper.AuthUserMapper;
import com.bit.lotte.fresh.auth.repository.AuthUserRepository;
import java.time.ZonedDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AuthUserCommandHandler {

  private final AuthUserRepository authUserRepository;
  private final AuthDomainApplicationService authService;
  private final AuthUserMapper authUserMapper;


  public AuthUser getAuthUser(AuthUserIdCommand idCommand) {
    return Optional.ofNullable(
        authUserRepository.getAuthUser(idCommand.getAuthUserId())).orElseThrow(() -> {
      throw new AuthUserDomainException("존재하지 않는 회원입니다.");
    });
  }

  public CreateAuthDomainEvent createAuthUser(CreateAuthDomainCommand command) {
    AuthUser authUser = authUserMapper.createAuthDomainCommandToAuthUser(command);
    AuthUser savedAuthUser = authUserRepository.createAuthUser(authUser);
    if (savedAuthUser == null) {
      throw new AuthUserDomainException("Auth 회원을 생성할 수 없습니다. 이미 존재한 회원이거나 시스템 오류입니다.");
    }
    return authService.createAuthUser(authUser);
  }

  public DeleteAuthDomainEvent deleteAuthUser(AuthUserIdCommand id) {
    AuthUser authUser = getAuthUser(id);
    authUserRepository.deleteAuthUser(id.getAuthUserId());
    return new DeleteAuthDomainEvent(authUser, ZonedDateTime.now());
  }

  public LoginAuthDomainEvent login(LoginAuthDomainCommand command) {
    AuthUser authUser = authUserMapper.loginAuthDomainCommandToUser(command);
    authUser.loginProcessor(authUser.getId(), authUser.getEmail(), authUser.getPassword(),
        authUser.getAuthProvider());
    return new LoginAuthDomainEvent(authUser, ZonedDateTime.now());
  }

  public LoginSessionExtendAuthDomainEvent loginSessionExtend(AuthUserIdCommand id) {
    AuthUser authUser = getAuthUser(id);
    authUser.extendLoginSession(id.getAuthUserId());
    return new LoginSessionExtendAuthDomainEvent(authUser, ZonedDateTime.now());
  }

  public LogoutAuthDomainEvent logout(AuthUserIdCommand id) {
    AuthUser authUser = getAuthUser(id);
    authUser.logOut(id.getAuthUserId());
    authUserRepository.updateTheLastLogin(id.getAuthUserId());
    return new LogoutAuthDomainEvent(authUser, ZonedDateTime.now());
  }

  public UpdateUserAuthRoleDomainEvent updateRole(AuthUserIdCommand actor, AuthUserIdCommand target) {
    AuthUser actorUser = getAuthUser(actor);
    AuthUser targetUser = getAuthUser(target);

    targetUser.updateAdminAuthorization(actorUser.getUserRole(), actorUser.getDescription(),
        targetUser.getUserRole(),
        targetUser.getDescription());
    authUserRepository.updateRole(targetUser.getId(),targetUser.getUserRole());

    return new UpdateUserAuthRoleDomainEvent(targetUser, ZonedDateTime.now());
  }

}
