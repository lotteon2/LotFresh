<template>
  <div class="user-container">
    <h3 class="title">추가 정보 입력</h3>
    <div class="required-box">
      <span class="check-span">*</span> 필수입력사항
    </div>
    <div class="line"></div>
    <table class="signup-table">
      <tbody>
        <tr>
          <td>
            <label class="large-font-input">우편번호</label>
            <span class="check-span">*</span>
          </td>
          <td>
            <input
              class="zoncode-input"
              v-model="memberCreateDto.zoncode"
              placeholder="우편번호"
              readonly
              required
            />
          </td>

          <td>
            <button class="addAddressButton" @click="execDaumPostcode">
              주소 추가
            </button>
          </td>
        </tr>
        <tr>
          <td>
            <label class="large-font-input">권역</label>
            <span class="check-span">*</span>
          </td>
          <td>
            <input
              v-model="memberCreateDto.province"
              placeholder="권역"
              readonly
              required
            />
          </td>
        </tr>
        <tr>
          <td>
            <label class="large-font-input">도로명 주소</label>
            <span class="check-span">*</span>
          </td>
          <td>
            <input
              v-model="memberCreateDto.roadAddress"
              placeholder="도로명 주소"
              readonly
              required
            />
          </td>
        </tr>
        <tr>
          <td>
            <label class="large-font-input">상세 주소</label>
            <span class="check-span">*</span>
          </td>
          <td>
            <input
              v-model="memberCreateDto.addressDetail"
              placeholder="상세 주소"
            />
          </td>
        </tr>
      </tbody>
    </table>
    <button class="signup-button" @click="signup">회원가입</button>
  </div>
</template>

<script setup lang="ts">
import { useMemberStore } from "@/stores/member";
import { ref } from "vue";
import type { MemberCreateDto } from "@/interface/memberInterface";
const memberStore = useMemberStore();
const memberCreateDto = ref<MemberCreateDto>({
  roadAddress: "",
  province: "",
  zoncode: "",
  addressDetail: "",
});

const execDaumPostcode = () => {
  new window.daum.Postcode({
    oncomplete: (data: any) => {
      memberCreateDto.value.province = data.sidoEnglish;
      memberCreateDto.value.roadAddress = data.roadAddress;
      memberCreateDto.value.zoncode = data.zonecode;
    },
  }).open();
};

const signup = () => {
  if (!memberCreateDto.value.province) {
    alert("주소 입력은 필수 사항 입니다.");
    return;
  }
</script>

<style scoped>
.container {
  width: 640px;
  display: flex;
  align-items: center;
  margin: 0 auto;
  justify-content: center;
  flex-direction: column;
  padding: 5px 0px 120px 0px;
  font-family: "Noto Sans KR", sans-serif;
}

.title {
  font-size: 28px;
  text-align: center;
  margin-top: 10px;
}

.required-box {
  text-align: right;
  width: 100%;
  margin: 10px 0px 0px 0px;
}

.check-span {
  color: #ee6a7b;
}

.line {
  display: block;
  width: 100%;
  height: 2px;
  background-color: #333333;
  margin-top: -2px;
}

.signup-table {
  margin-top: 10px;
  padding-bottom: 49px;
  width: 100%;
}

.signup-table tr {
  text-align: left;
  font-size: 14px;
  font-weight: 500;
}

.signup-table td {
  position: relative;
  padding-bottom: 16px;
}

.signup-table td:nth-child(1) {
  box-sizing: border-box;
  padding: 15px 0px 0px 18px;
  width: 152px;
  vertical-align: top;
}

.info-ul {
  font-size: 16px;
  color: #666666;
  left: -37px;
  font-weight: 400;
  list-style: none;
  margin-top: 0px;
  margin-left: 0px;
}

.signup-button {
  width: 240px;
  height: 56px;
  background-color: #f1575b;
  border-radius: 3px;
  border: 0 solid #8c8c8c;
  cursor: pointer;
  text-align: center;
  font-size: 16.5px;
  color: #ffffff;
  margin: 1px 0 0 0;
  font-weight: bold;
}
.large-font-input {
  font-size: 16px; /* Adjust the font size as needed */
}

.user-container {
  width: 640px;
  display: flex;
  align-items: center;
  margin: 0 auto;
  justify-content: center;
  flex-direction: column;
  padding: 5px 0px 120px 0px;
  font-family: "Noto Sans KR", sans-serif;
}

.title {
  font-size: 28px;
  text-align: center;
  margin-top: 10px;
}

.required-box {
  text-align: right;
  width: 100%;
  margin: 10px 0px 0px 0px;
}

.check-span {
  color: #ee6a7b;
}

.line {
  display: block;
  width: 100%;
  height: 2px;
  background-color: #333333;
  margin-top: -2px;
}

.signup-table {
  margin-top: 10px;
  padding-bottom: 49px;
  width: 100%;
}

.signup-table tr {
  text-align: left;
  font-size: 14px;
  font-weight: 500;
}

.signup-table td {
  position: relative;
  padding-bottom: 16px;
}

.signup-table td:nth-child(1) {
  box-sizing: border-box;
  padding: 15px 0px 0px 18px;
  width: 152px;
  vertical-align: top;
}

.signup-button {
  width: 240px;
  height: 56px;
  background-color: #f1575b;
  border-radius: 3px;
  border: 0 solid #8c8c8c;
  cursor: pointer;
  text-align: center;
  font-size: 16.5px;
  color: #ffffff;
  margin: 1px 0 0 0;
  font-weight: bold;
}
.large-font-input {
  font-size: 16px; /* Adjust the font size as needed */
}
input {
  padding: 10px; /* Increase padding to make the input bigger */
  border-radius: 8px; /* Set border-radius to make it more circular */
  //width: 80%; /* Optionally, set the width to 80% to provide some space for the error message */
  box-sizing: border-box; /* Include padding and border in the width calculation */
  width: 100%;
}

/* Style the error message */
.info-ul {
  font-size: 12px;
  color: #ff0000; /* Set the color to red for error messages */
  font-weight: 400;
  list-style: none;
  margin-top: 0px;
  margin-left: 150%;
  width: 100%; /* Set a fixed width for the error message container */
  display: block;
  text-align: center; /* Center the text within the container */
}

.addAddressButton {
  width: auto; /* Let the button size be based on content */
  background-color: #f1575b; /* Set background color */
  border-radius: 28px; /* Adjust border-radius to make it circular */
  cursor: pointer;
  padding: 10px 20px; /* Adjust padding to make it bigger */
  color: #ffffff; /* Text color */
  outline: none; /* Remove the focus outline */
}

.input-container {
  display: flex;
  align-items: center;
}

.zoncode-input {
  margin-right: 10px; /* Adjust the margin to make it closer to the button */
  width: 100%; /* Take the available width */
}
</style>
