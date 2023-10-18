<template>
  <div>
    <Header class="body_width" />

    <!-- <div class="outter_flex_box"> -->
    <!-- <div>나는 왼쪽 광고야</div> -->
    <RouterView />
    <!-- <div>나는 오른쪽 최근본상품이야</div> -->
    <!-- </div> -->
    <Footer class="body_width" />
  </div>
</template>

<script setup lang="ts">
import { RouterLink, RouterView } from "vue-router";
import Header from "./components/common/Header.vue";
import Footer from "./components/common/Footer.vue";
</script>

<style scored>
body {
  overflow-x: hidden;
}
.body_width {
  width: 65%;
  margin: auto;
}
.banner_width {
  width: 100%;
}

/* .outter_flex_box {
  display: flex;
  flex-direction: row;
  justify-content: center;
} */

.body_content {
  width: 100%;
  height: 100%;
}
</style>
