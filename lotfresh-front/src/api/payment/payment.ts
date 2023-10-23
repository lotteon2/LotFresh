import { defaultInstance } from "../utils";

interface PaymentInfo {
  paymentMethod: string;
  originalAmount: number;
  discountedAmount: number;
  transactionAmount: number;
  //
}

export const getPaymentDetailInfo = async (
  orderId: number
): Promise<PaymentInfo> => {
  const { data } = await defaultInstance.get(`/orderid/${orderId}`);
  return data;
};
