<template>
  <div class="cart-container">
    <div class="title">
      <h2>장바구니</h2>
    </div>

    <div class="left-section">
      <div>
        <div class="sub_title_toggle_wrapper" @click="toggleOrderItems">
          <div>주문 상품</div>
          <div
            :class="{
              on_toggle: isOrderItemsVisible,
              off_toggle: !isOrderItemsVisible,
            }"
          ></div>
        </div>
      </div>

      <transition name="slide">
        <div v-if="isOrderItemsVisible" key="1">
          <div
            v-for="(cartItemResponse, index) in cartItemResponses"
            :key="index"
            class="item"
          >
            <input
              type="checkbox"
              v-model="selectedCartItems"
              :value="cartItemResponse"
              checked
              class="check-box"
            />
            <div>
              <img :src="cartItemResponse.productThumbnail" class="item_img" />
            </div>
            <div class="item_name">{{ cartItemResponse.productName }}</div>
            <div class="item_quantity">
              <div class="option">
                <button
                  type="button"
                  class="btn down on"
                  @click="minus(index)"
                  value="-"
                ></button>
                <div id="result">{{ cartItemResponse.productStock }}</div>
                <button
                  type="button"
                  class="btn up on"
                  @click="plus(index)"
                  value="+"
                ></button>
              </div>
            </div>
            <div class="item_price">
              <div v-if="cartItemResponse.discountedPrice == 0">
                <div>{{ cartItemResponse.originalPrice }}원</div>
              </div>
              <div v-else>
                <div class="original-price">
                  {{ cartItemResponse.originalPrice }}원
                </div>
                <div>
                  {{
                    cartItemResponse.originalPrice -
                    cartItemResponse.discountedPrice
                  }}원
                </div>
              </div>
            </div>
          </div>
        </div>
      </transition>
    </div>
    <div class="right-section">
      <div class="delivery">
        <delivery-side
          @openAddressModal="openAddressModal"
          :addressInfo="addressInfo"
        />
      </div>
      <div class="payment">
        <payment-side
          :total-price="totalPrice"
          :discount-price="discountPrice"
          :items="selectedCartItems"
        />
      </div>
      <KakaoAddressFinderModal
        v-model:isAddressModalOpen="isAddressModalOpen"
        @closeModal="(address) => updateAddressInfo(address)"
      ></KakaoAddressFinderModal>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import DeliverySide from "../components/cart/DeliverySide.vue";
import PaymentSide from "../components/cart/PaymentSide.vue";
import KakaoAddressFinderModal from "../components/order/orderSheet/KakaoAddressFinderModal.vue";
import type { OrderSheetItem } from "@/interface/orderInterface";
import { watch } from "vue";
import { useMemberStore } from "@/stores/member";
import { storeToRefs } from "pinia";
import { getCarts } from "@/api/cart/cart";

export default defineComponent({
  components: {
    DeliverySide,
    PaymentSide,
    KakaoAddressFinderModal,
  },
  setup() {
    const { accessToken } = storeToRefs(useMemberStore());
    return {
      accessToken,
    };
  },
  data() {
    return {
      isOrderItemsVisible: true,
      cartItemResponses: [] as OrderSheetItem[],
      selectedCartItems: [] as OrderSheetItem[],
      isAddressModalOpen: false,
      addressInfo: {
        zipCode: "초기 우편번호",
        roadAddress: "초기 도로명 주소",
        detailAddress: "나는 상세 주소",
      },
      totalPrice: 0,
      discountPrice: 0,
    };
  },
  methods: {
    toggleOrderItems() {
      this.isOrderItemsVisible = !this.isOrderItemsVisible;
    },
    minus(index: number) {
      if (this.cartItemResponses[index].productStock > 1) {
        this.cartItemResponses[index].productStock--;
      }
    },
    plus(index: number) {
      this.cartItemResponses[index].productStock++;
    },
    openAddressModal: function (): void {
      this.isAddressModalOpen = true;
    },
    updateAddressInfo: function (address: any): void {
      this.addressInfo.zipCode = address.zipCode;
      this.addressInfo.roadAddress = address.roadAddress;
      this.addressInfo.detailAddress = address.detailAddress;
    },
  },
  watch: {
    selectedCartItems: {
      handler(newSelectedCartItems, oldSelectedCartItems) {
        let total = 0;
        let discount = 0;
        if (newSelectedCartItems) {
          for (const cartItem of newSelectedCartItems) {
            total += cartItem.originalPrice * cartItem.productStock;
            discount +=
              (cartItem.originalPrice - cartItem.discountedPrice) *
              cartItem.productStock;
          }
          this.totalPrice = total;
          this.discountPrice = discount;
        }
      },
      deep: true,
    },
  },
  created() {
    getCarts(this.accessToken).then((data) => {
      this.cartItemResponses = data;
    });
  },
});
</script>

<style scoped>
.left-section {
  width: 47vw;
  float: left;
  margin-right: 70px;
}
.right-section {
  width: 14vw;
  float: left;
}

.title {
  text-align: center;
  margin-top: 150px;
  margin-bottom: 30px;
  padding-bottom: 30px;
  margin-left: auto;
  margin-right: auto;
  border-bottom: 2px solid rgb(245, 199, 199);
  font-size: 20px;
}

.sub_title_toggle_wrapper {
  border-bottom: 2px solid black;
  margin-bottom: 2vh;

  display: flex;
  flex-direction: row;
  justify-content: space-between;
  font-size: 1.5rem;

  cursor: pointer;
}

.item_info_wrapper {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 3vh;
}

.item {
  display: flex;
  flex-direction: row;
  /* justify-content: space-between; */
  align-items: center;
  margin-bottom: 20px;
}

.check-box {
  margin-right: 35px;
}

.item_img {
  width: 8vw;
  height: 8vw;
  object-fit: cover;
  margin-right: 2vw;
  border-radius: 10px;
}

.item_name {
  font-size: 20px;
  width: 40%;
}

.item_quantity {
  width: 10%;
  margin-right: 10%;
}

.item_price {
  width: 10%;
}

.btn {
  overflow: hidden;
  float: left;
  margin-left: 0px;
  width: 28px;
  height: 28px;
  border: 0;
  font-size: 0;
  line-height: 0;
  text-indent: -9999px;
}

.up {
  margin-right: 0px;
  margin-left: -1px;
  background-image: url(https://res.kurly.com/pc/ico/2010/ico_plus_on.svg);
  background: #fff url(https://res.kurly.com/pc/ico/2010/ico_plus.svg) no-repeat
    50% 50%;
  background-size: 30px 30px;
}

.down {
  background-image: url(https://res.kurly.com/pc/ico/2010/ico_minus_on.svg);
  background: #fff url(https://res.kurly.com/pc/ico/2010/ico_minus.svg)
    no-repeat 50% 50%;
  background-size: 30px 30px;
}

.option {
  margin-top: -5px;
  overflow: hidden;
  display: flex;
  justify-items: center;
  /* float: left; */
  width: 88px;
  height: 30px;
  border: 1px solid #dddfe1;
  border-radius: 3px;
}

.original-price {
  text-decoration: line-through;
  color: #999999;
}

input[type="checkbox"] {
  width: 1.6rem;
  height: 1.6rem;
  border-radius: 50%;
  border: 1px solid #999;
  appearance: none;
  cursor: pointer;
  transition: background 0.2s;
}

input[type="checkbox"]:checked {
  /* background: red; */
  border: none;
  background-image: url("https://as2.ftcdn.net/v2/jpg/02/59/69/81/1000_F_259698116_LCxGTz8PujushBCnkAXO18UQCMpbpDO0.jpg");
  background-size: cover;
}

.on_toggle {
  width: 2rem;
  height: 2rem;
  background-image: url("https://www.svgrepo.com/show/521469/arrow-down.svg");
  background-size: cover;
}

.off_toggle {
  width: 2rem;
  height: 2rem;
  background-image: url("https://www.svgrepo.com/show/521479/arrow-next-small.svg");
  background-size: cover;
}

#result {
  float: left;
  /* margin-left: 0px; */
  width: 30px;
  height: 30px;
  margin-right: -1px;
  padding: 4px 0 4px;
  border: 0;
  background-color: #fff;
  font-size: 14px;
  color: #000;
  line-height: 18px;
  text-align: center;
}
</style>
