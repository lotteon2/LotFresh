import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import SignIn from "./components/SignIn";
import Main from "./components/Main";
import Storages from './components/Storages';
import Products from './components/Products';
import axios from 'axios';
import { useEffect } from 'react';
import UserTable from './components/UserList'

function App() {

  return (
    <Router>
      <Routes>
        <Route path="/login" element={<SignIn />} />
        <Route path="/" element={<Main />} />
        <Route path="/users" element={<UserTable />} />
        <Route path="/storages" element={<Storages />} />
        <Route path="/products" element={<Products />} />
        <Route path="/refund" element={<Products />} />
      </Routes>
    </Router>
  );
}

export default App;
