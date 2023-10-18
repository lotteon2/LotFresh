<template>
  <div class="menubar-box">
    <div class="inner-menu">
      <ul class="menu-item">
        <li class="menu1">
          <span class="menu-inner-span"
            ><span class="menu-img"></span
            ><span class="txt">전체 카테고리</span></span
          >
          <div
            class="items"
            style="position: absolute; flex-direction: column; top: 60px"
          >
            <ul class="item-list">
              <li>
                <div class="category-box">
                  <img
                    style="width: 25px; height: 25px"
                    src="https://img-cf.kurly.com/category/icon/pc/25040bd0-5889-4d8c-b863-13f71750a8e6"
                  /><a href="#">채소</a>
                </div>
              </li>

              <li>
                <div class="category-box">
                  <img
                    style="width: 25px; height: 25px"
                    src="https://img-cf.kurly.com/category/icon/pc/f68cee74-477c-44da-8c87-62618e9d55e5"
                  /><a href="#">과일·견과·쌀</a>
                </div>
              </li>

              <li>
                <div class="category-box">
                  <img
                    style="width: 25px; height: 25px"
                    src="https://img-cf.kurly.com/category/icon/pc/c4256803-f1ec-4ac5-87a5-b68d3d23f6e0"
                  /><a href="#">수산·해산·건어물</a>
                </div>
              </li>

              <li>
                <div class="category-box">
                  <img
                    style="width: 25px; height: 25px"
                    src="https://img-cf.kurly.com/category/icon/pc/34168b18-94ee-4946-8430-0a6239054ad6"
                  /><a href="#">정육·계란</a>
                </div>
              </li>

              <li>
                <div class="category-box">
                  <img
                    style="width: 25px; height: 25px"
                    src="https://img-cf.kurly.com/category/icon/pc/3067199c-f080-4205-b7a4-bb1b6eef5d12"
                  /><a href="#">국·반찬·메인요리</a>
                </div>
              </li>

              <li>
                <div class="category-box">
                  <img
                    style="width: 25px; height: 25px"
                    src="https://img-cf.kurly.com/category/icon/pc/82ae12ba-4876-455d-82e8-c6997613559b"
                  /><a href="#">샐러드·간편식</a>
                </div>
              </li>

              <li>
                <div class="category-box">
                  <img
                    style="width: 25px; height: 25px"
                    src="https://img-cf.kurly.com/category/icon/pc/28f2b10d-a0b9-4ecc-bb51-325d01197326"
                  /><a href="#">면·양념·오일</a>
                </div>
              </li>

              <li>
                <div class="category-box">
                  <img
                    style="width: 25px; height: 25px"
                    src="https://img-cf.kurly.com/category/icon/pc/df435e92-3356-4717-b765-567ed9fb9fc1"
                  /><a href="#">생수·음료·우유·커피</a>
                </div>
              </li>

              <li>
                <div class="category-box">
                  <img
                    style="width: 25px; height: 25px"
                    src="https://img-cf.kurly.com/category/icon/pc/15449666-5758-44db-bb71-620d2736664c"
                  /><a href="#">간식·과자·떡</a>
                </div>
              </li>

              <li>
                <div class="category-box">
                  <img
                    style="width: 25px; height: 25px"
                    src="https://img-cf.kurly.com/category/icon/pc/d0f84ec7-7391-44e7-ad4d-962ba4b52443"
                  /><a href="#">베이커리·치즈·델리</a>
                </div>
              </li>
            </ul>
          </div>
        </li>

        <li class="menu2">
          <span class="menu-inner-span">
            <span class="txt">신상품</span>
          </span>
        </li>
        <li class="menu3">
          <span class="menu-inner-span"><span class="txt">베스트</span></span>
        </li>

        <li class="menu4">
          <span class="menu-inner-span"
            ><span class="txt">마감 임박</span></span
          >
        </li>
      </ul>
    </div>
  </div>
</template>

<script setup lang="ts"></script>

<style scoped>
ul {
  list-style-type: none;
}
.menu {
  display: block;
  overflow: hidden;
  position: absolute;
  left: -9999px;
  width: 1px;
  height: 1px;
  font-size: 0;
  line-height: 0;
  text-indent: -9999px;
}

#menubar {
  height: 50px;
}

/*#menubar*/
.menubar-box {
  position: relative;
  z-index: 300;
  min-width: 1050px;
  background-color: #fff;
  letter-spacing: -0.3px;
}

/*#menubar */
.inner-menu {
  position: relative;
  width: 1050px;
  height: 56px;
  margin: 0 auto;
  border-top: 1px solid #e2e2e2;
  border-bottom: 1px solid #e2e2e2;
}

/*#menubar .menubar-box .inner-menu */
.txt {
  font-weight: 700;
  border-bottom-style: none;
}

.menu-img {
  float: left;
  width: 16px;
  height: 14px;
  margin: 8px 0 0 0px;
  background: url("../../assets/menu.png") no-repeat;
  background-size: 15px;
}

.menu1 a {
  width: 230px;
}

.menu5 a {
  width: 100px;
}

.menu-item a {
  overflow: hidden;
  float: left;
  width: 180px;
  height: 55px;
  padding-left: 55px;
  font-size: 16px;
  color: #333;
  text-align: center;
  text-decoration: none;
}

.menu-inner-span {
  overflow: hidden;
  float: left;
  width: 180px;
  height: 55px;
  padding-left: 55px;
  font-size: 16px;
  color: #333;
  text-align: center;
  text-decoration: none;
}

.menu-inner-span .txt:hover {
  cursor: pointer;
  text-decoration: underline;
  text-decoration-color: #333;
  color: #ef2a23;
}

.items ul {
  width: 25%;
  text-align: center;
  line-height: 20px;
  height: 50px;
  display: none;
}

.items .item-list li {
  width: 200px;
  padding-left: 10px;
  background-color: #fff;
}

.items li a {
  text-align: left;
  padding-left: 20px;
  display: block;
}

.menu1:hover .items ul {
  display: block;
}

.menu1:focus .items ul {
  display: block;
}

.category-box {
  display: inline-flex;
  height: 40px;
}
.item-list {
  padding: 0;
}
</style>
