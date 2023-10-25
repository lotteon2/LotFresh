<template>
  <div>
    <Header class="header_width" />
    <div v-if="$route.path == '/'" class="banners">
      <banner-slide />
    </div>
    <div class="outter_flex_box">
      <div class="left_side">나는 왼쪽 광고야</div>
      <RouterView class="body_content" />
      <div class="right_side">나는 오른쪽 최근본상품이야</div>
    </div>
    <Footer class="body_width" />
  </div>
</template>

<script setup lang="ts">
import { RouterLink, RouterView } from "vue-router";
import Header from "./components/common/Header.vue";
import BannerSlide from "@/components/banner/BannerSlide.vue";
import Footer from "./components/common/Footer.vue";
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
  padding-top: 0.5vh;
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
