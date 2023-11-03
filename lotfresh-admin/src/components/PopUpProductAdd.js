import React, { useState } from 'react';
import DialogTitle from '@mui/material/DialogTitle';
import DialogContent from '@mui/material/DialogContent';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import { saveProduct } from '../api/product/index';

function PopupAdd() {
  const [product, setproduct] = useState({
    categoryId: '',
    name: '',
    thumbnail: '',
    detail: '',
    price: '',
    productCode: '',
  });

  const handleInputChange = (event) => {
    setproduct({
      ...product,
      [event.target.name]: event.target.value,
    });
  };

  const handleSaveClick = async () => {
    try {
      const response = await saveProduct(product);
      alert('성공적으로 추가되었습니다.');
    } catch (error) {
      console.log(error.message);
    }
  };

  return (
    <>
      <DialogTitle>{"Product Save"}</DialogTitle>
      <DialogContent>
        <TextField
          name="categoryId"
          label="Category ID"
          type="number"
          value={product.categoryId}
          onChange={handleInputChange}
        />
        <TextField
          name="name"
          label="Name"
          type="text"
          value={product.name}
          onChange={handleInputChange}
        />
        <TextField
          name="thumbnail"
          label="Thumbnail"
          type="text"
          value={product.thumbnail}
          onChange={handleInputChange}
        />
        <TextField
          name="detail"
          label="Detail"
          type="text"
          value={product.detail}
          onChange={handleInputChange}
        />
        <TextField
          name="price"
          label="Price"
          type="number"
          value={product.price}
          onChange={handleInputChange}
        />
        <TextField
          name="productCode"
          label="Product Code"
          type="text"
          value={product.productCode}
          onChange={handleInputChange}
        />
        <Button
          variant="contained"
          color="primary"
          onClick={handleSaveClick}
        >
          Save
        </Button>
      </DialogContent>
    </>
  );
}

export default PopupAdd;
