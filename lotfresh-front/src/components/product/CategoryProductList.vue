<template v-if="categoryProducts.length != 0">
  <div class="container">
    <div v-for="(product, index) in categoryProducts" :key="index">
      <product-item
        :key="index"
        :product="product"
        :componentHeight="componentHeight"
      />
    </div>
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
import { getCategoryProducts } from "@/api/product/product";
import { ref, watchEffect } from "vue";
import { useRoute } from "vue-router";
import type {
  ProductPageResponse,
  ProductResponse,
} from "@/interface/productInterface";

const categoryProducts = ref<ProductResponse[]>([]);
const totalPage = ref();
const totalElements = ref();
const page = ref(1);
const route = useRoute();
const componentHeight = ref("300px");

const callApi = (categoryId: any, page: number) => {
  getCategoryProducts(categoryId, page).then((data) => {
    categoryProducts.value = data.products;
    totalPage.value = data.totalPage;
    totalElements.value = data.totalElements;
  });
};

callApi(route.params.id, page.value);

watchEffect(() => {
  callApi(route.params.id, page.value), page.value, route.params.id;
});
</script>

<style scoped>
/* .content {
  display: flex;
  flex-wrap: wrap;
  gap: 39px;
} */

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
