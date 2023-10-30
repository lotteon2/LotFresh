import { ref, watchEffect } from "vue";
import { defineStore } from "pinia";

export const useMemberStore = defineStore("member", () => {
  const province = ref<string | null | undefined>("Seoul");
  const accessToken = ref<string | null | undefined>("");
  if (localStorage.getItem("province")) {
    province.value = JSON.parse(<string>localStorage.getItem("province"));
  }

  if (localStorage.getItem("accessToken")) {
    accessToken.value = localStorage.getItem("accessToken");
  }

  const setAccessToken = (accesstoken: string) => {
    accessToken.value = accesstoken;
  };

  watchEffect(() => {
    localStorage.setItem("province", JSON.stringify(province.value));
  }, <any>province.value);

  watchEffect(() => {
    localStorage.setItem("accessToken", <string>accessToken.value);
  }, <any>accessToken);

  return { province, setAccessToken };
});
