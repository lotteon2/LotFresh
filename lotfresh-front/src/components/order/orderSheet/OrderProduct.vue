<template>
  <div>
    <div>
      <div>주문 상품</div>
      <div>toggle button</div>
    </div>
    <div>
      <div class="item_info_wrapper">
        <div class="flex_row align_item_center">
          <img
            src="https://product-image.kurly.com/product/image/3df368c8-e124-4d06-a9e9-af4c10d01b53.jpeg"
            class="item_img"
          />
          <div class="content_text">[아임닭] 닭가슴살 꾸이칩 시그니쳐 30g</div>
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
          <div class="content_text">[아임닭] 닭가슴살 꾸이칩 시그니쳐 30g</div>
        </div>
        <div class="flex_row">
          <div class="content_text">1개</div>
          <div class="content_text">2400원</div>
        </div>
      </div>
    </div>
    <div class="item_info_wrapper">
      <div class="flex_row align_item_center">
        <img
          src="https://product-image.kurly.com/product/image/3df368c8-e124-4d06-a9e9-af4c10d01b53.jpeg"
          class="item_img"
        />
        <div class="content_text">[아임닭] 닭가슴살 꾸이칩 시그니쳐 30g</div>
      </div>
      <div class="flex_row">
        <div class="content_text">1개</div>
        <div class="content_text">2400원</div>
      </div>
    </div>
    <div class="item_info_wrapper">
      <div class="flex_row align_item_center">
        <img
          src="https://product-image.kurly.com/product/image/3df368c8-e124-4d06-a9e9-af4c10d01b53.jpeg"
          class="item_img"
        />
        <div class="content_text">[아임닭] 닭가슴살 꾸이칩 시그니쳐 30g</div>
      </div>
      <div class="flex_row">
        <div class="content_text">1개</div>
        <div class="content_text">2400원</div>
      </div>
    </div>
  </div>

  <div class="order-product-container">
    <div class="demo-collapse">
      <el-collapse>
        <el-collapse-item name="1" class="is-active">
          <template #title>
            <div class="title"><h2>주문 상품</h2></div>
          </template>
          <div class="item">
            mounted시점에 redis로 axios통신을 해서 구매상품을 가져온다
          </div>
          <div class="item">
            mounted시점에 redis로 axios통신을 해서 구매상품을 가져온다
          </div>
          <div class="item">
            mounted시점에 redis로 axios통신을 해서 구매상품을 가져온다
          </div>
        </el-collapse-item>
      </el-collapse>
    </div>
  </div>
</template>

<script lang="ts">
export default {
  // 이거를 안쓰더라도 넣어주셔야 다른 파일에서 이 컴포넌트를 import 할 수 있습니다.
};
</script>

<style scoped>
.text-right {
  text-align: right;
}
.order-product-container {
  margin-right: auto;
  margin-left: auto;
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
  font-size: 1rem;
  font-weight: bold;
  line-height: 1.1rem;
}

.ml-2vw {
  margin-left: 1.5vw;
}

.content_text_light {
  font-size: 0.9rem;
  font-weight: normal;
  line-height: 1.1rem;
}
</style>
