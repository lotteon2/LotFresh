import axios from "axios";

const BASE_URL = "http://localhost:8083";

// 기본 API 요청 처리
const axiosApi = (baseURL: string) => {
  const instance = axios.create({
    baseURL,
    withCredentials: true,
  });
  return instance;
};

export const defaultInstance = axiosApi(BASE_URL);
