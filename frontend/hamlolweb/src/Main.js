import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { jwtDecode } from "jwt-decode";

function Main() {
    const [userName, setUserName] = useState("");
    const [isLinked, setIsLinked] = useState(false); // 🔑 계정 연동 여부 상태

    useEffect(() => {
        const token = localStorage.getItem("accessToken");
        if (token) {
            const decoded = jwtDecode(token);
            console.log("🔥 JWT 내용:", decoded);

            // ✅ 계정연동 여부 판단
            if (decoded.gameName === "계정연동안됨" || decoded.tagLine === "계정연동안됨") {
                setUserName("");
                setIsLinked(false);
            } else {
                const fullName = `${decoded.gameName}#${decoded.tagLine}`;
                setUserName(fullName);
                setIsLinked(true);
            }
        }
    }, []);

    return (
        <div className="main-container">
            <h1>
                {userName ? (
                    <>
                        {userName} 님<br />
                        환영합니다!
                    </>
                ) : (
                    "환영합니다!"
                )}
            </h1>
            <div className="link">
                <Link to="/login">로그아웃</Link>
                <Link to="/savegame">전적 등록하기</Link>

                {!isLinked && <Link to="/account">계정 연동하기</Link>}

                <Link to="/gameList">전적 보기</Link>
            </div>
        </div>
    );
}

export default Main;
