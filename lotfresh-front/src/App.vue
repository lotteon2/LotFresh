<template>
  <div>
    <Header class="body_width" />
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
import Footer from "./components/common/Footer.vue";
</script>

<style scored>
body {
  overflow-x: hidden;
}
.body_width {
  width: 65vw;
  margin: auto;
}
.banner_width {
  width: 100%;
}

.outter_flex_box {
  width: 100%;
  display: flex;
  flex-direction: row;
  justify-content: center;
}

.body_content {
  width: 50%;
  height: 100%;
}

.left_side {
  width: 25vw;
}

.right_side {
  width: 25vw;
}

.display_none {
  display: none;
}
</style>
