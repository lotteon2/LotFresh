import { ref, watchEffect } from "vue";
import { defineStore } from "pinia";

export const useMemberStore = defineStore("member", () => {
  const province = ref<string | null | undefined>("Seoul");

  if (localStorage.getItem("province")) {
    province.value = JSON.parse(<string>localStorage.getItem("province"));
  }

  watchEffect(() => {
    localStorage.setItem("province", JSON.stringify(province.value));
  }, <any>province.value);

  return { province };
});
