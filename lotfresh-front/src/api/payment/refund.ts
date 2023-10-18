import { defaultInstance } from "../utils";

interface RefundDetail {
    name: string;
    amount: number; // JavaScript와 TypeScript에서는 Long 타입 대신 Number 타입을 사용합니다.
  }

export const getRefundDetail = async (order_detail_id: number): Promise<RefundDetail> => {
    const { data } = await defaultInstance.get(`/refunds/order-details/${order_detail_id}`);
    return data;
  };
  