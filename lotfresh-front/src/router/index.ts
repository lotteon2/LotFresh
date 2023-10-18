import { createRouter, createWebHistory } from "vue-router";
import MainView from "../views/MainView.vue";
import CancelRefundDetailView from "../views/CancelRefundDetailView.vue";
import CancelRefundListView from "../views/CancelRefundListView.vue";
import OrderListView from "../views/OrderLIstView.vue"
import OrderDetailView from "../views/OrderDetailView.vue"



const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "main",
      component: MainView,
    },
    {
      path: '/mypage',
      redirect: '/mypage/order/list',
      children: [
        {
          path: 'order',
          redirect: '/mypage/order/list',
          children:[
            {
              path:'list',
              name:'orderlist', // 프로그래밍방식( router.push("orderlist")할때 쓰는 용도)
              component: OrderListView,
            },
            {
              path:'detail',
              name:'orderdetail',
              component: OrderDetailView,
            }
          ]
        },
        {
          path:'cancel-refund', 
          redirect:'/mypage/cancel-refund/list', 
          children:[
            {
              path:'list', 
              name:'cancelrefundlist', 
              component: CancelRefundListView
            }, 
            {
             path:'detail', 
             name:'cancelrefunddetail', 
             component : CancelRefundDetailView
           }
         ]
       }
     ]
   }
  ],
});

export default router;
