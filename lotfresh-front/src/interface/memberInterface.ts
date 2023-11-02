export interface MemberInfo {
  email: string;
  nickname: string;
  isActive: boolean;
  province: string | null;
  zipCode: string | null;
  roadAddress: string | null;
  detailAddress: string | null;
}

export interface CreateMemberDto {
  roadAddress: string;
  province: string;
  zipCode: string;
  addressDetail: string | null | undefined;
}
