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
    console.log("토큰입니다###############");
    console.log(token);
  }

  return instance;
};

export const defaultInstance = axiosApi(BASE_URL);
