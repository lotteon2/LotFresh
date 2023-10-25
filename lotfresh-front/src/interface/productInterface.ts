export interface ProductResponse {
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

export interface ProductPageResponse {
  products: ProductResponse[];
  totalPage: number;
  totalElements: number;
}
