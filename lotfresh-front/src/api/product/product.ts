import { defaultInstance, productInstance } from "../utils";
import type {
  ProductResponse,
  ProductPageResponse,
} from "@/interface/productInterface";

export const getBestProducts = async (): Promise<ProductResponse[]> => {
  const { data } = await productInstance.get("/products/best-products");
  return data;
};

export const getSalesProducts = async (): Promise<ProductResponse[]> => {
  const { data } = await productInstance.get("/products/sales-products");
  return data;
};

export const getNewProducts = async (): Promise<ProductResponse[]> => {
  const { data } = await productInstance.get(`/products/new-products`);
  return data;
};

export const getCategoryProducts = async (
  categoryId: any,
  page: number
): Promise<ProductPageResponse> => {
  const { data } = await productInstance.get(
    `/products/categories/${categoryId}`,
    {
      params: {
        page: page,
      },
    }
  );
  return data;
};

export const getProductsBySearch = async (
  page: number,
  keyword: any
): Promise<ProductPageResponse> => {
  const { data } = await productInstance.get(`/products`, {
    params: {
      page: page,
      keyword: keyword,
    },
  });
  return data;
};

export const getProductDetail = async (
  id: any,
  province: string | null
): Promise<ProductResponse> => {
  const { data } = await productInstance.get(`/products/${id}/${province}`);
  return data;
};
