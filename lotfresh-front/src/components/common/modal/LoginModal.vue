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
import { defineEmits } from 'vue';

const emits = defineEmits(['closeModal']);
console.log(Kakao.isInitialized());

function kakaoLogin() {
  Kakao.Auth.login({
    success: function(auth) {
      Kakao.API.request({
        url: '/v2/user/me',
        success: function(response) {
          var userId = response.id; 
          console.log(userId);
          console.log(response);

          var url = "https://lot-fresh.shop/auth/oauth/provider/KAKAO/users/" + userId;

          $.ajax({
            type: "POST", 
            url: url,
            success: function(newUserResponse) {
              console.log(newUserResponse);
            },
            error: function(error) {
              console.error("Error sending the new request:", error);
            }
          });
        },
        fail: function(error) {
          console.log("카카오 로그인 에러")
        }
      });
    },
    fail: function(error) {
      console.log("카카오 로그인 에러")
    }
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