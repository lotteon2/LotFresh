<template>
  <div v-if="banners.length != 0" class="banner">
    <carousel :items-to-show="1" :autoplay="3500" :transition="1500">
      <slide v-for="(banner, index) in banners" :key="index">
        <banner :banner="banner" />
      </slide>
      <template #addons>
        <navigation />
      </template>
    </carousel>
  </div>
</template>

<script setup lang="ts">
import Banner from "./BannerItem.vue";
import "vue3-carousel/dist/carousel.css";
import { Carousel, Slide, Navigation } from "vue3-carousel";
import { defaultInstance, productInstance } from "@/api/utils";
import { ref } from "vue";

const banners = ref([]);

const callApi = () => {
  productInstance
    .get(`/discounts`)
    .then((response) => {
      banners.value = response.data;
    })
    .catch((error) => {
      console.log(error);
    });
};

callApi();
</script>

<style scoped></style>
