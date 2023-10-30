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
console.log("is initialized:" +Kakao.isInitialized());


function kakaoLogin() {
  getCode();
  const url = window.location.href;
  const codeIndex = url.indexOf('?code=');

  if (codeIndex !== -1) {
    const code = url.substring(codeIndex + 6);
    exchangeCodeForAccessToken(code);
  } 
}

function getCode() {
  Kakao.Auth.authorize({
    redirectUri: 'https://www.lot-fresh.shop',
    });
  }


function exchangeCodeForAccessToken(kakaoCode) {
  $.ajax({
    type: "POST",
    url: 'https://kauth.kakao.com/oauth/token',
    data: {
      grant_type: 'authorization_code',
      client_id: 'YOUR_CLIENT_ID', // Replace with your actual Kakao client ID
      redirect_uri: 'https://www.lot-fresh.shop', // Replace with your actual redirect URI
      code: kakaoCode,
    },
    contentType: 'application/x-www-form-urlencoded;charset=utf-8',
    dataType: 'json',
    success: function (response) {
      Kakao.Auth.setAccessToken(response.access_token);
      requestUserInfo()
    },
    error: function(response) { console.log(response)}
  });
}

  function requestUserInfo() {
  Kakao.API.request({
    url: '/v2/user/me',
  })
    .then(function (res) {
      alert(JSON.stringify(res));
      requestAdditionalInfo()
        })
    .catch(function (err) {
      alert('failed to request user information: ' + JSON.stringify(err));
      // Handle errors
    });
}

function requestAdditionalInfo(id) {
  const provider = 'KAKAO';
  const requestUrl = `https://www.lot-fresh.shop/auth-service/auth/oauth/provider/${provider}/users/${id}`;

  fetch(requestUrl)
    .then(function (response) {
      if (response.status === 301) {
        router.push({ name: 'signup', params: { id: id } });
      } else {
        const token = response.headers.get('Authorization');
        localStorage.setItem('token', token);
        router.push({ name: 'main' });
      }
    })

    .catch(function (error) {
      console.error(error);
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