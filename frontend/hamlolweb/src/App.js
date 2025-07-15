import Login from "./Login";
import {Signup} from "./Signup";
import Main from "./Main";
import GameList from "./GameList";
import SaveGame from "./SaveGame";
import Account from "./Account";
import FindPassword from "./FindPassword";

import './Login.css';
import './Signup.css';
import './Main.css';
import './GameList.css';
import './SaveGame.css';
import './Account.css';
import './FindPassword.css';

import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom'

function App() {
  return (
      <BrowserRouter>
          <Routes>
              <Route path="/" element={<Navigate to="/login" replace/>} />
              <Route path="/login" element={<Login />} />
              <Route path="/signup" element={<Signup /> } />
              <Route path="/main" element={<Main />}/>
              <Route path="/account" element={<Account />}/>
              <Route path="/gameList" element={<GameList />}/>
              <Route path="/saveGame" element={<SaveGame />}/>
              <Route path="/findpassword" element={<FindPassword />}/>

          </Routes>
      </BrowserRouter>  );
}

export default App;
