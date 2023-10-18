<template>
  <div class="item_wrapper">
    <div class="flex_row item_header_wrapper">
      <div>상품</div>
      <div>금액</div>
      <div>진행상태</div>
      
    </div>

    <div class="item_content_wrapper flex_row">
      <div class="margin_left_sm item_content_box">
        <div class="product_name">오리엔탈 드레싱</div>
        <!-- <div class="to_cart_button">장바구니 담기</div> -->
      </div>
      <div class="item_content_box">
        <div class="text_center stock font-normal">1개</div>
        <div class="text_center price">27,300원</div>
      </div>
      <div class="item_content_box align-items-center">
        <div class="text_center refund_state">취소 완료</div>
        <!-- <div class="refund_detail_button">취소 상세</div> -->
      </div>
    </div>
  </div>
</template>

<script>
export default {

}
</script>

<style scoped>
.item_wrapper {
    border: solid 4px #c8c8c8;
    border-radius: 5px;
    padding-top: 2vh;
}

.flex_row {
    display: flex;
    flex-direction: row;

}

.item_content_wrapper { 
    display: flex;
    flex-direction: row;
}

.text_center {
  text-align: center;
}

.item_content_box {
    border-right: solid 4px #c8c8c8;
    padding-top: 3vh;
    padding-bottom: 2vh;

    display: flex;
    flex-direction: column;
    justify-content: center;
}

.product_name {
  font-size: 1.3rem;
  font-weight: bold;
  margin-bottom: 2vh;
}

.flex_row > div:nth-child(1) {
  flex: 3;
  text-align: center;
  font-weight: bold;
}

.flex_row > div:nth-child(2) {
  flex: 1;
  text-align: center;
  font-weight: bold;
}

.flex_row > div:nth-child(3) {
  flex: 1;
  text-align: center;
  font-weight: bold;
}

.font-normal {
  font-weight: normal;
}



.item_header_wrapper {
  padding-top: 0.3vh;
  padding-bottom: 2vh;
  border-bottom: solid 4px #c8c8c8;
  font-size: 1.1rem;
  line-height: 1.2rem;
  align-items: baseline;
}

.margin_left_sm {
    margin-left: 0.5vw;
}

.item_header_content {
  margin-right: 2vw;
  
  font-weight: bold;
  font-size: 1.2rem;
}


.right_margin_sm {
    margin-right: 0.5vw;
}

.item_name {

}

.to_cart_button {
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 5px;
  border: 2px solid #c8c8c8;
  padding: 10px 5px 10px 5px;
  width: 6vw;
  align-self: end;
  margin-right: 0.5vw;
}

.to_cart_button:hover {
  background-color: #f5f5f5; /* 마우스 오버시 배경색 변경 */
  cursor:pointer;
}

.to_cart_button:active {
  background-color: #e0e0e0; /* 클릭시 배경색 변경 */
}

.stock {
  font-size: 1.1rem;
  margin-bottom: 1vh;

}

.price {
  font-size: 1.3rem;
  font-weight: bold;
}

.refund_state {
  font-size: 1.3rem;
  font-weight: bold;
  margin-bottom: 1.5vh;

}

.align-items-center {
  align-items: center;
}

.refund_detail_button {
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 5px;
  border: 2px solid #EF2A23;
  color:#EF2A23;
  padding: 10px 5px 10px 5px;
  width: 6vw;
}

.refund_detail_button:hover {
    background-color: #FED8D9;
    cursor:pointer;
}

.refund_detail_button:active {
    background-color: #FEB4B6;
}
</style>