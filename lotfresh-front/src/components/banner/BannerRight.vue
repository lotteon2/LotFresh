<template>
  <div class="quick_banner">
    <div class="emptyBox">
      <div class="container_box">
        <button
          type="button"
          @click="moveSlide(-1)"
          :disabled="currentSlide === 0"
          class="top_button"
        >
          <svg
            width="20"
            height="20"
            viewBox="0 0 18 18"
            fill="none"
            stroke="#add"
            xmlns="http://www.w3.org/2000/svg"
          >
            <path d="M5 11L9 7L13 11" stroke="#add" stroke-width="1.3"></path>
          </svg>
        </button>
        <div class="title">최근 본 상품</div>
        <div class="wrap_content">
          <ul v-if="recentProducts.length != 0" class="content_ul">
            <li
              v-for="(product, index) in recentProducts"
              :key="index"
              :style="{
                transform: `translateY(${(index - currentSlide) * 35}%)`,
              }"
            >
              <router-link :to="`/goods/${product.id}`">
                <span
                  style="
                    box-sizing: border-box;
                    display: inline-block;
                    overflow: hidden;
                    width: initial;
                    height: initial;
                    background: none;
                    opacity: 1;
                    border: 0px;
                    margin: 0px;
                    padding: 0px;
                    position: relative;
                    max-width: 100%;
                  "
                >
                  <span
                    style="
                      box-sizing: border-box;
                      display: block;
                      width: initial;
                      height: initial;
                      background: none;
                      opacity: 1;
                      border: 0px;
                      margin: 0px;
                      padding: 0px;
                      max-width: 100%;
                    "
                  >
                    <img
                      aria-hidden="true"
                      src="data:image/svg+xml,%3csvg%20xmlns=%27http://www.w3.org/2000/svg%27%20version=%271.1%27%20width=%2760%27%20height=%2780%27/%3e"
                      style="
                        display: block;
                        max-width: 100%;
                        width: initial;
                        height: initial;
                        background: none;
                        opacity: 1;
                        border: 0px;
                        margin: 0px;
                        padding: 0px;
                      "
                    />
                  </span>
                  <img
                    alt="recent-product"
                    :src="`${product.imgurl}`"
                    style="
                      position: absolute;
                      inset: 0px;
                      box-sizing: border-box;
                      padding: 0px;
                      border: none;
                      margin: auto;
                      display: block;
                      width: 0px;
                      height: 0px;
                      min-width: 100%;
                      max-width: 100%;
                      min-height: 100%;
                      max-height: 100%;
                      object-fit: cover;
                    "
                  />
                </span>
              </router-link>
            </li>
          </ul>
        </div>
        <button
          type="button"
          @click="moveSlide(1)"
          :disabled="currentSlide === recentProducts.length - 1"
          class="bottom_button"
        >
          <svg
            width="20"
            height="20"
            viewBox="0 0 18 18"
            fill="none"
            xmlns="http://www.w3.org/2000/svg"
          >
            <path d="M13 7L9 11L5 7" stroke="#666" stroke-width="1.3"></path>
          </svg>
        </button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from "vue";

const recentProducts = ref([]);
const currentSlide = ref(0);

const moveSlide = (direction: any) => {
  const totalSlides = recentProducts.value.length;
  currentSlide.value =
    (currentSlide.value + direction + totalSlides) % totalSlides;
};

const call = () => {
  recentProducts.value = JSON.parse(
    localStorage.getItem("recentProducts") || "[]"
  );
  console.log(recentProducts.value);
};

call();
</script>

<style scoped>
.quick_banner {
  display: flex;
  justify-content: center;
}

.title {
  font-family: "Noto Sans", "malgun gothic", AppleGothic, dotum, sans-serif;
  text-align: center;
  font-size: 14px;
  padding-bottom: 3px;
}
.emptybox {
  margin-top: 8px;
  border: 1px solid rgb(221, 221, 221);
  background-color: rgb(255, 255, 255);
  text-align: center;
  font-weight: 500;
}

.container_box {
  width: 90%;
  border: 1px solid rgb(221, 221, 221);
}

.top_button,
.bottom_button {
  cursor: default;
  width: 100%;
  background-color: white;
  border: none;
}

.wrap_content {
  max-height: 209px;
  overflow: hidden;
  margin-top: 6px;
  float: left;
}

.content_ul {
  position: relative;
  top: 0px;
  right: 8%;
  display: flex;
  flex-direction: column;
  -webkit-box-align: center;
  align-items: center;
  transition: top 0.2s ease 0s;
  list-style: none;
}

.recent_img {
  display: block;
  height: 80px;
  margin: 2px 0px;
}

svg:not(:root) {
  overflow: hidden;
}
</style>
