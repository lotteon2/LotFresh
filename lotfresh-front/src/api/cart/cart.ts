import { defaultInstance, cartInstance } from "../utils";
import type { CartCreateDto } from "@/interface/cartInterface";

export const createCart = async (
  cartCreateDto: CartCreateDto,
  accessToken: string | null
): Promise<any> => {
  const response = await cartInstance.post("/carts", cartCreateDto, {
    headers: { Authorization: `Bearer ${accessToken}` },
  });
  return response;
};
