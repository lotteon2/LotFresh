import { defaultInstance, productInstance } from "../utils";

export const getBestProducts = async () => {
  const { data } = await productInstance.get("/products/best-products");
  return data;
};
