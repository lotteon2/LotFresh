<template>
  <div class="SectionTitle st1">
    <div class="title">
      <!-- <span class="title_span">신상품</span> -->
    </div>
    <div class="content">
      <category-product-list />
    </div>
  </div>
</template>

<script setup lang="ts">
import CategoryProductList from "@/components/product/CategoryProductList.vue";
</script>

<style scoped>
.title {
  padding-top: 50px;
  padding-bottom: 50px;
}
.title_span {
  color: rgb(51, 51, 51);
  font-size: 23px;
  line-height: 1.15;
  letter-spacing: -0.26px;
  font-weight: 600;
  margin-bottom: 30px;
}

.st1 {
  margin-bottom: 27px;
  display: flex;
  flex-direction: column;
  -webkit-box-pack: center;
  justify-content: center;
  -webkit-box-align: center;
  align-items: center;
}

.SectionTitle {
  /* width: 1050px; */
  margin: auto;
}
</style>
