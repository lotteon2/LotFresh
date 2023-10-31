<template>
  <div class="mb-2vh">
    <div class="sub_title_toggle_wrapper" @click="toggleOrderItems">
      <div>주문 상품</div>
      <div
        :class="{
          on_toggle: isOrderItemsVisible,
          off_toggle: !isOrderItemsVisible,
        }"
      ></div>
    </div>

    <transition name="slide">
      <div
        class="item_info_wrapper"
        v-for="item in orderSheetItems"
        :key="item.productId"
      >
        <div class="flex_row align_item_center">
          <img :src="item.productThumbnail" class="item_img" />
          <div class="content_text">
            {{ item.productName }}
          </div>
        </div>
        <div class="flex_row">
          <div class="content_text_light">{{ item.productStock }}개</div>
          <div class="content_text ml-2vw">{{ item.discountedPrice }}원</div>
        </div>
      </div>

      <div v-if="isOrderItemsVisible" key="1">
        <div class="item_info_wrapper">
          <div class="flex_row align_item_center">
            <img
              src="https://product-image.kurly.com/product/image/3df368c8-e124-4d06-a9e9-af4c10d01b53.jpeg"
              class="item_img"
            />
            <div class="content_text">
              [아임닭] 닭가슴살 꾸이칩 시그니쳐 30g
            </div>
          </div>
          <div class="flex_row">
            <div class="content_text_light">1개</div>
            <div class="content_text ml-2vw">2400원</div>
          </div>
        </div>
        <div class="item_info_wrapper">
          <div class="flex_row align_item_center">
            <img
              src="https://product-image.kurly.com/product/image/3df368c8-e124-4d06-a9e9-af4c10d01b53.jpeg"
              class="item_img"
            />
            <div class="content_text">
              [아임닭] 닭가슴살 꾸이칩 시그니쳐 30g
            </div>
          </div>
          <div class="flex_row">
            <div class="content_text_light">1개</div>
            <div class="content_text ml-2vw">2400원</div>
          </div>
        </div>
        <div class="item_info_wrapper">
          <div class="flex_row align_item_center">
            <img
              src="https://product-image.kurly.com/product/image/3df368c8-e124-4d06-a9e9-af4c10d01b53.jpeg"
              class="item_img"
            />
            <div class="content_text">
              [아임닭] 닭가슴살 꾸이칩 시그니쳐 30g
            </div>
          </div>
          <div class="flex_row">
            <div class="content_text_light">1개</div>
            <div class="content_text ml-2vw">2400원</div>
          </div>
        </div>
        <div class="item_info_wrapper">
          <div class="flex_row align_item_center">
            <img
              src="https://product-image.kurly.com/product/image/3df368c8-e124-4d06-a9e9-af4c10d01b53.jpeg"
              class="item_img"
            />
            <div class="content_text">
              [아임닭] 닭가슴살 꾸이칩 시그니쳐 30g
            </div>
          </div>
          <div class="flex_row">
            <div class="content_text_light">1개</div>
            <div class="content_text ml-2vw">2400원</div>
          </div>
        </div>
      </div>
    </transition>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import type { OrderSheetItem } from "../../../interface/cartInterface";

export default defineComponent({
  props: {
    orderSheetItems: {
      type: Array as () => OrderSheetItem[] | undefined,
      required: true,
    },
  },
  data() {
    return {
      isOrderItemsVisible: true,
    };
  },
  methods: {
    toggleOrderItems() {
      this.isOrderItemsVisible = !this.isOrderItemsVisible;
    },
  },
});
</script>

<style scoped>
.text-right {
  text-align: right;
}
.order-product-container {
  margin-right: auto;
  margin-left: auto;
}

.sub_title_toggle_wrapper {
  border-bottom: 2px solid black;
  margin-bottom: 5vh;

  display: flex;
  flex-direction: row;
  justify-content: space-between;
  font-size: 1.5rem;

  cursor: pointer;
}

.item_img {
  width: 8vw;
  height: 8vw;
  object-fit: cover;
  margin-right: 2vw;
  border-radius: 10px;
}

.item_info_wrapper {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 3vh;
}

.flex_row {
  display: flex;
  flex-direction: row;
}

.align_item_center {
  align-items: center;
}

.content_text {
  font-size: 1.1rem;
  font-weight: bold;
  line-height: 1.1rem;
}

.ml-2vw {
  margin-left: 1.5vw;
}

.mb-2vh {
  margin-bottom: 10vh;
}

.content_text_light {
  font-size: 1rem;
  font-weight: normal;
  line-height: 1.1rem;
}
.title {
  font-size: 12.6px;
}
.item {
  font-size: 20px;
}

.slide-enter-active,
.slide-leave-active {
  transition: max-height 1s ease;
}

.slide-enter,
.slide-leave-to {
  max-height: 0;
  overflow: hidden;
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
</style>
