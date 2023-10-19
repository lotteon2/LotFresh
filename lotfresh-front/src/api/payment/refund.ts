import { defaultInstance } from "../utils";

interface RefundDetail {
  refundCreatedAt: Date;
  refundId: number;
  refundUpdatedAt: Date;

  stock: number;
  productAmount: number;
  refundMethod: string;
  refundedAmount: number;
}

export const getRefundDetail = async (
  refundId: number
): Promise<RefundDetail> => {
  const { data } = await defaultInstance.get(
    `/payment-service/refunds/${refundId}`
  );
  return data;
};

// 이부분 아예 새로 만들어야함.
export const getRefundList = async (
  refundId: number
): Promise<RefundDetail> => {
  const { data } = await defaultInstance.get(
    `/payment-service/refunds/${refundId}`
  );
  return data;
};
