package com.bit.lotte.fresh.auth.service.dto.response;

import com.bit.lotte.fresh.auth.entity.AuthUser;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GetAdminInfoListResponse {
  List<AuthUser> authUserList;
}
