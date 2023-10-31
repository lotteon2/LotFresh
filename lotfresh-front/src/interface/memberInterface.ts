export interface MemberInfo {
  email: string;
  nickname: string;
  isActive: boolean;
  province: string | null | undefined;
}

export interface CreateMemberDto {
  roadAddress: string;
  province: string;
  zipCode: string;
  addressDetail: string | null | undefined;
}
