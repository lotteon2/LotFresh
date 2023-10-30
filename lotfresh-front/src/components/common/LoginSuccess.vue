<template>
  <div></div>
</template>

<script setup lang="ts">
import router from "@/router";
import { onBeforeMount } from "vue";
import { useRoute } from "vue-router";
import { useMemberStore } from "@/stores/member";
const route = useRoute();
const memberStore = useMemberStore();
onBeforeMount(async () => {
  await router.isReady();

  const accessToken = <string>route.query.accessToken;

  if (accessToken) {
    await memberStore.setAccessToken(accessToken);
    console.log("토큰찍어봅니다...", accessToken);
  }
  router.push("/");
});
</script>

<style></style>
