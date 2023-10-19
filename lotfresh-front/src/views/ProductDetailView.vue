<template>
  <div>
    <div class="wrap">
      <product-thumbnail />
      <product-detail />
    </div>
    <div class="detail_image">
      <product-detail-image />
    </div>
  </div>
</template>

<script setup lang="ts">
import ProductThumbnail from "@/components/product/item/ProductThumbnail.vue";
import ProductDetail from "@/components/product/item/ProductDetail.vue";
import ProductDetailImage from "@/components/product/item/ProductDetailImage.vue";
</script>

<style></style>
