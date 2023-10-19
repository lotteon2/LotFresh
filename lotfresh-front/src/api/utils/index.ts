import axios from "axios";

const BASE_URL = "http://localhost:8083";
const ORDER_URL = "http://localhost:80";

// 기본 API 요청 처리
const axiosApi = (baseURL: string) => {
  const instance = axios.create({
    baseURL,
  });
  return instance;
};

export const defaultInstance = axiosApi(BASE_URL);
export const orderInstance = axiosApi(ORDER_URL);
