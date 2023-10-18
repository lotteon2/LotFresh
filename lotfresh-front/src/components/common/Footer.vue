<template>
  <div class="footer">
    <div class="footer_info">
      <section class="footer_info_left">
        <h2>고객행복센터</h2>
        <div class="footer_info_primary">
          <h2>1644-0101</h2>
          월~토요일 오전 7시 - 오후 6시
        </div>
        <div class="footer_info_caption font_gray">
          <button>카카오톡 문의</button>
          <div>
            <span>월~토요일 | 오전 7시 - 오후 6시</span> <br />
            <span>일/공휴일 | 오전 7시 - 오후 1시</span> <br />
          </div>
        </div>
        <div class="footer_info_caption font_gray">
          <button>1:1 문의</button>
          <div>
            <span>365일</span> <br />
            <span>일/공휴일 | 오전 7시 - 오후 1시</span> <br />
          </div>
        </div>
        <span class="font_gray">비회원 문의 : help@lottefresh.com</span>
      </section>
      <section class="footer_info_right">
        <div class="footer_nav">
          <a href="/">롯프소개</a>
          <a href="/">롯프소개영상</a>
          <a href="/">투자정보</a>
          <a href="/">인재채용</a>
          <a href="/">이용약관</a>
          <a href="/">개인정보처리방침</a>
          <a href="/">이용안내</a>
        </div>
        <div>
          <span
            >법인명 (상호) : 롯데 프레시 | 사업자등록번호 : 261-81-23567
            <span class="info_primary">사업자 정보 확인</span></span
          >
          <br />
          <span>통신판매업: 제 2018-서울강남-01646호</span> <br />
          <span>주소: 서울 서초구 서초대로74길 33 | 대표이사: 최창효</span>
          <br />
          <span
            >채용문의:
            <span class="info_primary">recruit@lotte-fresh.com</span></span
          >
          <br />
          <span>팩스: 070 - 7500 - 6098</span>
        </div>
        <div class="wrapper">
          <a href="#" class="icon facebook">
            <div class="tooltip">Facebook</div>
            <span><i class="fab fa-facebook-f"></i></span>
          </a>
          <a href="#" class="icon twitter">
            <div class="tooltip">Twitter</div>
            <span><i class="fab fa-twitter"></i></span>
          </a>
          <a href="#" class="icon instagram">
            <div class="tooltip">Instagram</div>
            <span><i class="fab fa-instagram"></i></span>
          </a>
          <a href="#" class="icon github">
            <div class="tooltip">Github</div>
            <span><i class="fab fa-github"></i></span>
          </a>
          <a href="#" class="icon youtube">
            <div class="tooltip">Youtube</div>
            <span><i class="fab fa-youtube"></i></span>
          </a>
        </div>
      </section>
    </div>
    <section class="footer_imgs font_gray">
      <div class="footer_img">
        <img
          src="https://github.com/Hyevvy/lotbook/assets/72402747/6b9bf8a5-eb3b-4e96-becc-766d8aa44300"
        />
        <span>
          [인증범위] 롯데프레시 쇼핑몰 서비스 개발·운영 (심사받지 않은 물리적
          인프라 제외) [유효기간] 2022.01.19 ~ 2025.01.18
        </span>
      </div>
      <div class="footer_img">
        <img
          src="https://github.com/Hyevvy/lotbook/assets/72402747/6b9bf8a5-eb3b-4e96-becc-766d8aa44300"
        />
        <span>
          [인증범위] 롯데프레시 쇼핑몰 서비스 개발·운영 (심사받지 않은 물리적
          인프라 제외) [유효기간] 2022.01.19 ~ 2025.01.18
        </span>
      </div>
      <div class="footer_img">
        <img
          src="https://github.com/Hyevvy/lotbook/assets/72402747/6b9bf8a5-eb3b-4e96-becc-766d8aa44300"
        />
        <span>
          [인증범위] 롯데프레시 쇼핑몰 서비스 개발·운영 (심사받지 않은 물리적
          인프라 제외) [유효기간] 2022.01.19 ~ 2025.01.18
        </span>
      </div>
      <div class="footer_img">
        <img
          src="https://github.com/Hyevvy/lotbook/assets/72402747/6b9bf8a5-eb3b-4e96-becc-766d8aa44300"
        />
        <span>
          [인증범위] 롯데프레시 쇼핑몰 서비스 개발·운영 (심사받지 않은 물리적
          인프라 제외) [유효기간] 2022.01.19 ~ 2025.01.18
        </span>
      </div>
    </section>
    <div class="footer_rights font_gray">
      롯데프레시에서 판매되는 상품 중에는 롯데프레시에 입점한 개별 판매자가
      판매하는 마켓플레이스(오픈마켓) 상품이 포함되어 있습니다. <br />
      마켓플레이스(오픈마켓) 상품의 경우 컬리는 통신판매중개자로서 통신판매의
      당사자가 아닙니다. 롯데프레시는 해당 상품의 주문, 품질, 교환/환불 등
      의무와 책임을 부담하지 않습니다. <br />
      © LOTTE-FRESH CORP. ALL RIGHTS RESERVED
    </div>
  </div>
</template>

<script setup lang="ts"></script>

<style scoped>
h2 {
  display: inline-block;
  width: 100%;
  @media (max-width: 800px) {
    width: 200px;
  }
}
.footer {
  margin-left: -40px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: baseline;
  position: relative;
  bottom: 0;
  /* width: 100%; */
  width: 100vw;
  gap: 15px;
}

.footer_info {
  display: flex;
  align-items: center;
  flex-direction: row;
  gap: 100px;
  @media (max-width: 800px) {
    flex-direction: column;
    align-items: baseline;
    gap: 15px;
    width: 100%;
  }
}

.footer_rights {
  text-align: center;
  font-size: 10px;
  line-height: 15px;
  @media (max-width: 800px) {
    text-align: left;
    margin-top: 10px;
  }
}

.footer_info_right {
  font-size: 12px;
  line-height: 18px;
  color: #999999;
}

.footer_info_left {
  text-align: left;
  @media (max-width: 800px) {
    width: 100%;
  }
}

.footer_info_left .footer_info_caption {
  display: flex;
  align-items: center;
  gap: 10px;
  margin: 7px 0;
  text-align: left;
}

.footer_info_left h1 {
  text-align: left;
}
.footer_info_left button {
  width: 120px;
  max-width: 140px;
  max-height: 140px;
  border-radius: 3px;
  border: 1px solid #e2e2e2;
  padding: 10px;
  line-height: 20px;
  text-align: center;
}

a:link {
  text-decoration: none;
}

/* 원하는 색상있으면 바꾸세용*/
a:visited,
a:active {
  color: var(--color-text);
}

.footer_nav {
  display: flex;
  gap: 3px;
  flex-wrap: wrap;
  color: #333333;
  line-height: 18px;
  font-size: 14px;
  padding-bottom: 14px;
}

.info_primary {
  color: red;
}

.header_wrap {
  padding-left: 350px;
  display: flex;
  width: 800px;
  height: 55px;
  justify-content: space-between;
}

.footer_imgs {
  display: flex;
  justify-content: space-between;
  line-height: 14.5px;
  font-size: 10px;
  gap: 15px;
  flex-wrap: wrap;
}

.footer_imgs img {
  width: 34px;
  height: 34px;
}

.footer_img {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 3px;
  max-width: 230px;
}

.input_bar {
  padding-left: 15px;
  width: 370px;
  background-color: inherit;
  border: none;
  outline: none;
  font-size: 16px;
  letter-spacing: -0.33px;
}

.logo_img {
  top: 50px;
  width: 150px;
  height: auto;
  position: absolute;
}

.font_gray {
  color: #999999;
}

.wrapper {
  display: flex;
  margin-top: 15px;
}
.wrapper .icon {
  display: flex;
  align-items: center;
  justify-content: center;
  position: relative;
  background-color: #ffffff;
  border-radius: 50%;
  margin: 10px;
  width: 50px;
  height: 50px;
  line-height: 50px;
  font-size: 22px;
  box-shadow: 0 10px 10px rgba(0, 0, 0, 0.1);
  cursor: pointer;
  transition: all 0.2s cubic-bezier(0.68, -0.55, 0.265, 1.55);
  color: #333;
  text-decoration: none;
}
.wrapper .tooltip {
  position: absolute;
  top: 0;
  line-height: 1.5;
  font-size: 14px;
  background-color: #ffffff;
  color: #ffffff;
  padding: 5px 8px;
  border-radius: 5px;
  box-shadow: 0 10px 10px rgba(0, 0, 0, 0.1);
  opacity: 0;
  pointer-events: none;
  transition: all 0.3s cubic-bezier(0.68, -0.55, 0.265, 1.55);
}
.wrapper .tooltip::before {
  position: absolute;
  content: "";
  height: 8px;
  width: 8px;
  background-color: #ffffff;
  bottom: -3px;
  left: 50%;
  transform: translate(-50%) rotate(45deg);
  transition: all 0.3s cubic-bezier(0.68, -0.55, 0.265, 1.55);
}
.wrapper .icon:hover .tooltip {
  top: -45px;
  opacity: 1;
  visibility: visible;
  pointer-events: auto;
}
.wrapper .icon:hover span,
.wrapper .icon:hover .tooltip {
  text-shadow: 0px -1px 0px rgba(0, 0, 0, 0.1);
}
.wrapper .facebook:hover,
.wrapper .facebook:hover .tooltip,
.wrapper .facebook:hover .tooltip::before {
  background-color: #3b5999;
  color: #ffffff;
}
.wrapper .twitter:hover,
.wrapper .twitter:hover .tooltip,
.wrapper .twitter:hover .tooltip::before {
  background-color: #46c1f6;
  color: #ffffff;
}
.wrapper .instagram:hover,
.wrapper .instagram:hover .tooltip,
.wrapper .instagram:hover .tooltip::before {
  background-color: #e1306c;
  color: #ffffff;
}
.wrapper .github:hover,
.wrapper .github:hover .tooltip,
.wrapper .github:hover .tooltip::before {
  background-color: #333333;
  color: #ffffff;
}
.wrapper .youtube:hover,
.wrapper .youtube:hover .tooltip,
.wrapper .youtube:hover .tooltip::before {
  background-color: #de463b;
  color: #ffffff;
}
</style>
