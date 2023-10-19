<template>
  <div class="product-detail">
    <p class="goods-name">
      <strong class="name">{{ props.product.name }}</strong>
      <span class="simple-content">신선식품 롯프레쉬</span>
    </p>
    <p class="goods-price">
      <span class="position">
        <span class="price">
          <span id="basic-price">
            {{ props.product.price }}
          </span>
          <span class="won">원</span>
        </span>
      </span>
    </p>
  </div>
</template>

<script setup lang="ts">
const props = defineProps(["product"]);
</script>

<style scoped>
.product-detail .goods-name {
  width: 560px;
  padding: 10px 0 29px;
}

.product-detail .goods-name .name {
  display: block;
  padding-right: 60px;
  font-weight: 700;
  font-size: 24px;
  line-height: 34px;
  word-break: break-all;
}

.product-detail .goods-name .simple-content {
  display: block;
  padding: 4px 60px 0 0;
  font-size: 14px;
  color: #999;
  line-height: 20px;
  word-break: break-all;
}

.product-detail .goods-price {
  width: 560px;
  padding: 0 20px 0 0;
  border-bottom: 1px solid #e2e2e2;
  padding-bottom: 5px;
  margin-bottom: 5px;
}

.inner-view .goods-price .price {
  font-weight: 700;
  font-size: 28px;
  line-height: 30px;
  letter-spacing: 0;
  word-break: break-all;
}

.product-detail .goods-price .won {
  padding: 0 7px 0 2px;
  font-weight: 700;
  font-size: 18px;
  line-height: 30px;
  vertical-align: 2px;
  letter-spacing: 0;
}
</style>
