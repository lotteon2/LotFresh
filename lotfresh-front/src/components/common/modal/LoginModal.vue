<template>
  <div>
    <div id="login-modal">
      <div id="close-button">
        <img
          src="@/assets/close-button.svg"
          width="18"
          height="18"
          id="close-icon"
          @click="emits('closeModal')"
        />
      </div>

      <div id="lot-title">
        <span>롯프</span>
      </div>
      <div id="login-button">
        <img
          class="kakao-btn"
          src="@/assets/kakao-login-btn.png"
          @click="kakaoLogin"
        />
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { defineEmits } from "vue";
const emits = defineEmits(["closeModal"]);
const kakaoLogin = () => {
  window.location.replace(
    // "https://lot-fresh.shop/user-service/oauth2/authorization/kakao"
    "http://localhost:8090/user-service/oauth2/authorization/kakao"
  );
};
</script>
<style scoped>
#login-modal {
  background-color: white;
  width: 377px;
  height: 426px;
  border-radius: 10px;
  text-align: center;
}

#login-modal span {
  font-size: 96px;
}

#close-button {
  text-align: right;
  margin-right: 13px;
  margin-top: 50px;
}

#lot-title {
  margin-top: 45px;
}

#close-icon {
  margin-top: 15px;
}

.kakao-btn {
  margin-top: 75px;
}

img:hover {
  cursor: pointer;
}
</style>
