!
<template>
  <div v-if="props.addressInfo">
    <div class="sub_title_wrapper">
      <div class="header_icon_1"></div>
      <div class="title-text middle-text">배송지</div>
    </div>

    <div class="align-item-center">
      <div>
        <div>{{ props.addressInfo.roadAddress }}</div>
        <div>
          <input
            type="text"
            v-model="props.addressInfo.detailAddress"
            @input="props.addressInfo.detailAddress"
            placeholder="상세 주소를 입력 해주세요."
          />
        </div>
        <div class="address_button" @click="emit('openAddressModal')">
          주소 변경
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
const emit = defineEmits(["openAddressModal"]);
const props = defineProps(["addressInfo"]);
</script>

<style scoped>
.sub_title_wrapper {
  display: flex;
  flex-direction: row;
  font-size: 1.5rem;
  margin-bottom: 10px;
}
.title-text {
  margin: auto 0;
}

.align-item-center {
  align-items: center;
}

.align-self-start {
  align-self: flex-start;
}

.address_button {
  color: #ef2a23;
  border-radius: 5px;
  border: 1px solid #ef2a23;
  padding: 0.5rem 2rem 0.5rem 2rem;
  font-weight: 400;
  transition: all 0.2s ease;
  cursor: pointer;
  margin: 20px auto;
  text-align: center;
  width: 70%;
}

.address_button:hover {
  background-color: #ef2a23;
  color: white;
}

/* 마우스를 클릭 했을 때의 효과 */
.address_button:active {
  background-color: #b81c16;
  color: white;
}

.middle-text {
  font-size: 20px;
}

.header_icon_1 {
  width: 36px;
  height: 36px;
  background: url(data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iMzYiIGhlaWdodD0iMzYiIHZpZXdCb3g9IjAgMCAzNiAzNiIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KICAgIDxnIGZpbGw9Im5vbmUiIGZpbGwtcnVsZT0iZXZlbm9kZCI+CiAgICAgICAgPHBhdGggZD0iTTM2IDM2SDBWMGgzNnoiLz4KICAgICAgICA8ZyB0cmFuc2Zvcm09InRyYW5zbGF0ZSg4LjcgNikiIHN0cm9rZT0iIzMzMyIgc3Ryb2tlLWxpbmVqb2luPSJyb3VuZCIgc3Ryb2tlLXdpZHRoPSIxLjciPgogICAgICAgICAgICA8cGF0aCBkPSJNOS4zMDYgMjRTMCAxNi41NDQgMCA5LjMwNmE5LjMwNiA5LjMwNiAwIDAgMSAxOC42MTIgMEMxOC42MTIgMTYuNTQ0IDkuMzA2IDI0IDkuMzA2IDI0eiIgc3Ryb2tlLWxpbmVjYXA9InJvdW5kIi8+CiAgICAgICAgICAgIDxjaXJjbGUgc3Ryb2tlLWxpbmVjYXA9InNxdWFyZSIgY3g9IjkuMjEyIiBjeT0iOS4xMjMiIHI9IjIuNzg0Ii8+CiAgICAgICAgPC9nPgogICAgPC9nPgo8L3N2Zz4K)
    50% 50% no-repeat;
  cursor: pointer;
}
</style>
