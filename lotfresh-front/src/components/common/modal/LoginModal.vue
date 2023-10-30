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

<script setup>
import { defineEmits } from "vue";
const emits = defineEmits(["closeModal"]);
window.Kakao.init('5dca3ee52a5c5e81b0415473b05366f0');

function kakaoLogin() {
  window.Kakao.Auth.login({
    scope: "profile_nickname, account_email",
    success: (authObj) => {
      window.Kakao.API.request({
        url: "/v2/user/me",
        success: async (res) => {
          console.log(res);

          const email = res.kakao_account.email;
          const nickname = res.properties.nickname;

          // Make a POST request to your server for KAKAO login
          $.post("member-rest.bit?cmd=kakaoLogin", {
            email: email,
            nickname: nickname
          }, async function () {
            // After successful login, fetch user data and handle redirection
            const provider = 'KAKAO';
            const id = res.id;
            const requestUrl = `https://www.lot-fresh.shop/auth-service/auth/oauth/provider/${provider}/users/${id}`;

            const response = await fetch(requestUrl);

            if (response.status === 301) {
              router.push({ name: 'signup', params: { id: id } });
            } else {
              const token = response.headers.get('Authorization');
              localStorage.setItem('token', token);
              router.push({ name: 'main' });
            }
          }).fail(function (err) {
            console.log(err);
          });
        },
        fail: (res) => {
          console.log(res);
        },
      });
    },
  });
}
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