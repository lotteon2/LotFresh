<template>
  <div class="order-product-container">
    <div class="demo-collapse">
      <el-collapse>
        <el-collapse-item name="1">
          <template #title>
            <div class="title"><h2>주문 상품</h2></div>
          </template>
          <div class="item">
            mounted시점에 redis로 axios통신을 해서 구매상품을 가져온다
          </div>
        </el-collapse-item>
      </el-collapse>
    </div>
  </div>
</template>

<script lang="ts"></script>

<style scoped>
.text-right {
  text-align: right;
}
.order-product-container {
  margin-right: auto;
  margin-left: auto;
}
</style>
