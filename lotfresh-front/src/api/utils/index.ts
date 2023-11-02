import axios from "axios";

// const BASE_URL = "http://localhost:8083";
const BASE_URL = "https://www.lot-fresh.shop/";
const PRODUCT_URL = "https://www.lot-fresh.shop/product-service/";
// const PRODUCT_URL = "http://localhost:8090/product-service/";
// const ORDER_URL = "http://localhost:8084";
const MEMBER_URL = "https://www.lot-fresh.shop/user-service/";
// const MEMBER_URL = "http://localhost:8090/user-service/";
const ORDER_URL = "https://www.lot-fresh.shop/order-service/";
// const ORDER_URL = "http://localhost:8084";
const CART_URL = "https://www.lot-fresh.shop/cart-service/";

// 기본 API 요청 처리
const axiosApi = (baseURL: string) => {
  const instance = axios.create({
    baseURL,
  });
  return instance;
};

export const defaultInstance = axiosApi(BASE_URL);
export const productInstance = axiosApi(PRODUCT_URL);
export const orderInstance = axiosApi(ORDER_URL);
export const cartInstance = axiosApi(CART_URL);
export const memberInstance = axiosApi(MEMBER_URL);
