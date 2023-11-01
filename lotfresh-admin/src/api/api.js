import axios from "axios";

export const getStorageProduct = async (region) => {
  try {
    const response = await axios.get(
      `http://localhost:8086/storageproduct/search/${region}`
    );
    return response.data;
  } catch (error) {
    throw new Error("API 요청 중 오류가 발생했습니다.");
  }
};
