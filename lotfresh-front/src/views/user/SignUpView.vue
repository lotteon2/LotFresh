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
            <label for="name" class="large-font-input">이름</label>
            <span class="check-span">*</span>
          </td>
          <td>
            <input
              v-model="name"
              placeholder="이름"
              type="text"
              @input="signUpName"
            />
          </td>
        </tr>
        <tr>
          <td>
            <label class="large-font-input">연락처</label>
            <span class="check-span">*</span>
          </td>
          <td>
            <input
              v-model="contactNumber"
              placeholder="연락처"
              type="tel"
              @input="signUpContact"
            />
          </td>
        </tr>
        <tr>
          <td>
            <label class="large-font-input">권역</label>
            <span class="check-span">*</span>
          </td>
          <td>
            <input v-model="province" placeholder="권역" readonly />
          </td>
        </tr>
        <tr>
          <td>
            <label class="large-font-input">상세주소</label>
            <span class="check-span">*</span>
          </td>
          <td>
            <input v-model="localAddress" placeholder="상세주소" readonly />
          </td>
        </tr>
        <tr>
          <td>
            <label class="large-font-input">우편번호</label>
            <span class="check-span">*</span>
          </td>
          <td>
            <input class="zoncode-input" v-model="zoncode" placeholder="우편번호" readonly />
          </td>
          <td>
            <button class="addAddressButton" @click="execDaumPostcode">주소 추가</button>
          </td>
        </tr>
      </tbody>
    </table>
    <button class="signup-button" @click="SignUp">회원가입</button>
  </div>
</template>


<script>
import main from "@/main.ts"

export default {

  props: {
    showModal: Boolean,
    modalMessage: "추가 정보를 입력해야합니다."
  },


  data() {
    return {
      name: "",
      contactNumber: "",
      localAddress: "",
      province: "",
      zoncode: "",
      nameValidMessage: "",
      contactValidMessage: "",
      addressValidMessage: "",
    };
  },
  computed: {
    address() {
      return address
    },
    Address() {
      return Address
    },
    nameValidStyle() {
      return {color: this.nameValidMessage ? "red" : ""};
    },
    contactValidStyle() {
      return {color: this.contactValidMessage ? "red" : ""};
    },
    addressValidStyle() {
      return {color: this.addressValidMessage ? "red" : ""};
    },
  },
  methods: {
    closeModal() {
      this.$emit('close-modal');
    },

    execDaumPostcode() {
      const that = this;
      new window.daum.Postcode({
        oncomplete: (data) => {
          that.province = data.sidoEnglish;
          that.localAddress = data.roadAddressEnglish;
          that.zoncode = data.zonecode;
        }
      }).open()},

    signUpName() {
      if (this.name.length >= 2 && this.name.length < 5) {
        this.nameValidMessage = "";
      } else {
        this.nameValidMessage = "이름을 2글자 이상 5글자 미만으로 입력하세요.";
      }
    },
    signUpContact() {
      // Regex pattern for "000-0000-0000" format
      const pattern = /^\d{3}-\d{4}-\d{4}$/;
      if (pattern.test(this.contactNumber)) {
        this.contactValidMessage = "";
      } else {
        this.contactValidMessage = "000-0000-0000 형식으로 입력하세요.";
      }
    },
    signup() {
      if (this.isNameValid && this.isContactValid) {

          // window. 에러메시지 전송
      }
    },
  },
};
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
  font-family: 'Noto Sans KR', sans-serif;
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
   font-family: 'Noto Sans KR', sans-serif;
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
