package com.bit.lotte.fresh.auth.entity;

import com.bit.lotte.fresh.auth.exception.AlreadyVerifiedAuthDomainException;
import com.bit.lotte.fresh.auth.exception.AuthUserDomainException;
import com.bit.lotte.fresh.auth.exception.AuthenticationDomainException;
import com.bit.lotte.fresh.auth.exception.AuthorizationAuthDomainException;
import com.bit.lotte.fresh.auth.exception.LoginFailedAuthDomainException;
import com.bit.lotte.fresh.auth.exception.LoginTimeExpiredException;
import com.bit.lotte.fresh.auth.valueobject.AuthProvider;
import com.bit.lotte.fresh.auth.valueobject.AuthRole;
import com.bit.lotte.fresh.user.common.entity.AggregateRoot;
import com.bit.lotte.fresh.user.common.valueobject.AuthUserId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public  class AuthUserAbstract extends AggregateRoot<AuthUserId> {


}



