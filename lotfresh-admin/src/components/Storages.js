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
import {getStorageProduct} from "../api/product"
import Button from '@mui/material/Button';
import PopupContent from './PopupContent';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import PopUpStockAdd from './PopUpStockAdd';
import TablePagination from '@mui/material/TablePagination';

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
  { value: '경기도', label: 'Gyeonggi-do' },
  { value: '강원도', label: 'Gangwon-do' },
  { value: '충청북도', label: 'Chungcheongbuk-do' },
  { value: '충청남도', label: 'Chungcheongnam-do' },
  { value: '전라북도', label: 'Jeollabuk-do' },
  { value: '전라남도', label: 'Jeollanam-do' },
  { value: '경상북도', label:'Gyeongsangbuk-do'},
  { value:'경상남도',label:'Gyeongsangnam-do'},
  {value:'제주도',label:'Jeju-do'}
];


function Storages() {
  const [selectedRegion, setSelectedRegion] = useState('');
  const [storageProducts, setStorageProducts] = useState([]);
  const [error, setError] = useState(false);

  const [isPopupOpen, setPopupOpen] = useState(false);
  const [selectedProduct, setSelectedProduct] = useState(null);

  const [isAddPopupOpen, setAddPopupOpen] = useState(false);

  const handleAddClick = () => {
    setAddPopupOpen(true);
  };

  const handleAddClose = () => {
    setAddPopupOpen(false);
  };

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

  const [page, setPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(8);

  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(parseInt(event.target.value, 10));
    setPage(0);
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
                    <Button 
                      variant="contained" 
                      color="primary"
                      onClick={handleAddClick} // 버튼 클릭 시 handleAddClick 함수 호출
                      style={{ float: 'right', width: '100px' }}
                    >
                      상품 추가
                    </Button>
                    
                    {/* Add Dialog */}
                    <Dialog open={isAddPopupOpen} onClose={handleAddClose}>
                      <PopUpStockAdd />
                      <DialogActions>
                        <Button onClick={handleClose}>Close</Button>
                      </DialogActions>
                    </Dialog>
                      <Table>
                    <TableHead>
                      <TableRow>
                        <TableCell>ID</TableCell>
                        <TableCell>Stock</TableCell>
                        <TableCell>Product ID</TableCell>
                        <TableCell>Expiration Date</TableCell>
                        <TableCell>Modify</TableCell>
                        <TableCell> </TableCell>
                      </TableRow>
                    </TableHead>
                    <TableBody>
                    {storageProducts.length > 0 ? (
                      storageProducts.slice(page * rowsPerPage, (page + 1) * rowsPerPage).map((product) => (
                        <TableRow key={product.id}>
                          <TableCell>{product.id}</TableCell>
                          <TableCell>{product.stock}</TableCell>
                          <TableCell>{product.productId}</TableCell>
                          <TableCell>
                            {format(new Date(product.expirationDateStart), 'yyyy-MM-dd')} ~ 
                            {format(new Date(product.expirationDateEnd), 'yyyy-MM-dd')}
                          </TableCell>
                          <TableCell> 
                            <Button onClick={() => handleEditClick(product)} color="error">수정</Button>
                            <Dialog open={isPopupOpen} onClose={handleClose}>
                              <PopupContent product={selectedProduct} />
                              <DialogActions>
                                <Button onClick={handleClose}>Close</Button>
                              </DialogActions>
                            </Dialog>
                          </TableCell>
                        </TableRow>
                      ))
                      ) : (
                        <TableRow>
                          <TableCell colSpan={8} align="center">
                            데이터가 없습니다.
                          </TableCell>
                        </TableRow>
                      )}
                    </TableBody> 
                  </Table> 

                  <TablePagination
                    component="div"
                    count={storageProducts.length}
                    page={page}
                    onPageChange={handleChangePage}
                    rowsPerPage={rowsPerPage}
                    onRowsPerPageChange={handleChangeRowsPerPage}
                  />
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
