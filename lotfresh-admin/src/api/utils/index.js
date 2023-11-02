// import axios, { HttpStatusCode } from "axios";

// const BASE_URL = "https:\\www.lot-fresh.shop";
// // 기본 API 요청 처리
// const axiosApi = (baseURL) => {
//   const instance = axios.create({
//     baseURL,
//      withCredentials: true,
//      headers: {
//         "Content-Type": `application/json;charset=UTF-8`,
//         "Accept": "application/json",
//         "Access-Control-Allow-Origin": `https://www.lot-fresh.shop`,
//         'Access-Control-Allow-Credentials':"true",
//     }
//   });

// }


// export const defaultInstance = axiosApi(BASE_URL);


// import axios from "axios";

// const BASE_URL = "https://www.lot-fresh.shop";

// // 기본 API 요청 처리
// const axiosApi = (baseURL) => {
//   return axios.create({
//     baseURL,
//     withCredentials: true,
//     headers: {
//       "Content-Type": "application/json;charset=UTF-8",
//       "Accept": "application/json",
//     },
//   });
// };

// export const defaultInstance = axiosApi(BASE_URL);


import axios from "axios";

const BASE_URL = "https://www.lot-fresh.shop";

// 기본 API 요청 처리
const axiosApi = (baseURL) => {
  const instance = axios.create({
    baseURL,
    withCredentials: true,
    headers: {
      "Content-Type": "application/json;charset=UTF-8",
      "Accept": "application/json",
    },
  });

  // 토큰을 가져와 헤더에 포함시킵니다.
  const token = localStorage.getItem("token"); // 토큰을 로컬 스토리지에서 가져옵니다.
  if (token) {
    instance.defaults.headers.common["Authorization"] = `Bearer ${token}`;
  }

  return instance;
};

export const defaultInstance = axiosApi(BASE_URL);
