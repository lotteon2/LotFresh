import { defaultInstance, paymentInstance } from "../utils";

export interface PaymentInfo {
  paymentMethod: string;
  originalAmount: number;
  discountedAmount: number;
  transactionAmount: number;
}

export const getPaymentDetailInfo = async (
  orderId: string,
  accessToken: string | null
): Promise<PaymentInfo> => {
  const { data } = await paymentInstance.get(`/orderid/${orderId}`, {
    headers: { Authorization: `Bearer ${accessToken}` },
  });
  return data;
};
