<template>
  <div>
    <Header @openModal="() => (isOpenModal = true)" class="header_width" />
    <div v-if="$route.path == '/'" class="banners">
      <banner-slide />
    </div>
    <div class="outter_flex_box">
      <div class="left_side"></div>
      <RouterView
        @openModal="() => (isOpenModal = true)"
        class="body_content"
      />
      <banner-right class="right_side" />
    </div>
    <modal-frame
      @closeModal="() => (isOpenModal = false)"
      v-if="isOpenModal"
      v-model:isOpenModal="isOpenModal"
    />
    <Footer class="body_width" />
  </div>
</template>

<script setup lang="ts">
import { RouterLink, RouterView } from "vue-router";
import { ref } from "vue";
import Header from "./components/common/Header.vue";
import BannerSlide from "@/components/banner/BannerSlide.vue";
import Footer from "./components/common/Footer.vue";
import BannerRight from "./components/banner/BannerRight.vue";
//
const isOpenModal = ref(false);
</script>

<style scored>
body {
  overflow-x: hidden;
}
.header_width {
  width: 100vw;
  margin: auto;
}
.banner_width {
  width: 100%;
}

.outter_flex_box {
  width: 100vw;
  display: flex;
  flex-direction: row;
  justify-content: center;
}

.banners {
  width: 100%;
}

.body_content {
  width: 65vw;
}

.left_side {
  width: 17.5vw;
}

.right_side {
  width: 17.5vw;
}

.display_none {
  display: none;
}
</style>
