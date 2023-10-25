<template>
  <div>
    <div class="sub_title_wrapper">
      <div>배송 정보</div>
      <div>배송지변경 안내</div>
    </div>

    <div class="flex_row align-item-center">
      <div class="width10vw align-self-start">배송지</div>
      <div>
        <div class="flex_row">
          <div class="width7vw">우편번호:</div>
          <div>55999</div>
        </div>
        <div class="flex_row">
          <div class="width7vw">도로명 주소:</div>
          <div>서울특별시 서초구 도로명주소는 이것입니다.</div>
        </div>
        <div class="flex_row">
          <div class="width7vw">상세주소:</div>
          <div>2층 서22 김성중</div>
        </div>
      </div>
      <div class="to_right_end">주소 변경 버튼</div>
    </div>
  </div>

  <div class="delivery-info-container">
    <div>
      <div class="title">
        <div class="item"><h3>배송 정보</h3></div>
        <div class="item"></div>
        <div class="item"></div>
        <div class="item"></div>
        <div class="item"></div>
        <div class="item"></div>
        <div class="item"></div>
        <div class="item"></div>
        <div class="item"></div>
        <div class="item"></div>
        <div class="item"></div>
        <div class="item text-right gray-text small-text v-center">
          배송지변경 안내
        </div>
      </div>
    </div>
    <div class="container">
      <div class="item">배송지</div>
      <div class="item">
        서우울특별시 서초구 서초동 어쩌구번지 비트컴퓨터 3층
      </div>
      <div class="item"></div>
      <div class="item"></div>
      <div class="item"></div>
      <div class="item text-right"><button>주소 변경</button></div>
    </div>
  </div>
</template>

<script lang="ts">
export default {};
</script>

<style scoped>
.sub_title_wrapper {
  border-bottom: 2px solid black;
  margin-bottom: 5vh;

  display: flex;
  flex-direction: row;
  justify-content: space-between;
  font-size: 1.5rem;
}

.flex_row {
  display: flex;
  flex-direction: row;
  font-size: 1.3rem;
}

.align-item-center {
  align-items: center;
}

.align-self-start {
  align-self: flex-start;
}

.width10vw {
  width: 10vw;
}

.width7vw {
  width: 7vw;
}

.to_right_end {
  margin-left: auto;
}

.title {
  display: grid;
  grid: ". . . . . . . . . . . .";
  margin-top: 30px;
}
.container {
  display: grid;
  grid: ". . . . . .";
  margin-top: 40px;
}
.text-right {
  text-align: right;
}
.gray-text {
  color: gray;
}
.small-text {
  font-size: 15px;
}
.delivery-info-container {
  margin-right: auto;
  margin-left: auto;
  font-size: 20px;
}
.title {
  padding-bottom: 10px;
  border-bottom: 2px solid rgb(0, 0, 0);
}
.v-center {
  margin-top: auto;
  margin-bottom: auto;
}
</style>
