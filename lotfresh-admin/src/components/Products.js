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
import PopUpProductAdd from './PopUpProductAdd';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import { getCategoryProduct} from "../api/product"
import Pagination from '@mui/material/Pagination';



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


const categorys = [
  { value: '1', label: '채소' },
  { value: '2', label: '과일·견과·쌀' },
  { value: '3', label: '수산·해산·건어물' },
  { value: '4', label: '정육·계란' },
  { value: '5', label: '국·반찬·메인요리' },
  { value: '6', label: '샐러드·간편식' },
  { value: '7', label: '면·양념·오일' },
  { value: '8', label: '생수·음료·우유·커피' },
  { value: '9', label: '간식·과자·떡' },
  { value: '10', label: '베이커리·치즈·델리' },
];

function Products() {
  const [selectedOption, setSelectedOption] = useState('');
  const [products, setProducts] = useState([]);
  const [page, setPage] = useState(1);

  const handleChange = (event) => {
    setSelectedOption(event.target.value);
  };

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await getCategoryProduct(selectedOption, page);
      } catch (error) {
        console.error(error);
      }
    };

    fetchData();
  }, [selectedOption, page]);

  const handlePageChange = (newPage) => {
    setPage(newPage);
  };

  const totalPage = 10;

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await getCategoryProduct(selectedOption, page); // 페이지 값을 API 호출에 전달합니다.
        setProducts(response.products);
      } catch (error) {
        console.error(error);
      }
    };fetchData();
  }, [selectedOption, page]);

  const [open, setOpen] = useState(false);

  const handleOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  return (
    <ThemeProvider theme={createTheme()}>
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
                    <Select onChange={handleChange} value={categorys[0].value} displayEmpty>
                      <MenuItem disabled value="">카테고리를 선택하세요.</MenuItem>
                      {
                        categorys.map((category) => (
                          <MenuItem key={category.value} value={category.value}>{category.label}</MenuItem>
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
                      height: 'calc(100vh - 200px)',
                    }}
                  >
                    <Button
                      variant="contained"
                      color="primary"
                      style={{ float: 'right', width: '100px' }}
                      onClick={handleOpen}
                    >
                      상품 추가
                    </Button>
                    <Dialog open={open} onClose={handleClose}>
                      <PopUpProductAdd />
                      <DialogActions>
                        <Button onClick={handleClose}>
                          Close
                        </Button>
                      </DialogActions>
                    </Dialog>

                    <Table>
                      <TableHead>
                        <TableRow>
                          <TableCell>ID</TableCell>
                          <TableCell>Name</TableCell>
                          <TableCell>Thumbnail</TableCell>
                          <TableCell>Product ID</TableCell>
                          <TableCell>Price</TableCell>
                        </TableRow>
                      </TableHead>
                      <TableBody>
                      {products.map((product) => (
                      <TableRow key={product.id}>
                        <TableCell>{product.id}</TableCell>
                        <TableCell>{product.name}</TableCell>
                        <TableCell>
                          <img src={product.thumbnail} alt="Thumbnail" width="50" height="50" />
                        </TableCell>
                        <TableCell>{product.productCode}</TableCell>
                        <TableCell>{product.price}</TableCell>
                      </TableRow>
                    ))}
                      </TableBody>
                    </Table>
                    <Grid item xs={12}>
                      <Pagination
                        count={totalPage}
                        page={page}
                        onChange={(event, newPage) => handlePageChange(newPage)}
                        shape="rounded"
                      />
                    </Grid>
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

export default Products;
