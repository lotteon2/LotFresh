import React, { useState } from 'react';
import DialogTitle from '@mui/material/DialogTitle';
import DialogContent from '@mui/material/DialogContent';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';

function PopupContent({ product }) {
  const [stock, setStock] = useState(product.stock);
  const [expirationDateStart, setExpirationDateStart] = useState('');
  const [expirationDateEnd, setExpirationDateEnd] = useState('');

  const handleStockChange = (event) => {
    setStock(event.target.value);
  };

  const handleExpirationStartDateChange = (event) => {
    setExpirationDateStart(event.target.value);
  };

  const handleExpirationEndDateChange = (event) => {
    setExpirationDateEnd(event.target.value);
  };

  return (
    <>
      <DialogTitle>{"Product Details"}</DialogTitle>
      <DialogContent>
        <div>ID: {product.id}</div>
        <div>Name: {product.name}</div>
        <div>Thumbnail: 이미지</div>
        {/* Stock 입력 필드 */}
        <TextField
          label="Stock"
          type="number"
          value={stock}
          onChange={handleStockChange}
          style={{ marginTop: '10px' }}
        />
        {/* Expiration Date Start 입력 필드 */}
        <TextField
          label="Expiration Date Start"
          type="date"
          value={expirationDateStart}
          onChange={handleExpirationStartDateChange}
          InputLabelProps={{
            shrink: true,
           }}
           style={{ marginTop: '10px' }}
         />
         {/* Expiration Date End 입력 필드 */}
         <TextField
           label="Expiration Date End"
           type="date"
           value={expirationDateEnd}
           onChange={handleExpirationEndDateChange}
           InputLabelProps={{
             shrink: true,
            }}
            style={{ marginTop: '10px' }}
         />
         <Button 
          variant="contained" 
          color="primary"
          //onClick={() => onSave(stock, expirationDateStart, expirationDateEnd)}
          style={{ marginTop: '10px' }}
         >
          Save
        </Button>
        {/* 취소 버튼 */}
        <Button 
          variant="outlined" 
          //onClick={onCancel}
          style={{ marginTop: '10px', marginLeft:'10px' }}
        >
          Cancel
        </Button>
      </DialogContent>
    </>
  );
}

export default PopupContent;
