<template>
  <div class="item_wrapper">
    <div class="flex_row item_header_wrapper">
      <span class="margin_left_sm right_margin_sm">주문취소 접수일: </span>
      <span class="item_header_content">2023.09.25 주문</span>
      <span class="right_margin_sm">주문상세번호: </span>
      <span class="item_header_content">165584</span>
    </div>
    <div class="item_content_wrapper">
      <div class="item_content_box"><span>오리엔탈 드레싱</span><div>장바구니 담기</div></div>
      <div class="item_content_box"><span>1개</span><span>27,300원</span></div>
      <div><span>취소 완료</span><div>취소 상세</div></div>
    </div>
  </div>
</template>

<script>
export default {

}
</script>

<style scoped>
.item_wrapper {
    /* width: 100%; */
    border: solid 4px #c8c8c8;
    border-radius: 5px;
    /* padding: 10px; */
    /* margin: 10px; */
    
}

.flex_row {
    display: flex;
    flex-direction: row;

}

.item_content_wrapper {
    display: flex;
    flex-direction: row;
    
}

.item_content_box {
    border-right: solid 4px #c8c8c8;
}

.item_header_wrapper {
  padding-top: 0.3vh;
  padding-bottom: 0.7vh;
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

}

.stock {

}

.price {

}

.refund_state {

}

.refund_detail_button {

}
</style>