package com.bit.lotte.fresh.auth.entity;

import com.bit.lotte.fresh.user.common.entity.AggregateRoot;
import com.bit.lotte.fresh.user.common.valueobject.AuthUserId;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public  class AuthUserAbstract extends AggregateRoot<AuthUserId> {


}



