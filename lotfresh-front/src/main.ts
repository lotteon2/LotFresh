import "normalize.css";
import "./assets/main.css";

import { createApp } from "vue";
import { createPinia } from "pinia";
//@ts-ignore
import piniaPersist from "pinia-plugin-persist";

import App from "./App.vue";
import router from "./router";

import ElementPlus from "element-plus";
import "element-plus/dist/index.css";

const pinia = createPinia();
pinia.use(piniaPersist);

const app = createApp(App);
app.use(pinia);
app.use(router);
app.use(ElementPlus);
app.mount("#app");



declare module "@vue/runtime-core" {
  export interface ComponentCustomProperties {
    $goto: any;
  }
}

window.Kakao.init("5dca3ee52a5c5e81b0415473b05366f0")
