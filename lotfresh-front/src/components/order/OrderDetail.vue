<template>
  <div class="order-detail-container">
    <div class="left-section">
      <div class="left-inner-status">
        <div class="product-status">{{ orderDetailData.status }}</div>
      </div>
      <div class="left-inner-contents">
        <div class="product-thumbnail">
          {{ orderDetailData.productThumbnail }}
        </div>
        <div class="product-name">{{ orderDetailData.productName }}</div>
        <div class="product-price">
          {{ orderDetailData.price }}원
          <span class="gray-text small-text">|</span>
          {{ orderDetailData.stock }}개
        </div>
        <div class="cart-button"><button>장바구니 담기</button></div>
      </div>
      <div class="left-inner-empty"></div>
    </div>
    <div class="right-section">
      <div class="right-section-buttons">
        <div class="right-inner-button"><button>배송 조회</button></div>
        <div class="right-inner-button"><button>주문 취소/환불</button></div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
export default {
  props: {
    orderDetailData: Object,
  },
};
</script>

<style scoped>
.order-detail-container {
  border: 1px solid gray;
  border-radius: 20px;
  width: 700px;
  height: 200px;
  margin-left: auto;
  margin-right: auto;
  margin-bottom: 25px;
}

.left-section {
  float: left;
  width: 549px;
  height: 200px;
  border-right: 1px solid gray;
}
.left-inner-status {
  height: 50px;
  position: relative;
}
.left-inner-contents {
  height: 100px;
  position: relative;
}
.left-inner-empty {
  height: 50px;
}
.product-status {
  position: absolute;
  left: 30px;
  top: 20px;
}
.product-thumbnail {
  width: 80px;
  height: 95px;
  position: absolute;
  background-color: green;
  left: 30px;
  top: 15px;
}
.product-name {
  position: absolute;
  left: 130px;
  bottom: 35px;
}
.product-price {
  position: absolute;
  left: 130px;
  bottom: 5px;
}
.cart-button {
  position: absolute;
  right: 30px;
  bottom: 5px;
}

.right-section {
  float: left;
  width: 149px;
  height: 200px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.right-inner-button {
  padding-top: 7px;
  padding-bottom: 7px;
}

.gray-text {
  color: gray;
}
.small-text {
  font-size: 13px;
}
</style>
