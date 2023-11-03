<template>
  <div>
    <div class="pageTitle">
      <div class="title">
        <h2>주문서</h2>
      </div>
    </div>
    <order-product
      v-if="orderSheetList"
      :orderSheetItems="orderSheetList?.orderSheetItems"
    />
    <orderer-info
      v-if="orderSheetList"
      :orderSheetItems="orderSheetList?.orderSheetItems"
    />
    <delivery-info
      @openAddressModal="openAddressModal"
      :addressInfo="addressInfo"
    />
    <payment-bill
      v-if="orderSheetList"
      :orderSheetItems="orderSheetList?.orderSheetItems"
    />
    <div class="pay_button_wrapper">
      <KakaopayButton @kakaopay_button_click="handlePayment"></KakaopayButton>
    </div>

    <KakaoAddressFinderModal
      v-model:isAddressModalOpen="isAddressModalOpen"
      @closeModal="(address) => updateAddressInfo(address)"
    ></KakaoAddressFinderModal>
  </div>
</template>

<script lang="ts">
import { onBeforeUnmount, onMounted, ref } from "vue";
import { useRoute, useRouter } from "vue-router";
import DeliveryInfo from "../components/order/orderSheet/DeliveryInfo.vue";
import OrdererInfo from "../components/order/orderSheet/OrdererInfo.vue";
import OrderProduct from "../components/order/orderSheet/OrderProduct.vue";
import PaymentBill from "../components/order/orderSheet/PaymentBill.vue";
import KakaopayButton from "../components/order/orderSheet/KakaopayButton.vue";
import KakaoAddressFinderModal from "../components/order/orderSheet/KakaoAddressFinderModal.vue";
import { getOrders } from "../api/order/order";
import { useMemberStore } from "@/stores/member";
import { storeToRefs } from "pinia";
import type { OrderCreateRequest, address } from "../api/order/order";
import type {
  OrderSheetList,
  OrderSheetItem,
} from "@/interface/orderInterface";
import { startKakaopay, getOrdersheetList } from "../api/order/order";

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
      addressInfo: {
        zipCode: "00000",
        roadAddress: "주소를 변경해주세요.",
        detailAddress: "상세주소를 입력해주세요..",
      },
    };
  },

  methods: {
    openAddressModal: function (): void {
      this.isAddressModalOpen = true;
    },

    updateAddressInfo: function (address: any): void {
      this.addressInfo.zipCode = address.zipCode;
      this.addressInfo.roadAddress = address.roadAddress;
      this.addressInfo.detailAddress = "";
    },
  },

  setup() {
    const router = useRouter(); // router 객체 초기화
    // let orderSheetInfo: OrderSheetInfo;
    let orderSheetList = ref<OrderSheetList | null>(null);
    const { accessToken } = storeToRefs(useMemberStore());
    const { memberInfo } = storeToRefs(useMemberStore()); // useMemberStore를 사용해 memberInfo를 가져옴.
    const addressInfo = ref<address>();
    const handlePayment = async () => {
      try {
        if (memberInfo.value && memberInfo) {
          addressInfo.value = {
            zipCode: memberInfo.value.zipCode, // memberInfo에 zipCode가 없는 경우 "00000"을 기본값으로 사용합니다.
            roadAddress: memberInfo.value.roadAddress,
            detailAddress: memberInfo.value.detailAddress,
          };
          const orderData: OrderCreateRequest = {
            productRequests: orderSheetList.value?.orderSheetItems,
            isFromCart: orderSheetList.value?.isFromCart, // 장바구니에서 주문하는 경우 true, 그렇지 않으면 false
            province: memberInfo.value.province,
            isBargain: orderSheetList.value?.isBargain,
            address: addressInfo.value,
          };
          const res = await startKakaopay(orderData, accessToken.value);
          // res ? (window.location.href = res) : console.log("없거나 실패");
          res
            ? window.open(
                res,
                "Lot-Fresh 카카오페이 QR 결제화면",
                "top=0, left=0, width=500, height=600, menubar=no, toolbar=no, resizable=no, status=no, scrollbars=no"
              )
            : console.log("없거나 실패");
        }
      } catch (error) {
        console.error(error);
        alert("오류가 발생했습니다: " + error);
        window.open(
          // "https://engineerinsight.tistory.com/73",
          "http://localhost:5173/payment-result/success/" + 3, // "qr url"로 수정 필요 😃
          "Lot-Fresh 카카오페이 QR 결제화면",
          "top=0, left=0, width=500, height=600, menubar=no, toolbar=no, resizable=no, status=no, scrollbars=no"
        );
      }
      return {
        accessToken,
      };
    };

    const handleMessage = (event: MessageEvent) => {
      // 팝업 창에서 보낸 메시지를 받아서 라우팅 - 영수증 페이지로
      if (
        event.origin !== "https://www.lot-fresh.shop" &&
        event.origin !== "https://lot-fresh.shop"
      )
        return;
      const { routeName, params } = event.data;
      // window.scrollTo(0, 0);
      // console.log("반복적호출이되고있나?" + params);
      if (params && params.orderId) {
        window.scrollTo(0, 0);
        router.push({ name: routeName, params: params });
      }
    };

    onMounted(async () => {
      try {
        orderSheetList.value = await getOrdersheetList(accessToken.value);
      } catch (error) {
        console.error(error);
      }

      window.addEventListener("message", handleMessage);
    });

    onBeforeUnmount(() => {
      window.removeEventListener("message", handleMessage);
    });

    return { handlePayment, orderSheetList, addressInfo };
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

.pay_button_wrapper {
  display: flex;
  flex-direction: column;
  margin: 1vh 0 15vh 0;
}
</style>
