<template>
  <div v-if="props.product_detail_img" class="detail_img_content">
    <div class="txt_wrap"><span class="txt">상품 상세 설명</span></div>
    <img class="detail_img" :src="props.product_detail_img" />
  </div>
</template>

<script setup lang="ts">
const props = defineProps(["product_detail_img"]);
</script>

<style scoped>
.txt_wrap {
  width: inherit;
  text-align: center;
  padding-top: 15px;
  padding-bottom: 15px;
  margin-bottom: 35px;
  background-color: #ef2a23;
}
.txt {
  color: #fff;
  font-size: 20px;
}
.detail_img_content {
  width: 65vw;
  padding-top: 50px;
  padding-bottom: 50px;
}
.detail_img {
  width: inherit;
}
</style>
