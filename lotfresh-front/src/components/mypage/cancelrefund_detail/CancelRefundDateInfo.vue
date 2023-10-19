<template>
  <div class="wrapper">
    <div class="head_text">상세정보</div>
    <div class="outter_box">
      <div class="medium_box right_border">
        <div class="content_box head_text">취소 접수일자</div>
        <div class="content_box head_text">취소 접수번호</div>
        <div class="content_box head_text">취소완료일시</div>
      </div>
      <div class="medium_box">
        <div class="content_box">2023/09/26</div>
        <div class="content_box">10000002</div>
        <div class="content_box">2023/09/27 3시</div>
      </div>
    </div>
  </div>
</template>

<script>
export default {};
</script>

<style>
.wrapper {
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
