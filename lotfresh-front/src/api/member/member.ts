import { defaultInstance, memberInstance } from "../utils";
import type { MemberInfo, CreateMemberDto } from "@/interface/memberInterface";

export const getMemberDetail = async (
  accessToken: string
): Promise<MemberInfo> => {
  console.log("aaa", accessToken);
  const { data } = await memberInstance.get(`/users/me`, {
    headers: { Authorization: `Bearer ${accessToken}` },
  });
  return data;
};

export const regist = async (
  createMemberDto: CreateMemberDto,
  accessToken: string | null
): Promise<string | null | undefined> => {
  const { data } = await memberInstance.post(`/users`, createMemberDto, {
    headers: { Authorization: `Bearer ${accessToken}` },
  });
  return data;
};
