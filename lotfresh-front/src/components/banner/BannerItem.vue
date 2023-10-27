<template>
  <div v-if="props.banner" class="banner_item">
    <router-link :to="`/categories/${props.banner.categoryId}`">
      <img class="banner_img" :src="banner.imgurl" />
    </router-link>
  </div>
</template>

<script setup lang="ts">
const props = defineProps(["banner", "index"]);
</script>

<style scoped>
.banner_img {
  width: 100%;
}
</style>
