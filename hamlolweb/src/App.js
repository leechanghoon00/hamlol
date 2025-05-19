import './Login.css';
import Login from "./Login";
import './Signup.css';
import {Signup} from "./Signup";
// import './Main.css'
import Main from "./Main";
 // import './Account.css'
import Account from "./Account";
import './GameList.css'
import GameList from "./GameList";
// import './SaveGame.css'
import SaveGame from "./SaveGame";


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
          </Routes>
      </BrowserRouter>  );
}

export default App;
