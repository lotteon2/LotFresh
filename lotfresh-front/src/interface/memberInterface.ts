export interface MemberInfo {
  email: string;
  nickname: string;
  isActive: boolean;
}

export interface CreateMemberDto {
  roadAddress: string;
  province: string;
  zoncode: string;
  addressDetail: string | null | undefined;
}
