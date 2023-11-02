import { Category } from '@mui/icons-material';
import { defaultInstance } from '../utils';

export const getStorageProduct = async (region) => {
  try {
    const response = await defaultInstance.get(`/storage-service/storageproduct/search/${region}`);
    return response.data;
  } catch (error) {
    throw new Error('Product Search API 요청 중 오류가 발생했습니다.');
  }
};

export const getCategoryProduct = async(categoryId, page) => {
  try {
    const response = await defaultInstance.get(`/product-service/products/categories/${categoryId}?page=${page}&size=8`);
    return response.data;
  } catch (error) {
    throw new Error('Category Search API 요청 중 오류가 발생했습니다.');
  }
}


export const getProductInfo = async(productId) => {
try {
    const response = await defaultInstance.get(`/product-service/products/${productId}/Seoul`);
    return response.data;
  } catch (error) {
    throw new Error('Product Search Info API 요청 중 오류가 발생했습니다.');
  }
}


export const getUsers = async(productId) => {
  try {
      const response = await defaultInstance.get(`/user-service/users`);
      return response.data;
    } catch (error) {
      throw new Error('Get Users API 요청 중 오류가 발생했습니다.');
    }
  }



export const saveStorageProduct = async(storageProduct) => {
  try {
    const response = await defaultInstance.post('/storage-service/storageproduct/storageProducts', storageProduct);
    return response.data;
  } catch (error) {
    throw new Error('Storage Product Save API 요청 중 오류가 발생했습니다.');
  }
};

export const saveProduct = async(product) => {
  try {
    const response = await defaultInstance.post('/product-service/products', product);
    return response.data;
  } catch (error) {
    throw new Error('Product Save API 요청 중 오류가 발생했습니다.');
  }
};