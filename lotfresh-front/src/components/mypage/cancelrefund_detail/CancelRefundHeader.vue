<template>
  <div class="header_wrapper">
    <div class="header_text">주문취소 및 반품 상세</div>
    <div class="order_info_wrapper">
      <div class="order_info">주문일자: 2023.09.25</div>
      <div class="order_info">주문상세번호: 19952</div>
      <div class="to_refund_list">목록으로 이동</div>
    </div>
  </div>
</template>

<script>
export default {};
</script>

<style scoped>
.header_wrapper {
  /* width:60%; */
  width: 100%;
  padding-top: 8vh;
  padding-bottom: 0.5vh;
  margin-bottom: 5vh;
  border-bottom: solid red 1.5px;
}

.header_text {
  font-weight: bolder;
  font-size: 1.4rem;
  text-align: center;
  margin-bottom: 3vh;
}

.order_info_wrapper {
  display: flex;
  flex-direction: row;
  padding-left: 0.5vw;
  width: 100%;
}

.order_info {
  font-size: 1.1rem;
  font-weight: bold;
  margin-right: 1vw;
}

.to_refund_list {
  margin-left: auto;
  margin-right: 1vw;
}
.to_refund_list:hover {
  cursor: pointer;
}
</style>
