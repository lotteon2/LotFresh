<template>
  <div>
    <CancelRefundHeader />
    <div class="body_wrapper">
      <CancelRefundItemInfo />
      <CancelRefundDateInfo />
      <CancelRefundAmountInfo />
    </div>
  </div>
</template>

<script>
import CancelRefundHeader from "../components/mypage/cancelrefund_detail/CancelRefundHeader.vue";
import CancelRefundItemInfo from "../components/mypage/cancelrefund_detail/CancelRefundItemInfo.vue";
import CancelRefundDateInfo from "../components/mypage/cancelrefund_detail/CancelRefundDateInfo.vue";
import CancelRefundAmountInfo from "../components/mypage/cancelrefund_detail/CancelRefundAmountInfo.vue";

export default {
  components: {
    CancelRefundHeader,
    CancelRefundItemInfo,
    CancelRefundDateInfo,
    CancelRefundAmountInfo,
  },
};
</script>

<style></style>
