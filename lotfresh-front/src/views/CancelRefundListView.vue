<template>
  <div>
    <CancelRefundHeader></CancelRefundHeader>
    <div class="body_wrapper">
      <CancelRefundItemList></CancelRefundItemList>
    </div>
    <KakaopayButton @kakaopay_button_click="handlePayment"></KakaopayButton>
  </div>
</template>

<script lang="ts">
import CancelRefundHeader from "../components/mypage/cancelrefund_list/CancelRefundHeader.vue";
import CancelRefundItemList from "../components/mypage/cancelrefund_list/CancelRefundItemList.vue";
import KakaopayButton from "../components/order/orderSheet/KakaopayButton.vue";
import type { OrderCreateRequest } from "../api/order/order";
import { startKakaopay } from "../api/order/order";

export default {
  components: {
    CancelRefundHeader,
    CancelRefundItemList,
    KakaopayButton,
  },

  setup() {
    // const handlePayment = async () => {
    //   try {
    //     const orderData: OrderCreateRequest = {
    //       productRequests: [
    //         {
    //           productId: 1,
    //           productPrice: 10000,
    //           productStock: 50,
    //           productName: "Product Name",
    //           productThumbnail: "https://example.com/product-thumbnail.jpg",
    //         },
    //         {
    //           productId: 2,
    //           productPrice: 20000,
    //           productStock: 30,
    //           productName: "Another Product Name",
    //           productThumbnail:
    //             "https://example.com/another-product-thumbnail.jpg",
    //         },
    //         // 필요한 만큼 추가적인 ProductRequest 객체를 포함시킬 수 있습니다.
    //       ],
    //       isFromCart: false, // 장바구니에서 주문하는 경우 true, 그렇지 않으면 false
    //     };
    //     const res = await startKakaopay(orderData);
    //     res ? (window.location.href = res) : console.log("없거나 실패");
    //     // if (result.status === 200) {
    //     //   window.location.href = result.data.body;
    //     // } else {
    //     //   console.log("실패했습니다");
    //     //   alert("실패");
    //     // }
    //   } catch (error) {
    //     console.error(error);
    //     alert("오류가 발생했습니다: " + error.message);
    //   }
    // };
    // return { handlePayment };
  },
};
</script>

<style scoped>
.body_wrapper {
  margin: 3%;
}
</style>
