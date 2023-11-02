!
<template>
  <div>
    <div class="section">
      <div class="item">상품금액</div>
      <div class="item text-right">{{ formattedNumber(totalPrice) }}원</div>
    </div>
    <div class="section">
      <div class="item">상품할인금액</div>
      <div class="item text-right">{{ formattedNumber(discountPrice) }}원</div>
    </div>
  </div>
  <div class="emptySpace"></div>

  <div class="section">
    <div class="item grid-col">결제예정금액</div>
    <div class="item text-right">
      {{ formattedNumber(totalPrice - discountPrice) }}원
    </div>
  </div>

  <div class="order_button" @click="order">주문하기</div>
</template>

<script lang="ts">
import { addOrdersheetInfos } from "@/api/order/order";
import { useMemberStore } from "@/stores/member";
import { storeToRefs } from "pinia";
import type {
  OrderSheetItem,
  OrderSheetList,
} from "@/interface/orderInterface";
import router from "@/router";

export default {
  props: {
    totalPrice: {
      type: Number,
      default: 0,
    },
    discountPrice: {
      type: Number,
      default: 0,
    },
    items: {
      type: Array as () => OrderSheetItem[],
      default: () => [],
    },
  },
  data() {
    return {
      orderSheetList: {
        orderSheetItems: this.items,
        isFromCart: false,
        isBargain: false,
      } as OrderSheetList,
    };
  },
  setup() {
    const { accessToken } = storeToRefs(useMemberStore());
    return {
      accessToken,
    };
  },
  computed: {
    formattedNumber() {
      return (num: number) => {
        return new Intl.NumberFormat("ko-KR").format(num);
      };
    },
  },
  methods: {
    order() {
      this.orderSheetList.orderSheetItems = this.items;
      addOrdersheetInfos(this.orderSheetList, this.accessToken)
        .then(() => {
          router.push("/ordersheet");
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
};
</script>

<style scoped>
.emptySpace {
  width: 95%;
  height: 30px;
  margin-left: auto;
  margin-right: auto;
  margin-bottom: 20px;
}
.section {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  margin-bottom: 10px;
}

.order_button {
  width: 11vw;
  padding: 1vh 1vw 1vh 1vw;
  color: white;
  background-color: #ef2a23;
  border-radius: 5px;
  text-align: center;
  font-size: 1rem;
  margin: 20px auto;
  text-align: center;
  width: 80%;
}

.order_button:hover {
  background-color: #d8241f;
  cursor: pointer;
}

.order_button:active {
  background-color: #c01e1b;
}
</style>
