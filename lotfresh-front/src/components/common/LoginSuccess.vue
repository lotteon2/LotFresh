<template>
  <div></div>
</template>

<script setup lang="ts">
import router from "@/router";
import { onBeforeMount } from "vue";
import { useRoute } from "vue-router";
import { useMemberStore } from "@/stores/member";
import { storeToRefs } from "pinia";
const route = useRoute();
const memberStore = useMemberStore();
const { memberInfo } = storeToRefs(memberStore);
onBeforeMount(async () => {
  await router.isReady();

  const accessToken = <string>route.query.accessToken;

  if (accessToken) {
    await memberStore.setAccessToken(accessToken);
    await memberStore.setMemberDetailInfo(accessToken);
    if (memberInfo.value?.isActive) {
      router.push("/");
    } else {
      router.push("/signup");
    }
  }
});
</script>

<style></style>
