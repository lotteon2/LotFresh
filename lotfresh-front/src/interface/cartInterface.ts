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

export interface OrderSheetItem {
  productId: number;
  originalPrice: number;
  discountedPrice: number;
  productStock: number;
  productName: string;
  productThumbnail: string;
}

export interface OrderSheetList {
  orderSheetItems: OrderSheetItem[];
  isFromCart: boolean;
}

// export interface OrderSheetInfo {
//   orderSheetItems: OrderSheetItem[];
//   isFromCart: boolean;
// }
// 아래가 기존.
export interface OrderSheetInfo {
  productId: number;
  originalPrice: number;
  discountedPrice: number;
  productStock: number;
  productName: string;
  productThumbnail: string;
}
