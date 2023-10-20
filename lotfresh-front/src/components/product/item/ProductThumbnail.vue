<template>
  <div class="thumb">
    <img :src="props.product_thumbnail" alt="상품 대표 이미지" class="bg" />
  </div>
</template>

<script setup lang="ts">
const props = defineProps(["product_thumbnail"]);
</script>

<style scoped>
.thumb {
  width: 24vw;
  height: 48vh;
}
.thumb .bg {
  width: inherit;
  height: inherit;
}
</style>
