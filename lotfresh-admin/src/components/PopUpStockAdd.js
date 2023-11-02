import React, { useState } from 'react';
import DialogTitle from '@mui/material/DialogTitle';
import DialogContent from '@mui/material/DialogContent';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import { getProductInfo } from '../api/product';
import { saveStorageProduct } from "../api/product"
import { styled } from '@mui/system';

const StyledDialogContent = styled(DialogContent)({
  display: 'flex',
  flexDirection: 'column',
  alignItems: 'center',
  gap: '16px',
  padding: '16px',
});

function PopUpStockAdd() {
  const [searchTerm, setSearchTerm] = useState('');
  const [productInfo, setProductInfo] = useState(null);
  const [storageId, setStorageId] = useState('');
  const [stock, setStock] = useState('');
  const [expirationDateStart, setExpirationDateStart] = useState('');
  const [expirationDateEnd, setExpirationDateEnd] = useState('');

  const handleSearchChange = (event) => {
    setSearchTerm(event.target.value);
  };

  const handleSearchClick = async () => {
    try {
      const response = await getProductInfo(searchTerm);
      setProductInfo(response);
      setStorageId('');
      setStock('');
      setExpirationDateStart('');
      setExpirationDateEnd('');
    } catch (error) {
      console.log(error.message);
      setProductInfo(null);
      setStorageId('');
      setStock('');
      setExpirationDateStart('');
      setExpirationDateEnd('');
    }
  };

  const handleSaveClick = async () => {
    console.log("start");
    try {
      const storageProduct = {
        productId: productInfo.id,
        storageId,
        stock: parseInt(stock),
        expirationDateStart,
        expirationDateEnd
      };
      console.log(storageProduct);
      await saveStorageProduct(storageProduct);
    } catch (error) {
      console.log(error.message);
    }
  };

  return (
    <>
      <DialogTitle>{"Lotfresh StorageProduct"}</DialogTitle>
      <StyledDialogContent>
        {/* Product Search Field */}
        <TextField
          label="Search Term"
          type="text"
          value={searchTerm}
          onChange={handleSearchChange}
        />
        <Button
          variant="contained"
          color="primary"
          onClick={handleSearchClick}
        >
          Search
        </Button>
        
        {/* Display Product Info */}
        {productInfo && (
          <>
            <div>ID: {productInfo.id}</div>
            <div>Category: {productInfo.categoryName}</div>
            <div>Name: {productInfo.name}</div>
            <div>Price: {productInfo.price}</div>
            <img src={productInfo.thumbnail} alt="Thumbnail" style={{maxWidth: '300px'}}/>
            
            {/* Storage Product Info */}
            <TextField
              label="Storage ID"
              type="text"
              value={storageId}
              onChange={(event) => setStorageId(event.target.value)}
            />
            <TextField
              label="Stock"
              type="number"
              value={stock}
              onChange={(event) => setStock(event.target.value)}
            />
            <TextField
              label="Expiration Date Start"
              type="date"
              value={expirationDateStart}
              onChange={(event) => setExpirationDateStart(event.target.value)}
              InputLabelProps={{
                shrink: true,
              }}
            />
            <TextField
              label="Expiration Date End"
              type="date"
              value={expirationDateEnd}
              onChange={(event) => setExpirationDateEnd(event.target.value)}
              InputLabelProps={{
                shrink: true,
              }}
            />
            <Button
              variant="contained"
              color="primary"
              onClick={handleSaveClick}
            >
              Save
            </Button>
          </>
        )}
      </StyledDialogContent>
    </>
  );
}

export default PopUpStockAdd;
