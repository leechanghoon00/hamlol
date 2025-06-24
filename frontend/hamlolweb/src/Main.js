import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { jwtDecode } from "jwt-decode";

function Main() {
    const [userName, setUserName] = useState("");

    useEffect(() => {
        const token = localStorage.getItem("accessToken"); // GameList.js와 통일
        if (token) {
            const decoded = jwtDecode(token);
            setUserName(decoded.user_name); // 서버 JWT 구조가 이렇게 되어 있어야 함
        }
    }, []);

    return (
        <div className="main-container">
            <h1>{userName ? `${userName} 님, 환영합니다!` : "환영합니다!"}</h1>
            <div className="link">
                <Link to="/login">로그아웃</Link>
                <Link to="/savegame">전적 등록하기</Link>
                <Link to="/account">계정 연동하기</Link>
                <Link to="/gameList">전적 보기</Link>
            </div>
        </div>
    );
}

export default Main;
