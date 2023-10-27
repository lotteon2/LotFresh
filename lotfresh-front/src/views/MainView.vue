<template>
  <div>
    <div class="SectionTitle css-2u0lrw e1py8bme2">
      <div class="css-7xc07p e6oc3j93">
        <span class="css-195c6n4 e1py8bme1">이 상품 어때요?</span>
      </div>
      <p class="css-1efm9d2 e1py8bme0"></p>
      <div class="slider">
        <new-product-list-slide />
      </div>
      <div class="css-7xc07p e6oc3j93">
        <span class="css-195c6n4 e1py8bme1">인기 상품 추천</span>
      </div>
      <p class="css-1efm9d2 e1py8bme0"></p>
      <div class="slider">
        <best-product-list-slide />
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import BestProductListSlide from "@/components/product/BestProductListSlide.vue";
import NewProductListSlide from "@/components/product/NewProductListSlide.vue";
</script>

<style scoped>
img {
  margin: 0;
}
.slider {
  width: 100%;
  margin: 20px auto;
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
  padding: 15px;
}

.css-195c6n4 {
  color: rgb(51, 51, 51);
  font-size: 23px;
  line-height: 1.15;
  letter-spacing: -0.26px;
  font-weight: 600;
  margin-bottom: 30px;
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
  /* width: 1050px; */
  margin: auto;
  padding-top: 10px;
}
</style>

<style>
.banners .carousel__prev,
.banners .carousel__next {
  border: solid;
  border-radius: 15px;
}

.banners .carousel__viewport {
  overflow: visible;
}

.slider .carousel__prev,
.slider .carousel__next {
  margin-top: -70px;
  border: solid;
  border-radius: 15px;
}

.slider .carousel__viewport {
  padding-bottom: 50px;
}

.carousel__slide {
  align-items: baseline;
}
</style>
