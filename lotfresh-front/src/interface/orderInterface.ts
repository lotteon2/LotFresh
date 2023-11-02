export interface ProductPageResponse {
  contents: OrderResponse[];
  totalPage: number;
}

export interface OrderResponse {
  orderId: number;
  orderCreatedTime: string;
  orderDetailResponses: OrderDetailResponse[];
}

export interface OrderDetailResponse {
  orderDetailId: number;
  price: number;
  stock: number;
  status: string;
  productName: string;
  productThumbnail: string;
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
  isBargain: boolean;
}
