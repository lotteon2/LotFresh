<template>
  <div class="item">
    <div class="thumb">
      <img class="product-img" :id="product.id" :src="product.thumbnail" />
      <div class="group-btn">
        <input type="hidden" class="pid" value="${m.productNo}" />
        <button
          id="${m.productNo}"
          type="button"
          class="btn cart-btn"
          onclick="bucksubmit(this)"
        ></button>
      </div>
    </div>
    <div class="info">
      <span class="name">{{ product.name }}</span>
      <span class="cost">
        <span v-if="!product.salesPrice" class="price"
          >{{ product.price }}
        </span>
        <span v-else>
          <span style="text-decoration: line-through; color: #999999"
            >{{ product.price }}
          </span>
          →{{ product.salesPrice }}
        </span>
      </span>
      <!-- <span class="simple-content">{{ product.detail }}</span> -->
    </div>
  </div>
</template>

<script setup lang="ts">
const props = defineProps(["product", "index"]);
</script>

<style scoped>
img {
  margin: 0;
}

/* 음 */

.thumb {
  display: block;
  position: relative;
  width: 100%;
  height: 300px;
  background-color: #f9f8f9;
}

.thumb .img {
  overflow: hidden;
  position: absolute;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
}

.product-img {
  height: 300px;
}

.thumb img {
  width: 100%;
  vertical-align: top;
  transition: all 0.5s ease-in-out;
}

.thumb .btn {
  display: block;
  width: 45px;
  height: 45px;
  margin-top: 15px;
  border: 0;
}

.thumb .cart-btn {
  background: url("../../../assets/cart-btn.png") no-repeat 40% 50%;
  background-size: 40px 40px;
}

.thumb .group-btn {
  position: absolute;
  z-index: 2;
  right: 15px;
  bottom: 15px;
  width: 45px;
}

.group-btn {
  overflow: hidden;
  /*width: 100%;*/
  text-align: center;
}

.group-btn .btn {
  overflow: hidden;
  float: left;
  width: 45px;
  height: 45px;
  font-size: 13px;
  line-height: 40px;
  text-align: center;
}

button {
  outline: none;
  cursor: pointer;
}

.info {
  display: block;
  padding: 14px 10px 10px 0;
}

.info h2 {
  padding: 0;
  margin-bottom: 5px;
  margin-top: 0;
}

.name {
  /*display: block;*/
  overflow: hidden;
  max-height: 58px;
  font-weight: 400;
  font-size: 20px;
  color: #333;
  line-height: 29px;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  word-wrap: break-word;
}

.cost {
  display: block;
  padding-top: 7px;
  font-size: 18px;
  line-height: 29px;
}

.cost .price {
  font-weight: 800;
  color: #333;
}

/* .simple-content {
  display: block;
  padding-top: 11px;
  font-size: 13px;
  color: #999;
  line-height: 19px;
} */
</style>
