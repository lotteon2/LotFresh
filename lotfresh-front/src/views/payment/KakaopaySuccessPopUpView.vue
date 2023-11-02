<template>
  <div class="fullscreen">
    <div>결제 성공하셨습니다.</div>
    <div>{{ countdown }}초 뒤 영수증 페이지로 이동합니다.</div>
  </div>
</template>

<script>
import { onMounted, ref } from "vue";
import { useRoute, useRouter } from "vue-router";

export default {
  setup() {
    const route = useRoute();
    const router = useRouter();
    const orderId = ref(route.params.orderId);
    const countdown = ref(3);

    onMounted(() => {
      // 부모 창으로 메시지 전달
      setTimeout(() => {
        window.opener.postMessage(
          {
            routeName: "orderdetail",
            params: {
              orderId: orderId.value,
            },
          },
          "https://lot-fresh.shop" // "https://www.lot-fresh.shop"로 수정 필요 😃
        );
        // clearInterval(intervalId);
      }, 3000);

      // 1초마다 카운트다운
      const intervalId = setInterval(() => {
        countdown.value--;
        if (countdown.value === 0) {
          clearInterval(intervalId);
          window.close();
        }
      }, 1000);
    });

    return {
      countdown,
    };
  },
};
</script>

<style scoped>
.fullscreen {
  position: fixed;
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
  z-index: 9999;
  width: 500px;
  height: 600px;
  background-color: white;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-content: center;
  text-align: center;
  gap: 25px;
  font-size: 1.5rem;
}

.text_center {
}
</style>
