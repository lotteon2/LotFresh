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

<script>
import axios from 'axios'; // Import Axios for HTTP requests
import { defineEmits } from 'vue';

export default {
  setup() {
    const emits = defineEmits(['closeModal']);

    async function kakaoLogin() {
      getCode()
    }

     function getCode() {
  const clientId = '5dca3ee52a5c5e81b0415473b05366f0';
  const redirectUri = 'https://www.lot-fresh.shop/auth-service/auth/oauth/provider/KAKAO';
  const authorizationUrl = `https://kauth.kakao.com/oauth/authorize?client_id=${clientId}&redirect_uri=${redirectUri}&response_type=code`;
  window.open(authorizationUrl, '_blank');
    }

  }}

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