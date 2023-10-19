import { createRouter, createWebHistory } from "vue-router";
import MainView from "@/views/MainView.vue";
import NewProuductsView from "@/views/NewProductsView.vue";
import BestProductsView from "@/views/BestProductsView.vue";
import ProductDetailView from "@/views/ProductDetailView.vue";
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
    {
      path: "/market-best",
      name: "market-best",
      component: BestProductsView,
    },
    {
      path: "/goods/:id",
      name: "goods",
      component: ProductDetailView,
    },
  ],
});

export default router;
