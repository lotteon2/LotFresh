export interface MemberInfo {
  email: string;
  nickname: string;
  isActive: boolean;
  province: string | null | undefined;
}

export interface CreateMemberDto {
  roadAddress: string;
  province: string;
  zipCode: string;
  addressDetail: string | null | undefined;
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
  orderSheetItems: OrderSheetItem[] | null;
  isFromCart: boolean;
}
