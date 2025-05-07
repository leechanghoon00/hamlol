import './Login.css';
import Login from "./Login";
import './Signup.css';
import {Signup} from "./Signup";
import Main from "./Main";

import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom'

function App() {
  return (
      <BrowserRouter>
          <Routes>
              <Route path="/" element={<Navigate to="/login" replace/>} />
              <Route path="/login" element={<Login />} />
              <Route path="/signup" element={<Signup /> } />
              <Route path="/main" element={<Main />}/>
          </Routes>
      </BrowserRouter>  );
}

export default App;
