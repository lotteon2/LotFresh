<template v-if="products.length != 0">
  <div class="container">
    <div v-for="(product, index) in products" :key="index">
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
      v-model:currentPage="pageReq.page"
    />
  </div>
</template>

<script setup lang="ts">
import ProductItem from "@/components/product/item/ProductItem.vue";
import { defaultInstance } from "@/api/utils";
import { watch, ref } from "vue";
import { useRoute } from "vue-router";

const route = useRoute();
const products = ref([]);
const componentHeight = ref("300px");
const totalPage = ref();
const totalElements = ref();

const pageReq = ref({
  keyword: "",
  page: 1,
});

watch(
  () => pageReq.value.page,
  () => {
    callApi();
  }
);

watch(
  () => route.params.id,
  () => {
    callApi();
  }
);
const callApi = () => {
  defaultInstance
    .get(`/products/categories/${route.params.id}`, {
      params: {
        page: pageReq.value.page,
        keyword: pageReq.value.keyword,
      },
    })
    .then((response) => {
      products.value = response.data.products;
      totalPage.value = response.data.totalPage;
      totalElements.value = response.data.totalElements;
    })
    .catch((error) => {
      console.log(error);
    });
};

callApi();
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
