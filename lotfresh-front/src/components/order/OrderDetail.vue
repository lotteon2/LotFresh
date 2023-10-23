<template>
  <div v-if="orderDetailData" class="order-detail-container">
    <div class="left-section">
      <div class="left-inner-status">
        <div class="product-status bold-text">{{ orderDetailData.status }}</div>
      </div>
      <div class="left-inner-contents">
        <div class="product-thumbnail">
          <img
            class="image"
            src="https://img-cf.kurly.com/cdn-cgi/image/width=360,height=468,fit=crop,quality=85/shop/data/goods/1653037895339l0.jpeg"
          />
          {{ orderDetailData.productThumbnail }}
        </div>
        <div class="product-name">{{ orderDetailData.productName }}</div>
        <div class="product-price small-text">
          {{ orderDetailData.price }}원
          <span class="gray-text mid-bar">|</span>
          {{ orderDetailData.stock }}개
        </div>
        <div class="cart-button"><button>장바구니 담기</button></div>
      </div>
      <div class="left-inner-empty"></div>
    </div>
    <div class="right-section">
      <div
        v-if="statusClassification(orderDetailData.status) == 'RefundStatus'"
      >
        <div class="right-section-buttons">
          <div class="right-inner-button"><button>환불</button></div>
          <div class="right-inner-button"><button>환불환불</button></div>
        </div>
      </div>
      <div
        v-else-if="
          statusClassification(orderDetailData.status) == 'PaymentStatus'
        "
      >
        <div class="right-section-buttons">
          <div class="right-inner-button"><button>결제</button></div>
          <div class="right-inner-button"><button>결제결제</button></div>
        </div>
      </div>
      <div
        v-else-if="
          statusClassification(orderDetailData.status) == 'DeliveryStatus'
        "
      >
        <div class="right-section-buttons">
          <div class="right-inner-button"><button>배송 조회</button></div>
          <div class="right-inner-button"><button>주문취소/환불</button></div>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
interface OrderDetail {
  orderDetailId: number;
  price: number;
  stock: number;
  status: string;
  productName: string;
  productThumbnail: string;
}

export default {
  props: {
    orderDetailData: {
      type: Object as () => OrderDetail,
      required: true,
    },
  },
  setup(props) {
    return {};
  },
  methods: {
    statusClassification(status: string): string {
      enum RefundStatus {
        "환불 승인 대기중",
        "환불 완료",
        "환불 거절",
      }
      enum PaymentStatus {
        "결제 대기",
        "결제 취소",
        "결제 실패",
        "결제 완료",
      }
      enum DeliveryStatus {
        "배송 준비",
        "배송 중",
        "배송 완료",
      }
      if (Object.values(RefundStatus).includes(status)) return "RefundStatus";
      else if (Object.values(PaymentStatus).includes(status))
        return "PaymentStatus";
      else {
        return "DeliveryStatus";
      }
    },
  },
};
</script>

<style scoped>
.order-detail-container {
  border: 2px solid rgb(204, 194, 194);
  border-radius: 20px;
  width: 93%;
  height: 300px;
  margin-left: auto;
  margin-right: auto;
  margin-bottom: 15px;
}

.left-section {
  float: left;
  width: 80%;
  height: 100%;
  border-right: 2px solid rgb(204, 194, 194);
}
.left-inner-status {
  height: 80px;
  position: relative;
  font-size: 20px;
}
.left-inner-contents {
  height: 170px;
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
  width: 120px;
  height: 130px;
  position: absolute;
  background-color: green;
  left: 30px;
  top: 5px;
}
.product-name {
  position: absolute;
  left: 180px;
  bottom: 75px;
  font-size: 50px;
}
.product-price {
  position: absolute;
  left: 180px;
  bottom: 40px;
}
.cart-button {
  position: absolute;
  right: 30px;
  bottom: 40px;
}

.right-section {
  float: left;
  width: 19%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
}

.right-inner-button {
  padding-top: 7px;
  padding-bottom: 7px;
  text-align: center;
}

.gray-text {
  color: gray;
}
.small-text {
  font-size: 20px;
}
.mid-bar {
  font-size: 15px;
}

.bold-text {
  font-weight: bold;
}

.image {
  width: 100%;
  height: 100%;
  object-fit: cover;
}
</style>
