<template>
  <div v-if="banner" class="banner_item">
    <img class="banner_img" :src="banner.imgurl" />
  </div>
</template>

<script setup lang="ts">
const props = defineProps(["banner", "index"]);
</script>

<style scoped>
.banner_img {
  width: 110%;
}
</style>
