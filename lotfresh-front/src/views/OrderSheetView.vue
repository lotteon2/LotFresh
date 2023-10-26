<template>
  <div>
    <div class="pageTitle">
      <div class="title">
        <h2>주문서</h2>
      </div>
    </div>
    <order-product />
    <orderer-info />
    <delivery-info @openAddressModal="openAddressModal" />
    <payment-bill />
    <KakaopayButton @kakaopay_button_click="handlePayment"></KakaopayButton>
    <KakaoAddressFinderModal
      v-model:isAddressModalOpen="isAddressModalOpen"
    ></KakaoAddressFinderModal>
  </div>
</template>

<script lang="ts">
import DeliveryInfo from "../components/order/orderSheet/DeliveryInfo.vue";
import OrdererInfo from "../components/order/orderSheet/OrdererInfo.vue";
import OrderProduct from "../components/order/orderSheet/OrderProduct.vue";
import PaymentBill from "../components/order/orderSheet/PaymentBill.vue";
import KakaopayButton from "../components/order/orderSheet/KakaopayButton.vue";
import KakaoAddressFinderModal from "../components/order/orderSheet/KakaoAddressFinderModal.vue";
import type { OrderCreateRequest } from "../api/order/order";
import { startKakaopay } from "../api/order/order";

export default {
  components: {
    DeliveryInfo,
    OrdererInfo,
    OrderProduct,
    PaymentBill,
    KakaopayButton,
    KakaoAddressFinderModal,
  },

  data() {
    return {
      isAddressModalOpen: false,
    };
  },

  methods: {
    openAddressModal: function (): void {
      console.log("openModal on OrderSheet" + this.isAddressModalOpen);
      this.isAddressModalOpen = true;
      console.log("openModal on OrderSheet" + this.isAddressModalOpen);
    },
  },

  setup() {
    const handlePayment = async () => {
      try {
        const orderData: OrderCreateRequest = {
          productRequests: [
            {
              productId: 1,
              productPrice: 10000,
              productStock: 50,
              productName: "Product Name",
              productThumbnail: "https://example.com/product-thumbnail.jpg",
            },
            {
              productId: 2,
              productPrice: 20000,
              productStock: 30,
              productName: "Another Product Name",
              productThumbnail:
                "https://example.com/another-product-thumbnail.jpg",
            },
            // 필요한 만큼 추가적인 ProductRequest 객체를 포함시킬 수 있습니다.
          ],
          isFromCart: false, // 장바구니에서 주문하는 경우 true, 그렇지 않으면 false
        };
        const res = await startKakaopay(orderData);
        res ? (window.location.href = res) : console.log("없거나 실패");
        // if (result.status === 200) {
        //   window.location.href = result.data.body;
        // } else {
        //   console.log("실패했습니다");
        //   alert("실패");
        // }
      } catch (error) {
        console.error(error);
        alert("오류가 발생했습니다: " + error);
      }
    };
    return { handlePayment };
  },
};
</script>

<style scoped>
.title {
  text-align: center;
  margin-top: 150px;
  margin-bottom: 50px;
  font-size: 20px;
}
.pay-button {
  text-align: center;
  margin-bottom: 200px;
}
</style>
