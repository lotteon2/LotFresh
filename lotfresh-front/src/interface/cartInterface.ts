export interface CartCreateDto {
  productId: number;
  discountedPrice: number | null | undefined;
  province: string | null | undefined;
  productStock: number | null | undefined;
  price: number;
  productName: string;
  productImageUrl: string;
  selectedQuantity: number;
}

export interface OrderSheetInfo {
  productId: number;
  originalPrice: number;
  discountedPrice: number;
  productStock: number;
  productName: string;
  productThumbnail: string;
}
