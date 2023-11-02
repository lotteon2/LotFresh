import { defaultInstance, cartInstance } from "../utils";
import type { CartCreateDto } from "@/interface/cartInterface";
import type { OrderSheetItem } from "@/interface/orderInterface";

export const createCart = async (
  cartCreateDto: CartCreateDto,
  accessToken: string | null
): Promise<any> => {
  const response = await cartInstance.post("/carts", cartCreateDto, {
    headers: { Authorization: `Bearer ${accessToken}` },
  });
  return response;
};

export const getCarts = async (
  accessToken: string | null
): Promise<OrderSheetItem[]> => {
  const response = await cartInstance.get("/carts", {
    headers: { Authorization: `Bearer ${accessToken}` },
  });
  return response.data;
};
