<template>
  <div
    class="kakao_pay_button align_center"
    @click="$emit('kakaopay_button_click')"
  >
    카카오페이로 결제하기
  </div>
</template>

<script>
export default {};
</script>

<style scoped>
.kakao_pay_button {
  width: 13vw;
  padding: 2vh 2vw 2vh 2vw;
  color: white;
  background-color: #ef2a23;
  border-radius: 5px;
  text-align: center;
  font-size: 1.4rem;
  font-weight: 600;
}

.kakao_pay_button:hover {
  background-color: #d8241f;
  cursor: pointer;
}

.kakao_pay_button:active {
  background-color: #c01e1b;
}

.align_center {
  align-self: center;
}
</style>
