<template>
  <div class="wrapper">
    <div class="head_text">환불안내</div>
    <div class="outter_box">
      <div class="medium_box right_border">
        <div class="content_box head_text">상품금액</div>
        <div class="content_box head_text">환불 수단</div>
        <div class="content_box head_text">환불 완료금액</div>
      </div>
      <div class="medium_box">
        <div class="content_box">2,830원</div>
        <div class="content_box">카카오페이</div>
        <div class="content_box">2,830원</div>
      </div>
    </div>
  </div>
</template>

<script>
export default {};
</script>

<style>
.wrapper {
  margin-bottom: 5vh;
}

.head_text {
  font-size: 1.1rem;
  /* line-height: 1.1rem; */
  font-weight: bold;
}

.outter_box {
  border: solid 4px #c8c8c8;
  border-radius: 5px;
  display: flex;
  flex-direction: row;
  margin-top: 0.8vh;
}

.right_border {
  border-right: solid 4px #c8c8c8;
}

.medium_box {
}

.content_box {
  padding: 2vh 1vw 2vh 1vw;
  line-height: 1.2rem;
}

.right_border {
}

.bold_text {
}

.light_text {
}
</style>
