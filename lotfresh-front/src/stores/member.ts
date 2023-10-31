import { ref, watchEffect } from "vue";
import { defineStore } from "pinia";
import { getMemberDetail, regist } from "@/api/member/member";
import type { CreateMemberDto, MemberInfo } from "@/interface/memberInterface";

export const useMemberStore = defineStore("member", () => {
  const province = ref<string | null | undefined>("Seoul");
  const accessToken = ref<string | null>("");
  const memberInfo = ref<MemberInfo | null | undefined>();

  if (localStorage.getItem("province")) {
    province.value = JSON.parse(<string>localStorage.getItem("province"));
  }

  if (localStorage.getItem("accessToken")) {
    accessToken.value = localStorage.getItem("accessToken");
  }

  if (sessionStorage.getItem("memberInfo")) {
    memberInfo.value = <MemberInfo>(
      JSON.parse(<string>sessionStorage.getItem("memberInfo"))
    );
  }

  const setAccessToken = (accesstoken: string) => {
    accessToken.value = accesstoken;
  };

  const setMemberDetailInfo = async (accessToken: string) => {
    memberInfo.value = await getMemberDetail(accessToken);
  };

  const registMember = async (
    createMemberDto: CreateMemberDto
  ): Promise<string> => {
    return await regist(createMemberDto, accessToken.value);
  };

  watchEffect(() => {
    localStorage.setItem("province", JSON.stringify(province.value));
  }, <any>province.value);

  watchEffect(() => {
    localStorage.setItem("accessToken", <string>accessToken.value);
  }, <any>accessToken);

  watchEffect(() => {
    sessionStorage.setItem("memberInfo", JSON.stringify(memberInfo.value));
  }, <any>memberInfo);

  return {
    province,
    memberInfo,
    accessToken,
    setAccessToken,
    setMemberDetailInfo,
    registMember,
  };
});
