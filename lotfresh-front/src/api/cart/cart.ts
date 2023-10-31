import { defaultInstance, cartInstance } from "../utils";
import type { CartCreateDto, OrderSheetInfo } from "@/interface/cartInterface";

export const createCart = async (
  cartCreateDto: CartCreateDto
): Promise<any> => {
  const response = await cartInstance.post("/carts", cartCreateDto);
  return response;
};

export const addOrderheetInfos = async (
  orderSheetInfos: OrderSheetInfo[],
  province: string | null | undefined,
  productId: number
): Promise<any> => {
  const response = await cartInstance.post(
    `/carts/province/${province}/product/${productId}`,
    orderSheetInfos
  );
};
