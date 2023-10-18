<template>
  <div class="outer">
    <div class="text-left">
      <h3>상품배송정보</h3>
    </div>
    <div class="delivery-container">
      <div class="item">
        <div class="item-title">상품배송 진행여부</div>
        <div class="item-content">내용</div>
      </div>
      <div class="item">
        <div class="item-title">배송 담당자 이름</div>
        <div class="item-content">내용</div>
      </div>
      <div class="item">
        <div class="item-title">연락처</div>
        <div class="item-content">내용</div>
      </div>
      <div class="item">
        <div class="item-title">배송 접수번호</div>
        <div class="item-content">내용</div>
      </div>
      <div class="item">
        <div class="item-title">배송주소</div>
        <div class="item-content">내용</div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
export default {};
</script>

<style scoped>
.delivery-container {
  display: grid;
  grid: ". .";
  margin-left: auto;
  margin-right: auto;
}

.outer {
  margin-left: 100px;
}

.item {
  margin-bottom: 15px;
}
.item-title {
  text-align: left;
  font-weight: bold;
  margin-bottom: 5px;
}

.item-content {
  text-align: left;
  color: gray;
  margin-left: 20px;
}
.text-left {
  text-align: left;
}
</style>
