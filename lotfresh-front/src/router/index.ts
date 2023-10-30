import { createRouter, createWebHistory } from "vue-router";
import MainView from "../views/MainView.vue";
import SignUpView from "@/views/user/SignUpView.vue";
import CancelRefundDetailView from "../views/CancelRefundDetailView.vue";
import CancelRefundListView from "../views/CancelRefundListView.vue";
import OrderListView from "../views/OrderLIstView.vue";
import OrderSheetView from "../views/OrderSheetView.vue";
import OrderDetailView from "../views/OrderDetailView.vue";
import OrdersView from "../views/OrdersView.vue";
import NewProuductsView from "@/views/product/NewProductsView.vue";
import BestProductsView from "@/views/product/BestProductsView.vue";
import SalesProductView from "@/views/product/SalesProductsView.vue";
import CategoryProductsView from "@/views/product/CategoryProductsView.vue";
import ProductDetailView from "@/views/product/ProductDetailView.vue";
import SearchProductsView from "@/views/product/SarchProductsView.vue";
import CartView from "../views/CartView.vue";
import KakaopaySuccessPopUpView from "@/views/payment/KakaopaySuccessPopUpView.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "main",
      component: MainView,
    },
    {
      path: "/mypage",
      redirect: "/mypage/order/list",
      children: [
        {
          path: "order",
          redirect: "/mypage/order/list",
          children: [
            {
              path: "list",
              name: "orderlist", // 프로그래밍방식( router.push("orderlist")할때 쓰는 용도)
              component: OrdersView,
            },
            {
              path: "detail",
              name: "orderdetail",
              component: OrderDetailView,
            },
          ],
        },
        {
          path: "cancel-refund",
          redirect: "/mypage/cancel-refund/list",
          children: [
            {
              path: "list",
              name: "cancelrefundlist",
              component: CancelRefundListView,
            },
            {
              path: "detail",
              name: "cancelrefunddetail",
              component: CancelRefundDetailView,
            },
          ],
        },
      ],
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
      path: "/market-time-sales",
      name: "market-time-sales",
      component: SalesProductView,
    },
    {
      path: "/categories/:id",
      name: "category-products",
      component: CategoryProductsView,
    },
    {
      path: "/search",
      name: "search-products",
      component: SearchProductsView,
    },
    {
      path: "/goods/:id",
      name: "goods",
      component: ProductDetailView,
    },

    {
      path: "/cart",
      name: "cart",
      component: CartView,
    },

    {
      path: "/payment-result",
      redirect: "/notfound",
      children: [
        {
          path: "success",
          redirect: "/notfound",
          children: [
            {
              path: ":orderId",
              name: "paymentResult",
              component: KakaopaySuccessPopUpView,
            },
          ],
        },
      ],
    },

    {
      path:"/signup:id",
      name:"signup",
      component: SignUpView,
    },
  ],
});

export default router;
