<template>
  <div v-if="orderDetailData" class="order-detail-container">
    <div class="left-section">
      <div class="left-inner-status">
        <div class="product-status bold-text">{{ orderDetailData.status }}</div>
      </div>
      <div class="left-inner-contents">
        <div class="product-thumbnail">
          <img class="image" :src="orderDetailData.productThumbnail" />
        </div>
        <div class="product-name">{{ orderDetailData.productName }}</div>
        <div class="product-price small-text">
          {{ orderDetailData.price }}원
          <span class="gray-text mid-bar">|</span>
          {{ orderDetailData.stock }}개
        </div>
      </div>
      <div class="left-inner-empty"></div>
    </div>
    <div class="right-section">
      <div
        v-if="statusClassification(orderDetailData.status) == 'RefundStatus'"
      >
        <div class="right-section-buttons">
          <div class="right-inner-button">
            <button class="white-button" @click="refundModal">환불 조회</button>
          </div>
        </div>
      </div>
      <div
        v-else-if="
          statusClassification(orderDetailData.status) == 'PaymentStatus'
        "
      >
        <div class="right-section-buttons">
          <div class="right-inner-button">
            <button class="white-button" @click="paymentModal">
              결제 조회
            </button>
          </div>
        </div>
      </div>
      <div
        v-else-if="
          statusClassification(orderDetailData.status) == 'DeliveryStatus'
        "
      >
        <div class="right-section-buttons">
          <div class="right-inner-button">
            <button class="white-button" @click="cancelModal">
              주문취소/환불
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { ElMessageBox } from "element-plus";
import type {
  OrderResponse,
  OrderDetailResponse,
} from "../../interface/orderInterface";
import { getPaymentDetailInfo } from "@/api/payment/payment";
import { useMemberStore } from "@/stores/member";
import { storeToRefs } from "pinia";
export default {
  props: {
    orderDetailData: {
      type: Object as () => OrderDetailResponse,
      required: true,
    },
    orderId: {
      type: Number,
      required: true,
    },
  },
  setup(props) {
    const { accessToken } = storeToRefs(useMemberStore());
    return {
      accessToken,
    };
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
    refundModal() {
      ElMessageBox.alert("환불모달", "Title");
    },
    paymentModal() {
      getPaymentDetailInfo(this.orderId.toString(), this.accessToken).then(
        (res) => {
          console.log(res);
          ElMessageBox.alert(
            "상품 가격 : " +
              res.originalAmount +
              "원</br>" +
              "할인 가격 : " +
              res.discountedAmount +
              "원</br>" +
              "상품 개수 : " +
              res.transactionAmount +
              "개</br>" +
              "결제 방법" +
              res.paymentMethod,
            "결제 상태"
          );
        }
      );
    },
    deliveryModal() {
      ElMessageBox.alert("배송모달", "배송 상태");
    },
    cancelModal() {
      ElMessageBox.alert("취소모달", "환불 상태");
    },
  },
  mounted() {
    window.scrollTo(0, 0);
  },
};
</script>

<style scoped>
.order-detail-container {
  border: 2px solid rgb(204, 194, 194);
  border-radius: 20px;
  width: 93%;
  height: 270px;
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
  font-size: 18px;
}
.left-inner-contents {
  height: 170px;
  position: relative;
}
.left-inner-empty {
  height: 20px;
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
  bottom: 90px;
  font-size: 30px;
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
  font-size: 10px;
}

.bold-text {
  font-weight: bold;
}

.image {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.white-button {
  /* float: right; */
  width: 170px;
  height: 60px;
  border: none;
  border-radius: 3px;
  font-size: 16px;
  font-weight: 600;
  line-height: normal;
  color: #ef2a23;
  background-color: #fff;
  border: 1px solid #ef2a23;

  cursor: pointer;
}

.red-button {
  width: 170px;
  height: 60px;
  border: none;
  border-radius: 3px;
  font-size: 16px;
  font-weight: 600;
  line-height: normal;
  color: #fff;
  background-color: #ef2a23;
  border: 1px solid #ef2a23;
  cursor: pointer;
}
</style>
