import Login from "./Login";
import {Signup} from "./Signup";
import Main from "./Main";
import GameList from "./GameList";
import SaveGame from "./SaveGame";
import Account from "./Account";
import FindPassword from "./FindPassword";
import ResetPassword from "./ResetPassword";
import Footer from "./Footer";
import Callback from "./Callback";
import { useState, useEffect } from "react";

import './Login.css';
import './Signup.css';
import './Main.css';
import './GameList.css';
import './SaveGame.css';
import './Account.css';
import './FindPassword.css';
import './ResetPassword.css'
import './App.css';
import './Callback.css';

import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom'

function App() {
  const [isDarkMode, setIsDarkMode] = useState(false);

  useEffect(() => {
    // 로컬 스토리지에서 테마 설정 불러오기
    const savedTheme = localStorage.getItem('theme');
    if (savedTheme === 'dark') {
      setIsDarkMode(true);
    }
  }, []);

  const toggleTheme = () => {
    const newTheme = !isDarkMode;
    setIsDarkMode(newTheme);
    localStorage.setItem('theme', newTheme ? 'dark' : 'light');
  };

  useEffect(() => {
    // 테마 변경 시 CSS 변수 업데이트
    if (isDarkMode) {
      document.documentElement.style.setProperty('--page-bg', '#2b2b2b');
      document.documentElement.style.setProperty('--text-color', '#ffffff');
      document.documentElement.style.setProperty('--container-bg', '#3a3a3a');
      document.documentElement.style.setProperty('--footer-bg', '#1c1c1c');
    } else {
      document.documentElement.style.setProperty('--page-bg', '#f5f5f5');
      document.documentElement.style.setProperty('--text-color', '#333333');
      document.documentElement.style.setProperty('--container-bg', '#ffffff');
      document.documentElement.style.setProperty('--footer-bg', '#1c1c1c');
    }
  }, [isDarkMode]);

  return (
      <BrowserRouter>
          <div className={`app-container ${isDarkMode ? 'dark-mode' : 'light-mode'}`}>
              <button 
                  className="theme-toggle-btn"
                  onClick={toggleTheme}
                  title={isDarkMode ? '라이트모드로 변경' : '다크모드로 변경'}
              >
                  {isDarkMode ? '☀️' : '🌙'}
              </button>
              <div className="app-content">
                  <Routes>
                      <Route path="/" element={<Navigate to="/login" replace/>} />
                      <Route path="/login" element={<Login />} />
                      <Route path="/signup" element={<Signup /> } />
                      <Route path="/main" element={<Main />}/>
                      <Route path="/account" element={<Account />}/>
                      <Route path="/gameList" element={<GameList />}/>
                      <Route path="/saveGame" element={<SaveGame />}/>
                      <Route path="/findpassword" element={<FindPassword />}/>
                      <Route path="/reset-password/:uuid" element={<ResetPassword />} />
                      <Route path="/callback" element={<Callback />} />
                  </Routes>
              </div>
              <Footer />
          </div>
      </BrowserRouter>  );
}

export default App;
