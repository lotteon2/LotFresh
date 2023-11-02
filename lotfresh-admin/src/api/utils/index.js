import axios from "axios";

const BASE_URL = "https://www.lot-fresh.shop";

const axiosApi = (baseURL) => {
  const instance = axios.create({
    baseURL,
    headers: {
      "Content-Type": "application/json;charset=UTF-8",
      "Accept": "application/json",
    },
  });

  const token = localStorage.getItem("token");
  if (token) {
    instance.defaults.headers.common["Authorization"] = `Bearer ${token}`;
  }

  return instance;
};

export const defaultInstance = axiosApi(BASE_URL);
