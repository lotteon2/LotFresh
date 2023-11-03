import React, { useEffect, useState } from 'react';
import { Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper, Container, Grid } from '@mui/material';
import Pagination from '@mui/material/Pagination';
import { getUsers } from '../api/product'; 
import AppLayout from './AppLayout'; 
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import CssBaseline from '@mui/material/CssBaseline';


function UserTable() {
  const [data, setData] = useState({ totalPages: 1, memberDetailResponses: [] });
  const [page, setPage] = useState(1);

  useEffect(() => {
    const fetchData = async () => {
        try {
            const result = await getUsers(); 
            setData(result); 
            console.log("user api :");
            console.log(result);
        } catch (error) {
            console.log("유저 api에러");
        }      
    };
    fetchData();
  }, []);

  const handleChange = (event, value) => {
    setPage(value);
  };

  return (
    
    <Box sx={{ display: 'flex', width: '100%', justifyContent: 'center'}}>
    <CssBaseline />
    <AppLayout>
    <Toolbar />
    <Box sx={{ mt: 4, width: '80%'  }}>
      <Container maxWidth="lg" sx={{ mt: 4, mb: 4 }}>
        <Grid container spacing={3}>
          <Grid item xs={12}>
            <Paper sx={{ p: 2, display: 'flex', flexDirection: 'column' }}>
              <TableContainer component={Paper}>
                <Table>
                  <TableHead>
                    <TableRow>
                      <TableCell>ID</TableCell>
                      <TableCell>Email</TableCell>
                      <TableCell>Nickname</TableCell>
                    </TableRow>
                  </TableHead>
                  <TableBody>
                    {data.memberDetailResponses.map((row) => (
                      <TableRow key={row.id}>
                        <TableCell>{row.id}</TableCell>
                        <TableCell>{row.email}</TableCell>
                        <TableCell>{row.nickname}</TableCell>
                      </TableRow>
                    ))}
                  </TableBody>
                </Table>
              </TableContainer>
              <Pagination count={data.totalPages} page={page} onChange={handleChange} />
            </Paper>
          </Grid>
        </Grid>
      </Container>
      </Box>
    </AppLayout>
    </Box>
  );
}

export default UserTable;
