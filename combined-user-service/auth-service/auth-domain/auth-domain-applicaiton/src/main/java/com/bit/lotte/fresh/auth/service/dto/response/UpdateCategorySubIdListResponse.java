package com.bit.lotte.fresh.auth.service.dto.response;

import com.bit.lotte.fresh.auth.valueobject.AuthRole;
import com.bit.lotte.fresh.user.common.valueobject.AuthUserId;

public class UpdateCategorySubIdListResponse{
    private final AuthUserId authUserId;
    private final AuthRole authRole;
    private String message;

    public UpdateCategorySubIdListResponse(AuthUserId authUserId,
        AuthRole authRole, String description) {
      this.authUserId = authUserId;
      this.authRole = authRole;
      this.getMessage(description);
    }

    void getMessage(String description) {
      StringBuilder sb = new StringBuilder();
      sb.append(authUserId);
      sb.append("님의 ");
      sb.append("의 권한이");
      sb.append(authRole);
      sb.append("과 ");
      sb.append("sub authUserId list가");
      sb.append(description);
      sb.append("로 업데이트 됐습니다.");
      this.message = sb.toString();
    }


}
