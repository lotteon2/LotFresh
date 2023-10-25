<template>
  <section
    class="post-box-wrapper"
    v-on:click="closeModal"
    v-if="isAddressModalOpen"
  >
    <div class="post-box" v-on:click.stop>
      <VueDaumPostcode @complete="oncomplete" v-if="isAddressModalOpen" />
    </div>
    <!-- <div class="form-box">
      <input disabled v-model="address" />
      <div v-on:click="search">검색</div>
    </div> -->
  </section>
</template>

<script lang="ts">
import { VueDaumPostcode } from "vue-daum-postcode";
import { watch } from "vue";

export default {
  name: "test",
  data() {
    return {
      address: null,
      // postOpen: false,
      // isAddressModalOpen: false,
    };
  },

  props: {
    isAddressModalOpen: Boolean,
  },

  components: {
    VueDaumPostcode,
  },

  mounted() {
    watch(
      () => this.isAddressModalOpen,
      (newValue, oldValue) => {
        console.log(`postOpen changed from ${oldValue} to ${newValue}`);
      }
    );
  },

  methods: {
    oncomplete: function (result: any): void {
      if (result.userSelectedType === "R") {
        console.log(result);
        // 도로명 주소 선택
        this.address = result.roadAddress;
      } else {
        // 지번 주소 선택
        console.log(result);
        this.address = result.jibunAddress;
      }
      // this.isAddressModalOpen = false;
    },
    closeModal: function (): void {
      console.log("closed!!");
      this.$emit("update:isAddressModalOpen", false);
    },
  },
};
</script>

<style scoped>
.post-box-wrapper {
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  z-index: 1;
  background: rgba(0, 0, 0, 0.5);
}

.post-box {
  position: fixed;
  top: 50%;
  left: 50%;
  width: 30vw;
  height: 25vw;
  overflow: auto;
  transform: translate(-50%, -50%);
  z-index: 2;
}
</style>
