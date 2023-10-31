import { ref, watchEffect } from "vue";
import { defineStore } from "pinia";
import { getMemberDetail, regist } from "@/api/member/member";
import type { CreateMemberDto, MemberInfo } from "@/interface/memberInterface";

export const useMemberStore = defineStore("member", () => {
  const accessToken = ref<string | null>("");
  const memberInfo = ref<MemberInfo>();

  if (localStorage.getItem("accessToken")) {
    accessToken.value = localStorage.getItem("accessToken");
  }

  if (sessionStorage.getItem("memberInfo") !== undefined) {
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
  ): Promise<void> => {
    if (memberInfo.value) {
      memberInfo.value.province = await regist(
        createMemberDto,
        accessToken.value
      );
    }
  };

  watchEffect(() => {
    localStorage.setItem("accessToken", <string>accessToken.value);
  }, <any>accessToken);

  watchEffect(() => {
    sessionStorage.setItem("memberInfo", JSON.stringify(memberInfo.value));
  }, <any>memberInfo);

  return {
    memberInfo,
    accessToken,
    setAccessToken,
    setMemberDetailInfo,
    registMember,
  };
});
