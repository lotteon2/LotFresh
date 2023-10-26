declare module "*.vue" {
  import type { DefineComponent } from "vue";
  const component: DefineComponent<{}, {}, any>;
  export default component;
}

declare module "vue-daum-postcode" {
  import { DefineComponent } from "vue";
  const VueDaumPostcode: DefineComponent;
  export { VueDaumPostcode };
}
