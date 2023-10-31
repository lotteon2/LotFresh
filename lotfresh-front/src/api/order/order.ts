import { defaultInstance, orderInstance } from "../utils";

export interface ProductRequest {
  productId: number;
  productPrice: number;
  productStock: number;
  productName: string;
  productThumbnail: string;
}

export interface OrderCreateRequest {
  productRequests: ProductRequest[];
  isFromCart: boolean;
  province: String;
}

// interface qrcodeUrl {
//   qrcodeUrl: string;
// }

export const startKakaopay = async (
  orderData: OrderCreateRequest
): Promise<string> => {
  const accessToken = localStorage.getItem("accessToken");

  const { data } = await orderInstance.post(`/order`, orderData, {
    headers: { Authorization: `Bearer ${accessToken}` },
  });

  // const { data } = await defaultInstance.post(
  //   `/order-service/order`,
  //   orderData,
  //   {
  //     headers: { Authorization: `Bearer ${jwtToken}` },
  //   }
  // );
  console.log(accessToken);
  console.log(data);
  console.log(data.qrcodeUrl);
  return data.qrcodeUrl;
};
