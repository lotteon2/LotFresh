import { createRouter, createWebHistory } from "vue-router";
import MainView from "../views/MainView.vue";
import NewProuductsView from "../views/NewProductsView.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "main",
      component: MainView,
    },
    {
      path: "/market-newproduct",
      name: "market-newproduct",
      component: NewProuductsView,
    },
  ],
});

export default router;
