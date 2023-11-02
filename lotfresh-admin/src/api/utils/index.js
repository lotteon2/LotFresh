import { WidthWideTwoTone } from "@mui/icons-material";
import axios, { HttpStatusCode } from "axios";

const BASE_URL = "https:\\www.lot-fresh.shop";
// 기본 API 요청 처리
const axiosApi = (baseURL) => {
  const instance = axios.create({
    baseURL,
     withCredentials: true,
     headers: {
        "Content-Type": `application/json;charset=UTF-8`,
        "Accept": "application/json",
        "Access-Control-Allow-Origin": `https://www.lot-fresh.shop`,
        'Access-Control-Allow-Credentials':"true",
    }
  });
  
  instance.interceptors.request.use((config) => {
    // 로컬 스토리지에서 토큰을 불러옵니다.
    const token = localStorage.getItem('token'); 
    if (token) {
        config.headers['Authorization'] = `Bearer ${token}`;
    }
    return config;
  }, (error) => {
    return Promise.reject(error);
  });

  return instance;
};



export const defaultInstance = axiosApi(BASE_URL);