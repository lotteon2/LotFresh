import { ref, watchEffect } from "vue";
import { defineStore } from "pinia";

export const useMemberStore = defineStore("member", () => {
  const privince = ref<string | null | undefined>("Seoul");

  if (localStorage.getItem("privince")) {
    privince.value = JSON.parse(<string>localStorage.getItem("privince"));
  }

  watchEffect(() => {
    localStorage.setItem("privince", JSON.stringify(privince.value));
  }, <any>privince.value);

  return { privince };
});
