<template>
  <div>
    <div class="SectionTitle css-2u0lrw e1py8bme2">
      <div class="css-7xc07p e6oc3j93">
        <span class="css-195c6n4 e1py8bme1">이 상품 어때요?</span>
      </div>
      <p class="css-1efm9d2 e1py8bme0"></p>
    </div>
    <div class="slider">
      <product-list v-for="(product, index) in products" :key="index" />
    </div>
  </div>
</template>

<script setup lang="ts">
import { useProductStore } from "@/stores/product";
import ProductList from "../components/product/ProductList.vue";
import { defaultInstance } from "@/api/utils";
import { ref } from "vue";

const products = ref([]);

const callApi = () => {
  defaultInstance
    .get(`/products/best-products`)
    .then((response) => {
      products.value = response.data;
    })
    .catch((error) => {
      console.log(error);
    });
};

callApi();
</script>

<style scoped>
.slick-list {
  width: 1074px;
}

img {
  margin: 0;
}

.boxbox {
  margin-bottom: 100px;
}

.slider .im img {
  width: 310px;
  max-width: 100%;
  height: 435px;
}

.slider {
  width: 1070px;
  margin: 0px auto;
}

.slider .slick-slide {
  height: auto;
  margin: 10px;
}

.slick-prev:before,
.slick-next:before {
  color: #444444;
}

.slick-prev,
.slick-next {
  top: 40%;
}

.css-2u0lrw {
  margin-bottom: 27px;
  display: flex;
  flex-direction: column;
  -webkit-box-pack: center;
  justify-content: center;
  -webkit-box-align: center;
  align-items: center;
}

.css-7xc07p {
  display: flex;
  -webkit-box-align: center;
  align-items: center;
  padding: 8px;
}

.css-195c6n4 {
  color: rgb(51, 51, 51);
  font-size: 28px;
  line-height: 1.15;
  letter-spacing: -0.26px;
  font-weight: 600;
}

.css-1efm9d2 {
  font-size: 16px;
  font-weight: normal;
  line-height: 1.45;
  letter-spacing: -0.2px;
  text-align: center;
  color: rgb(153, 153, 153);
  margin-top: 2px;
}

.SectionTitle {
  width: 1050px;
  margin: auto;
  padding-top: 10px;
}

/* 음 */
</style>
