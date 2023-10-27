<template>
  <div id="modal">
    <div id="modalBackground" @click="emits('closeModal')"></div>
    <login-modal class="modalContent" @closeModal="emits('closeModal')" />
  </div>
</template>

<script setup lang="ts">
const emits = defineEmits(["closeModal"]);
</script>

<style scoped>
#modal {
  position: fixed;
  width: 100%;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  z-index: 500;
}
#modalBackground {
  width: 100%;
  height: 100%;
  background-color: black;
  opacity: 0.5;
}
.modalContent {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}
</style>
