<template>
  <div class="content" v-if="product">
    <div class="wrap">
      <product-thumbnail :product_thumbnail="product.thumbnail" />
      <product-detail :product="product" />
    </div>
    <div class="detail_image">
      <product-detail-image :product_detail_img="product.detail" />
    </div>
  </div>
</template>

<script setup lang="ts">
import ProductThumbnail from "@/components/product/item/ProductThumbnail.vue";
import ProductDetail from "@/components/product/item/ProductDetail.vue";
import ProductDetailImage from "@/components/product/item/ProductDetailImage.vue";
import { getProductDetail } from "@/api/product/product";
import { ref, watchEffect } from "vue";
import { useRoute } from "vue-router";
import { storeToRefs } from "pinia";
import { useProductStore } from "@/stores/product";
import type { ProductResponse } from "@/interface/productInterface";

const route = useRoute();
const product = ref<ProductResponse>();

const { province } = <any>storeToRefs(useProductStore());
const callApi = (id: any, province: string) => {
  getProductDetail(id, province).then((data) => {
    product.value = data;
  });
};

watchEffect(() => {
  callApi(route.params.id, province.value), route.params.id;
});
</script>

<style scoped>
.wrap {
  padding-top: 100px;
  display: flex;
  gap: 2.5vw;
  justify-content: start;
  /* height: 48vh; */
}
</style>
