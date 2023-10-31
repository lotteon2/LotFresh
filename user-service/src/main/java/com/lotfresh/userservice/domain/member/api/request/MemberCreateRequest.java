package com.lotfresh.userservice.domain.member.api.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MemberCreateRequest {
    private String province;
    private String roadAddress;
    private String addressDetail;
}
