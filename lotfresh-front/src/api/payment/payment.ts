import { defaultInstance } from "../utils";

export const getBestProducts = async () => {
    const { data } = await defaultInstance.get("/products/best-products");
    return data;
  };
  