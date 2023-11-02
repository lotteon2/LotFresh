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

import VueSweetalert2 from "vue-sweetalert2";
import "sweetalert2/dist/sweetalert2.min.css";

const pinia = createPinia();
pinia.use(piniaPersist);

const app = createApp(App);
app.use(pinia);
app.use(router);
app.use(ElementPlus);
app.use(VueSweetalert2);
app.mount("#app");

declare module "@vue/runtime-core" {
  export interface ComponentCustomProperties {
    $goto: any;
  }
}
