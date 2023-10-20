import { ref } from "vue";
import { defineStore } from "pinia";
import { getBestProducts } from "@/api/product/product";

interface ProductResponse {
  id: number;
  name: string;
  thumbnail: string;
  detail: string;
  price: number;
  salesPrice: number | null;
  productCode: string;
  categoryId: number;
  categoryName: string;
  parentId: number | null;
  parentName: string | null;
  stock: number | null;
}
export const useProductStore = defineStore("product", () => {
  const getBestProductsAction = async () => {
    return await getBestProducts();
  };

  return { getBestProductsAction };
});
