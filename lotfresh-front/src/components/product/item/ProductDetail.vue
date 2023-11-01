<template>
  <div v-if="props.product" class="product-detail">
    <p class="goods-name">
      <strong class="name">{{ props.product.name }}</strong>
      <span class="simple-content">신선식품 롯프레쉬</span>
    </p>
    <p class="goods-price">
      <span class="position">
        <span class="cost">
          <span v-if="!product.salesPrice" class="price"
            >{{ formattedPrice }}원
          </span>
          <span v-else>
            <span style="text-decoration: line-through; color: #999999"
              >{{ formattedPrice }}
            </span>
            →{{ formattedDiscountPrice }}원
          </span>
        </span>
      </span>
    </p>
    <div class="detail_info">
      <div class="info_categories">
        상품 카테고리 |
        <router-link :to="`/categories/${props.product.parendId}`">
          <span v-if="props.product.parentName" class="parent_category">
            {{ props.product.parentName }} >
          </span>
        </router-link>
        <router-link :to="`/categories/${props.product.categoryId}`">
          <span class="my_category">
            {{ props.product.categoryName }}
          </span>
        </router-link>
      </div>
    </div>
    <!-- 예시 카트 -->
    <div id="cartPut">
      <div class="cart-option cartList cart-type2">
        <div class="inner-option">
          <div class="in-option">
            <div class="list-goods">
              <ul class="list list-nopackage">
                <span class="tit-item">구매수량</span>
                <div class="option">
                  <button
                    type="button"
                    class="btn down on"
                    @click="minus"
                    value="-"
                  ></button>
                  <div id="result">{{ quantity }}</div>
                  <button
                    type="button"
                    class="btn up on"
                    @click="plus"
                    value="+"
                  ></button>
                </div>
              </ul>
            </div>
            <div id="holidayDeliveryInfo" class="holiday-delivery"></div>
            <div class="total">
              <div class="price">
                <strong class="tit">총 상품금액 :</strong>
                <span class="sum">
                  <span class="num" id="total-price"> </span>
                  <span class="won">{{ totalPrice }} 원</span>
                </span>
              </div>
            </div>
          </div>
          <div class="cart-footer off">
            <div class="functions"></div>
            <div class="button-wrap">
              <button class="cart-button" @click="addCart">
                장바구니 담기
              </button>
              <button class="base-button" @click="addOrderSheet">
                바로 구매하기
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed, ref } from "vue";
import { createCart } from "@/api/cart/cart";
import { addOrdersheetInfos } from "@/api/order/order";
import router from "@/router";
import type { CartCreateDto } from "@/interface/cartInterface";
import type {
  OrderSheetItem,
  OrderSheetList,
} from "@/interface/orderInterface";
import { useMemberStore } from "@/stores/member";
import { storeToRefs } from "pinia";

const { memberInfo, accessToken } = storeToRefs(useMemberStore());

const props = defineProps(["product"]);

const quantity = ref(1);

const orderSheetItems = ref<OrderSheetItem[]>([
  {
    productId: props.product.id,
    originalPrice: props.product.price,
    discountedPrice: props.product.salesPrice,
    productStock: quantity.value,
    productName: props.product.name,
    productThumbnail: props.product.thumbnail,
  },
]);

const cartCreateDto = ref<CartCreateDto>({
  productId: props.product.id,
  discountedPrice: props.product.salesPrice,
  province: memberInfo.value?.province,
  productStock: props.product.productStock,
  price: props.product.price,
  productName: props.product.name,
  productImageUrl: props.product.thumbnail,
  selectedQuantity: quantity.value,
});

const formattedPrice = computed(() => {
  return new Intl.NumberFormat("ko-KR").format(props.product.price);
});

const formattedDiscountPrice = computed(() => {
  return new Intl.NumberFormat("ko-KR").format(props.product.salesPrice);
});

const totalPrice = computed(() => {
  if (props.product.salesPrice != null) {
    return new Intl.NumberFormat("ko-KR").format(
      props.product.salesPrice * quantity.value
    );
  }
  return new Intl.NumberFormat("ko-KR").format(
    props.product.price * quantity.value
  );
});

const addCart = () => {
  createCart(cartCreateDto.value, accessToken.value)
    .then((response) => {
      console.log("성공", response);
    })
    .catch((err) => {
      console.log(err);
    });
};
const orderSheetList = ref<OrderSheetList>({
  orderSheetItems: [],
  isFromCart: false,
});

const addOrderSheet = () => {
  orderSheetList.value.orderSheetItems = orderSheetItems.value;
  console.log(orderSheetItems);
  addOrdersheetInfos(orderSheetList.value, accessToken.value)
    .then(() => {
      router.push("/ordersheet");
    })
    .catch(() => {
      console.log("##");
    });
};

const minus = () => {
  if (quantity.value > 1) {
    quantity.value--;
  }
};

const plus = () => {
  quantity.value++;
};
</script>

<style scoped>
.product-detail {
  width: 100%;
}

.product-detail .goods-name .name {
  display: block;
  font-weight: 700;
  font-size: 24px;
  line-height: 34px;
  word-break: break-all;
  padding-bottom: 1.5vh;
}

.product-detail .goods-name .simple-content {
  display: block;
  font-size: 14px;
  color: #999;
  line-height: 20px;
  word-break: break-all;
  padding-bottom: 4.5vh;
}

.product-detail .goods-price {
  border-bottom: 1px solid #e2e2e2;
  padding-bottom: 5px;
  margin-bottom: 5px;
}

.product-detail .goods-price .price {
  font-weight: 700;
  font-size: 28px;
  line-height: 30px;
  letter-spacing: 0;
  word-break: break-all;
}

.product-detail .goods-price .won {
  font-weight: 700;
  font-size: 18px;
  line-height: 30px;
  vertical-align: 2px;
  letter-spacing: 0;
}

.detail_info {
  padding-top: 30px;
}

.my_category {
  color: #999999;
}

.my_category:hover {
  color: #ef2a23;
}

.info_categories {
  color: #999999;
  padding-bottom: 24px;
}

/* cart */
#cartPut .cart-option {
  background-color: #fff;
}

#cartPut .cart-type2 {
  padding-bottom: 40px;
}

#cartPut .cart-type2 .list-goods {
}

#cartPut .cart-type2 .list-nopackage {
  padding: 0;
}

#cartPut .cart-type2 .list-nopackage li {
  float: none;
  /* width: 100%; */
  padding: 0 0 5px;
  margin: 0;
  border: 0;
}
#cartPut .cart_option .list .btn-position {
  position: relative;
}

#cartPut .cart-option .list .btn-del {
  overflow: hidden;
  position: absolute;
  right: 0;
  top: -1px;
  width: 24px;
  height: 24px;
  border: 0;
  background: url(https://res.kurly.com/pc/ico/2010/ico_cartput_del_24x25.svg)
    no-repeat 50% 50%;
  background-size: 24px 25px;
  font-size: 0;
  line-height: 0;
  text-indent: -9999px;
}

#cartPut .cart-type2 .list-nopackage .tit-item {
  /* float: left; */
  /* width: 128px; */
  font-weight: 400;
  font-size: 14px;
  color: #999999;
  line-height: 20px;
  letter-spacing: -0.5px;
}

#cartPut .cart-type2 .list-nopackage .option {
  padding-top: 0;
}

#cartPut .cart-option .list .option {
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

#cartPut .cart-option .list .down.on {
  background-image: url(https://res.kurly.com/pc/ico/2010/ico_minus_on.svg);
}

#cartPut .cart-option .list .down {
  background: #fff url(https://res.kurly.com/pc/ico/2010/ico_minus.svg)
    no-repeat 50% 50%;
  background-size: 30px 30px;
}

#cartPut .cart-option .list .btn {
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

#cartPut .cart-option .list .up.on {
  background-image: url(https://res.kurly.com/pc/ico/2010/ico_plus_on.svg);
}

#cartPut .cart-option .list .up {
  margin-right: 0px;
  margin-left: -1px;
  background: #fff url(https://res.kurly.com/pc/ico/2010/ico_plus.svg) no-repeat
    50% 50%;
  background-size: 30px 30px;
}

#cartPut .cart-option .list .btn {
  overflow: hidden;
  margin-left: 0px;
  width: 28px;
  height: 28px;
  border: 0;
  font-size: 0;
  line-height: 0;
  text-indent: -9999px;
}

#cartPut .cart-type2 .list {
  display: flex;
  gap: 20px;
  margin-left: 0px;
  padding-top: 15px;
  font-size: 12px;
  line-height: 20px;
  letter-spacing: -0.6px;
}

#cartPut .holiday-delivery {
  display: none;
  overflow: hidden;
  padding: 16px 0 18px;
  border-top: 1px solid #f4f4f4;
  font-size: 14px;
  color: #333;
  line-height: 16px;
}

#cartPut .cart-type2 .total {
  padding: 20px 0 20px;
}

#cartPut .cart-option .total {
  overflow: hidden;
  text-align: right;
}

#cartPut .cart-option .total .num {
  padding-left: 8px;
  font-weight: 800;
  font-size: 32px;
  line-height: 32px;
}

#cartPut .cart-option .total .won {
  padding-left: 2px;
  font-size: 20px;
  font-weight: 700;
  line-height: 20px;
  vertical-align: -1px;
}

#cartPut .cart-option .total {
  display: block;
  /*padding-top: 14px;*/
  font-size: 14px;
  color: #666;
  line-height: 20px;
  text-align: right;
}

#cartPut .cart-option .total .ico {
  display: inline-block;
  width: 38px;
  height: 20px;
  margin-right: 2px;
  border-radius: 10px;
  background-color: #ffbf00;
  font-weight: 700;
  font-size: 11px;
  color: #fff;
  line-height: 20px;
  text-align: center;
  vertical-align: 1px;
}

#cartPut .cart-option .total {
  font-weight: 700;
}

.button-wrap {
  display: flex;
  gap: 20px;
  justify-content: flex-end;
}

#cartPut .cart-button {
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
#cartPut .base-button {
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
