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
import axios from 'axios';

const emits = defineEmits(['closeModal']);
console.log(Kakao.isInitialized());

function kakaoLogin() {
  Kakao.Auth.login({
    success: function(auth) {
      Kakao.API.request({
        url: '/v2/user/me',
        success: function(response) {
          var userId = response.id; // Assuming that 'id' is the user's ID
          console.log(userId);
          console.log(response);

          var url = "https://www.lot-fresh.shop/auth-service/auth/oauth/provider/KAKAO/users/" + userId;

          axios
            .post(url, {})
            .then(function(response) {
              // Check the status code for redirection
              if (response.status === 301) {
                console.log("redirect to sign-up page")
                router.push({ name: 'signup', params: { userId } });
              } else {
                router.push({ name: 'main' });
              }
            })
            .catch(function(error) {
              console.error("Error sending the new request:", error);
            });
        },
        fail: function(error) {
          console.error("Error sending the new request:", error);
        }
      });
    },
    fail: function(error) {
      console.error("Error sending the new request:", error);
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