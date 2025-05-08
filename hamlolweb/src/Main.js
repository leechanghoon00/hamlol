import React from "react";
import { Link } from "react-router-dom";

function Main() {
    return (
        <div className="container">
            <h1>환영합니다!</h1>
            <p>로그인에 성공했습니다.</p>
            <p>이곳은 메인 페이지입니다.</p>
            <div className="link">
                <Link to="/logout">로그아웃</Link>
                <Link to="/savegame">전적 등록하기</Link>
                <Link to="/account">계정 연동하기</Link>
                <Link to="/gameList">전적 보기</Link>
            </div>
        </div>
    );
}

export default Main;
