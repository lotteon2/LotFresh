<template>
  <div class="container" v-if="products.length != 0">
    <product-item
      v-for="(product, index) in products"
      :key="index"
      :product="product"
      :componentHeight="componentHeight"
    />
  </div>
  <div class="pagination">
    <el-pagination
      :page-size="20"
      :pager-count="11"
      layout="prev, pager, next"
      :total="totalPage * totalElements"
      v-model:currentPage="page"
    />
  </div>
</template>

<script setup lang="ts">
import ProductItem from "@/components/product/item/ProductItem.vue";
import { ref, watchEffect, onBeforeMount } from "vue";
import { useRoute } from "vue-router";
import { getProductsBySearch } from "@/api/product/product";
import type {
  ProductPageResponse,
  ProductResponse,
} from "@/interface/productInterface";

const products = ref<ProductResponse[]>([]);
const page = ref(1);
const route = useRoute();
const componentHeight = ref("300px");
const totalPage = ref();
const totalElements = ref();

const callApi = (page: number, keyword: any) => {
  getProductsBySearch(page, keyword).then((data) => {
    products.value = data.products;
    totalPage.value = data.totalPage;
    totalElements.value = data.totalElements;
  });
};

callApi(page.value, route.query.keyword);

watchEffect(() => {
  callApi(page.value, route.query.keyword), page.value, route.query.keyword;
});
</script>

<style scoped>
.container {
  display: flex;
  flex-wrap: wrap;
  gap: 39px;
  padding-left: 55px;
}

.el-pagination {
  display: flex;
  justify-content: center;
  padding-left: 55px;
}
</style>
