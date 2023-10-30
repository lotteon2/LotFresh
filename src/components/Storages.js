import React, { useEffect, useState } from 'react';
import AppLayout from './AppLayout';
import CssBaseline from '@mui/material/CssBaseline';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import Grid from '@mui/material/Grid';
import Paper from '@mui/material/Paper';
import {createTheme, ThemeProvider } from '@mui/material/styles';
import Link from '@mui/material/Link';
import Select from '@mui/material/Select';
import MenuItem from '@mui/material/MenuItem';
import { Table, TableHead, TableRow, TableCell, TableBody } from '@mui/material';
import { format } from 'date-fns';
import {getStorageProduct} from "../api/api"
import Button from '@mui/material/Button';
import PopupContent from './PopupContent';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';



function Copyright(props) {
  return (
    <Typography variant="body2" color="text.secondary" align="center" {...props}>
      {'Copyright © '}
      <Link color="inherit" href="https://mui.com/">
        Lotfresh
      </Link>{' '}
      {new Date().getFullYear()}
      {'.'}
    </Typography>
  );
}

const defaultTheme = createTheme();


const regions = [
  { value: '서울특별시', label: 'Seoul' },
  { value: '경기도', label: 'Gyeonggi' },
  { value: '강원도', label: 'Gangwon' },
  { value: '충청북도', label: 'Chungcheongbuk' },
  { value: '충청남도', label: 'Chungcheongnam' },
  { value: '전라북도', label: 'Jeollabuk' },
  { value: '전라남도', label: 'Jeollanam' },
  { value: '경상북도', label:'Gyeongsangbuk'},
  { value:'경상남도',label:'Gyeongsangnam'},
  {value:'제주도',label:'Jeju'}
];


function Storages() {
  const [selectedRegion, setSelectedRegion] = useState('');
  const [storageProducts, setStorageProducts] = useState([]);
  const [error, setError] = useState(false);

  const [isPopupOpen, setPopupOpen] = useState(false);
  const [selectedProduct, setSelectedProduct] = useState(null);

  const handleEditClick = (product) => {
    setSelectedProduct(product);
    setPopupOpen(true);
  };

  const handleClose = () => {
    setPopupOpen(false);
  };

  const fetchStorageProduct = async (region) => {
    try {
      const response = await getStorageProduct(region);
      setStorageProducts(response);
      console.log(response);
      setError(false);
    } catch (error) {
      console.log("data error!!");
      console.log(error);
      setStorageProducts([]);
      setError(true);
    }
  };

  useEffect(() => {
    if (selectedRegion) { 
      fetchStorageProduct(selectedRegion);
    }
  }, [selectedRegion]);

  const handleChange = (event) => {
     setSelectedRegion(event.target.value);
  };

  return (
    <ThemeProvider theme={defaultTheme}>
    <Box sx={{ display: 'flex' }}>
      <CssBaseline />
      <AppLayout>
      
      <Box
        component="main"
        sx={{
          backgroundColor: (theme) =>
            theme.palette.mode === 'light'
              ? theme.palette.grey[100]
              : theme.palette.grey[900],
          flexGrow: 1,
          height: '100vh',
          overflow: 'auto',
        }}
      >
        <Toolbar />
        <Container maxWidth="lg" sx={{ mt: 4, mb: 4 }}>
              <Grid container spacing={3}>
                <Grid item xs={12}>
                  <Paper
                    sx={{
                      p: 2,
                      display: 'flex',
                      flexDirection: 'column',
                      height: 100,
                    }}
                  >
                    <Select onChange={handleChange} value={selectedRegion} displayEmpty>
                    <MenuItem disabled value="">창고를 선택하세요.</MenuItem>
                    {
                        regions.map((region) => (
                          <MenuItem key={region.value} value={region.label}>{region.value}</MenuItem>
                        ))
                    }
                  </Select>
                  </Paper>
                </Grid>

                <Grid item xs={12}>
                  <Paper
                    sx={{
                      p: 2,
                      display: 'flex',
                      flexDirection: 'column',
                      height:'calc(100vh - 200px)', 
                    }}
                  >
                     <Table>
                      <TableHead>
                        <TableRow>
                          <TableCell>ID</TableCell>
                          <TableCell>Name</TableCell>
                          <TableCell>Thumbnail</TableCell>
                          <TableCell>Stock</TableCell>
                          <TableCell>Product ID</TableCell>
                          <TableCell>Expiration Date</TableCell>
                          <TableCell>Modify</TableCell>
                          <TableCell> </TableCell>
                        </TableRow>
                      </TableHead>
                      <TableBody>
                        {storageProducts.map((product) => (
                          <TableRow key={product.id}>
                            <TableCell>{product.id}</TableCell>
                            <TableCell>감자</TableCell>
                            <TableCell>이미지</TableCell>
                            <TableCell>{product.stock}</TableCell>
                            <TableCell>{product.productId}</TableCell>
                            <TableCell>
                              {format(new Date(product.expirationDateStart), 'yyyy-MM-dd')} ~ 
                              {format(new Date(product.expirationDateEnd), 'yyyy-MM-dd')}
                            </TableCell>
                            <TableCell> 
                              <Button onClick={() => handleEditClick(product)} color="error">수정</Button>

                              {/* 여기에 Dialog 컴포넌트를 추가합니다. */}
                              <Dialog open={isPopupOpen} onClose={handleClose}>
                                {/* 별도의 컴포넌트로 분리한 팝업 내용입니다. */}
                                <PopupContent product={selectedProduct} />
                                {/* "Close" 버튼입니다. */}
                                <DialogActions>
                                  <Button onClick={handleClose}>Close</Button>
                                </DialogActions>
                              </Dialog>
                    </TableCell>
                          </TableRow>
                        ))}
                      </TableBody> 
                    </Table> 
                  </Paper>
                </Grid>
               </Grid>

             </Container>
      </Box>
      </AppLayout>
    </Box>
  </ThemeProvider>
  );
}

export default Storages;
