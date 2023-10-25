<template>
  <div class="kakao_pay_button" @click="$emit('kakaopay_button_click')">
    카카오페이로 결제하기
  </div>
</template>

<script>
export default {};
</script>

<style>
.kakao_pay_button {
  width: 11vw;
  padding: 1vh 1vw 1vh 1vw;
  color: white;
  background-color: #ef2a23;
  border-radius: 5px;
  text-align: center;
  font-size: 1rem;
  /* font-weight: bold; */
}

.kakao_pay_button:hover {
  background-color: #d8241f;
  cursor: pointer;
}

.kakao_pay_button:active {
  background-color: #c01e1b;
}
</style>
