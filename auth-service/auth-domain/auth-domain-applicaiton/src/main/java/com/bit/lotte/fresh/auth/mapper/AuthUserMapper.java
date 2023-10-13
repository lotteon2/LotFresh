package com.bit.lotte.fresh.auth.mapper;

import com.bit.lotte.fresh.auth.dto.command.CreateAuthDomainCommand;
import com.bit.lotte.fresh.auth.dto.command.LoginAuthDomainCommand;
import com.bit.lotte.fresh.auth.dto.command.UpdateAuthDomainRoleCommand;
import com.bit.lotte.fresh.auth.dto.response.CreateInitAuthUserResponse;
import com.bit.lotte.fresh.auth.entity.AuthUser;
import com.bit.lotte.fresh.auth.event.CreateAuthDomainEvent;
import com.bit.lotte.fresh.auth.valueobject.AuthProvider;
import com.bit.lotte.fresh.user.common.valueobject.AuthUserId;

public class AuthUserMapper {


  AuthUser authUserIdToAuthUser(AuthUserId authUserId){

  }

  AuthUser UpdateAuthDomainRoleCommandToAuthUser(UpdateAuthDomainRoleCommand command){

  }


  public CreateInitAuthUserResponse createAuthUserDomainEventToInitAuthUserResponse(
      CreateAuthDomainEvent event) {
    AuthUser authUser =  event.getAuthUserAbstract();
    return new CreateInitAuthUserResponse(authUser.getId(),authUser.getAuthProvider());
  }

  public AuthUser createAuthDomainCommandToAuthUser(CreateAuthDomainCommand command) {
    return (AuthUser) AuthUser.builder().authProvider(command.getAuthProvider()).id(command.getAuthUserId()).build();
  }

  public AuthUser LoginAuthDomainCommandToUser(LoginAuthDomainCommand command) {
    if(command.getAuthProvider().equals(AuthProvider.NONE)){
      return (AuthUser) AuthUser.builder().authProvider(command.getAuthProvider()).id(command.getAuthUserId()).build();
    }
    return (AuthUser) AuthUser.builder().id(command.getAuthUserId()).authProvider(command.getAuthProvider()).build();
  }
}
