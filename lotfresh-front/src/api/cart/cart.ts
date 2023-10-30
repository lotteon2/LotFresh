import { defaultInstance, cartInstance } from "../utils";
import type {
  CartCreateDto,
  OrderSheetInfo,
} from "@/interface/cartInterface.ts";

export const createCart = async (
  cartCreateDto: CartCreateDto
): Promise<any> => {
  const response = await cartInstance.post("/carts");
  return response;
};
