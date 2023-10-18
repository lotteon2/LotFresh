<template>
  <div>
    <CancelRefundHeader/>
    <div class="body_wrapper">
      <CancelRefundItemInfo/>

    </div>
  </div>
  
</template>

<script>
import CancelRefundHeader from "../components/mypage/cancelrefund_detail/CancelRefundHeader.vue"
import CancelRefundItemInfo from "../components/mypage/cancelrefund_detail/CancelRefundItemInfo.vue"

export default {
    components: {
    CancelRefundHeader,
    CancelRefundItemInfo,
    }
}
</script>

<style>

</style>