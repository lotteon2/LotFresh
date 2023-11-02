<template>
  <div class="content" v-if="product">
    <div class="wrap">
      <product-thumbnail :product_thumbnail="product.thumbnail" />
      <product-detail :product="product" :isBargain="true" />
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
import { getSalesProductDetail } from "@/api/product/product";
import { ref, watchEffect } from "vue";
import { useRoute } from "vue-router";
import { storeToRefs } from "pinia";
import type { ProductResponse } from "@/interface/productInterface";
import { useMemberStore } from "@/stores/member";

const route = useRoute();
const product = ref<ProductResponse>();

const { memberInfo } = storeToRefs(useMemberStore());

const callApi = (id: any, province: string | null | undefined) => {
  getSalesProductDetail(id, province).then((data) => {
    product.value = data;
  });
};

watchEffect(() => {
  callApi(route.params.id, memberInfo.value.province), route.params.id;
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
