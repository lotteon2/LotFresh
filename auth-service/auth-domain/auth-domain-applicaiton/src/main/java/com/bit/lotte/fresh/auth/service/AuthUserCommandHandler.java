package com.bit.lotte.fresh.auth.service;

import com.bit.lotte.fresh.auth.AuthDomainApplicationService;
import com.bit.lotte.fresh.auth.AuthDomainApplicationServiceImpl;
import com.bit.lotte.fresh.auth.service.dto.command.AuthUserIdCommand;
import com.bit.lotte.fresh.auth.service.dto.command.CreateAuthDomainCommand;
import com.bit.lotte.fresh.auth.service.dto.command.LoginAuthDomainCommand;
import com.bit.lotte.fresh.auth.service.dto.command.UpdateAuthRoleCommand;
import com.bit.lotte.fresh.auth.entity.AuthUser;
import com.bit.lotte.fresh.auth.event.CreateAuthDomainEvent;
import com.bit.lotte.fresh.auth.event.DeleteAuthDomainEvent;
import com.bit.lotte.fresh.auth.event.LoginAuthDomainEvent;
import com.bit.lotte.fresh.auth.event.LoginSessionExtendAuthDomainEvent;
import com.bit.lotte.fresh.auth.event.LogoutAuthDomainEvent;
import com.bit.lotte.fresh.auth.event.UpdateUserAuthRoleDomainEvent;
import com.bit.lotte.fresh.auth.exception.AuthUserDomainException;
import com.bit.lotte.fresh.auth.service.dto.response.UpdateAuthUserRoleResponse;
import com.bit.lotte.fresh.auth.service.mapper.AuthUserMapper;
import com.bit.lotte.fresh.auth.service.repository.AuthUserRepository;
import com.bit.lotte.fresh.auth.valueobject.AuthRole;
import com.bit.lotte.fresh.user.common.valueobject.AuthUserId;
import java.time.ZonedDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AuthUserCommandHandler {

  private final AuthUserRepository authUserRepository;
  private final AuthDomainApplicationService authService = new AuthDomainApplicationServiceImpl();
  private final AuthUserMapper authUserMapper;


  public AuthUser getAuthUser(AuthUserIdCommand idCommand) {
    return authUserRepository.getAuthUser(idCommand.getAuthUserId());
  }

  public UpdateUserAuthRoleDomainEvent setCategorySubIdDescription(AuthUserIdCommand idCommand,
      String description) {
    //Target.updateAdminAuthorization => not change the inner domain logic. just change the input parameter by making new method in commandHandler
    AuthUser targetUser = getAuthUser(idCommand);
    UpdateUserAuthRoleDomainEvent event =authService.updateCategoryAdminSubIdList(targetUser,description);
    authUserRepository.createAuthUser(targetUser);
    return event;
  }

  public CreateAuthDomainEvent createOauthUser(CreateAuthDomainCommand command) {
    AuthUser authUser = authUserMapper.createAuthDomainCommandToAuthUser(command);
    CreateAuthDomainEvent event = authService.createOauthUser(authUser);
    AuthUser savedAuthUser = authUserRepository.createAuthUser(event.getAuthUser());
    if (savedAuthUser == null) {
      throw new AuthUserDomainException("Auth 회원을 생성할 수 없습니다. 이미 존재한 회원이거나 시스템 오류입니다.");
    }
    return event;
  }

  public DeleteAuthDomainEvent deleteAuthUser(AuthUserIdCommand id) {
    AuthUser authUser = getAuthUser(id);
    DeleteAuthDomainEvent event = authService.deleteAuthUser(authUser);
    authUserRepository.deleteAuthUser(id.getAuthUserId());
    return event;
  }

  public LoginAuthDomainEvent login(LoginAuthDomainCommand command) {
    AuthUser authUser = authUserMapper.loginAuthDomainCommandToUser(command);
    authService.login(authUser);
    return new LoginAuthDomainEvent(authUser, ZonedDateTime.now());
  }

  public LoginSessionExtendAuthDomainEvent loginSessionExtend(AuthUserIdCommand id) {
    AuthUser authUser = getAuthUser(id);
    authService.loginExtend(authUser);
    return new LoginSessionExtendAuthDomainEvent(authUser, ZonedDateTime.now());
  }

  public LogoutAuthDomainEvent logout(AuthUserIdCommand id) {
    AuthUser authUser = getAuthUser(id);
    authService.logout(authUser);
    authUserRepository.updateTheLastLogin(id.getAuthUserId(), ZonedDateTime.now());
    return new LogoutAuthDomainEvent(authUser, ZonedDateTime.now());
  }

  public UpdateUserAuthRoleDomainEvent
  updateCategoryAdmin(
      UpdateAuthRoleCommand command, int categoryId) {
    AuthUser actorUser = getAuthUser(new AuthUserIdCommand(command.getActorId()));
    AuthUser targetUser = getAuthUser(new AuthUserIdCommand(command.getTargetId()));

    authService.updateCategoryAdmin(actorUser,targetUser,String.valueOf(categoryId));

    targetUser.updateAdminAuthorization(actorUser.getUserRole(), actorUser.getDescription(),
        targetUser.getUserRole(),
        String.valueOf(categoryId));
    authUserRepository.updateRole(targetUser.getId(), command.getTargetRole());
    return new UpdateUserAuthRoleDomainEvent(targetUser, ZonedDateTime.now());
  }

  public UpdateUserAuthRoleDomainEvent updateRole(UpdateAuthRoleCommand command) {
    AuthUser actorUser = getAuthUser(new AuthUserIdCommand(command.getActorId()));
    AuthUser targetUser = getAuthUser(new AuthUserIdCommand(command.getTargetId()));

    targetUser.updateAdminAuthorization(actorUser.getUserRole(), actorUser.getDescription(),
        targetUser.getUserRole(),
        targetUser.getDescription());
    authUserRepository.updateRole(targetUser.getId(), command.getTargetRole());

    return new UpdateUserAuthRoleDomainEvent(targetUser, ZonedDateTime.now());
  }

}
