<template>
  <div class="order-product-container">
    <div class="demo-collapse">
      <el-collapse>
        <el-collapse-item name="1">
          <template #title>
            <div class="title"><h1>주문 상품</h1></div>
          </template>
          <div class="item">
            mounted시점에 redis로 axios통신을 해서 구매상품을 가져온다
          </div>
        </el-collapse-item>
      </el-collapse>
    </div>
  </div>
</template>

<script lang="ts">
export default {};
</script>

<style scoped>
.text-right {
  text-align: right;
}
.order-product-container {
  margin-right: auto;
  margin-left: auto;
}
.title {
  font-size: 15.6px;
}
.item {
  font-size: 30px;
}
</style>
