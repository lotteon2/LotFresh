import { defaultInstance, memberInstance } from "../utils";
import type { MemberInfo, CreateMemberDto } from "@/interface/memberInterface";

export const getMemberDetail = async (
  accessToken: string
): Promise<MemberInfo> => {
  const { data } = await memberInstance.get(`/users/me`, {
    headers: { Authorization: `Bearer ${accessToken}` },
  });
  return data;
};

export const regist = async (
  createMemberDto: CreateMemberDto,
  accessToken: string | null
): Promise<string | null> => {
  const { data } = await memberInstance.post(`/users`, createMemberDto, {
    headers: { Authorization: `Bearer ${accessToken}` },
  });
  return data;
};
