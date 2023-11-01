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
