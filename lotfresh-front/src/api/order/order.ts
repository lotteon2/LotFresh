import { defaultInstance, orderInstance } from "../utils";
import type {
  OrderSheetInfo,
  OrderSheetItem,
  OrderSheetList,
} from "../../interface/cartInterface";

export interface ProductRequest {
  productId: number;
  productPrice: number;
  productStock: number;
  productName: string;
  productThumbnail: string;
}

export interface OrderCreateRequest {
  productRequests: OrderSheetItem[] | undefined;
  isFromCart: boolean | undefined;
  province: String | undefined;
}

export const getOrdersheetList = async (): Promise<OrderSheetList> => {
  const accessToken = localStorage.getItem("accessToken");
  const { data } = await orderInstance.get(`/order/ordersheet`, {
    headers: { Authorization: `Bearer ${accessToken}` },
  });
  return data;
};

export const startKakaopay = async (
  orderData: OrderCreateRequest
): Promise<string> => {
  const accessToken = localStorage.getItem("accessToken");

  const { data } = await orderInstance.post(`/order`, orderData, {
    headers: { Authorization: `Bearer ${accessToken}` },
  });
  console.log(accessToken);
  console.log(data);
  return data;
};
