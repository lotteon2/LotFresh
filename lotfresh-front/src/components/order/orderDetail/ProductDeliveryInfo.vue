<template>
  <div class="outer">
    <div class="text-left">
      <h3>상품배송정보</h3>
    </div>
    <div class="delivery-container">
      <div class="item">
        <div class="item-title bold-text">상품배송 진행여부</div>
        <div class="item-content">-</div>
      </div>
      <div class="item">
        <div class="item-title bold-text">배송 담당자 이름</div>
        <div class="item-content">-</div>
      </div>
      <div class="item">
        <div class="item-title bold-text">연락처</div>
        <div class="item-content">-</div>
      </div>
      <div class="item">
        <div class="item-title bold-text">배송 접수번호</div>
        <div class="item-content">-</div>
      </div>
      <div class="item">
        <div class="item-title bold-text">배송주소</div>
        <div class="item-content">-</div>
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
  border: 4px solid #c8c8c8;
  border-radius: 5px;
  padding: 2vh 1vw 0vh 1vw;
  /* box-shadow: 0 0px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19); */
  box-shadow: 0 6px 10px rgba(0, 0, 0, 0.2);
}

.outer {
  margin-right: auto;
  margin-left: auto;
  font-size: 1.3rem;
  margin-bottom: 15vh;
}

.item {
  margin-bottom: 5vh;
  display: flex;
  flex-direction: row;
}
.item-title {
  text-align: left;
  margin-bottom: 5px;
  width: 10vw;
}
.bold-text {
  font-weight: bold;
}

.item-content {
  text-align: right;
  color: gray;
  /* margin-left: auto; */
  /* margin-right: 3vw; */
}
.text-left {
  text-align: left;
}
</style>
