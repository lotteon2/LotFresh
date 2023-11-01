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
