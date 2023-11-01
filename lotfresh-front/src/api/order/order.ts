import { defaultInstance, orderInstance } from "../utils";
import type {
  OrderSheetInfo,
  OrderSheetItem,
  OrderSheetList,
} from "../../interface/cartInterface";
import type {
  ProductPageResponse,
  OrderResponse,
  OrderDetailResponse,
} from "../../interface/orderInterface";

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
  province: string | undefined;
}

export const getOrdersheetList = async (
  accessToken: string | null
): Promise<OrderSheetList> => {
  const { data } = await orderInstance.get(`/order/ordersheet`, {
    headers: { Authorization: `Bearer ${accessToken}` },
  });
  return data;
};

export const startKakaopay = async (
  orderData: OrderCreateRequest,
  accessToken: string | null
): Promise<string> => {
  const { data } = await orderInstance.post(`/order`, orderData, {
    headers: { Authorization: `Bearer ${accessToken}` },
  });
  console.log(accessToken);
  console.log(data);
  return data;
};

export const getOrders = async (
  page: number,
  accessToken: string | null
): Promise<ProductPageResponse> => {
  const { data } = await orderInstance.get(
    `/order/myOrders?page=${page}&size=5`,
    {
      headers: { Authorization: `Bearer ${accessToken}` },
    }
  );
  return data;
};

export const getOrderDetail = async (
  orderId: any,
  accessToken: string | null
): Promise<OrderResponse> => {
  const { data } = await orderInstance.get(`/order/${orderId}`, {
    headers: { Authorization: `Bearer ${accessToken}` },
  });
  return data;
};
