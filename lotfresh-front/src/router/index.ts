import { createRouter, createWebHistory } from "vue-router";
import MainView from "../views/MainView.vue";
import OrderSheetView from "../views/OrderSheetView.vue";
import OrderDetailView from "../views/OrderDetailView.vue";
import OrdersView from "../views/OrdersView.vue";
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "main",
      component: MainView,
    },
    {
      path: "/ordersheet",
      name: "ordersheet",
      component: OrderSheetView,
    },
    {
      path: "/orderdetail/:orderId",
      name: "orderdetail",
      component: OrderDetailView,
    },
    {
      path: "/orders",
      name: "orders",
      component: OrdersView,
    },
  ],
});

export default router;
