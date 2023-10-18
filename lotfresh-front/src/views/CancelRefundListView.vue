
<template>
  <div>
    <CancelRefundHeader></CancelRefundHeader>
    <div class="body_wrapper">
      <CancelRefundItemList></CancelRefundItemList>

    </div>
  </div>
</template>

<script>
import CancelRefundHeader from "../components/mypage/cancelrefund_list/CancelRefundHeader.vue"
import CancelRefundItemList from "../components/mypage/cancelrefund_list/CancelRefundItemList.vue"
export default {
    components: {
    CancelRefundHeader,
    CancelRefundItemList
  }
}
</script>

<style scoped>
.body_wrapper{
  margin:3%; 
}

</style>