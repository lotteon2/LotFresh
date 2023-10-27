<template>
  <div>
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
          "http://localhost:5173" // "https://www.lot-fresh.shop"로 수정 필요 😃
        );
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

<style scoped></style>
