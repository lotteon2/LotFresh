import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import SignIn from "./components/SignIn";
import Main from "./components/Main";
import Storages from './components/Storages';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/login" element={<SignIn />} />
        <Route path="/" element={<Main />} />
        <Route path="/storages" element={<Storages />} />
      </Routes>
    </Router>
  );
}

export default App;
