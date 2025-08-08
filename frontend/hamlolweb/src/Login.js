import React, { useState } from "react";
import {useNavigate} from "react-router-dom";

function Main(){
    const navigate = useNavigate();
    const [email,setEmail] = useState("")
    // console.log('email',email);
    const emailChange = (e) => {
        setEmail(e.target.value)

    }

    const [pwd ,setPwd] = useState("")
    // console.log('pwd',pwd);
    const pwdChange = (e) => {
        setPwd(e.target.value)
    }

    const handleLogin = async (e) => {
        e.preventDefault();
        const loginData = { email: email, password: pwd };

        try {
            // 1. 로그인 요청
            const loginRes = await fetch("/api/login", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(loginData)
            });
            console.log(loginRes)
            if (!loginRes.ok) throw new Error(`로그인 실패 (${loginRes.status})`);

            const { accessToken } = await loginRes.json();

            // 2. 토큰 저장
            localStorage.setItem("accessToken", accessToken);

            navigate("/main")

        } catch (err) {
            console.error(err);
            alert(err.message);
        }
    };

    return (
        <div className="login-container">
            <h2>Hamlol</h2>
            <form onSubmit={handleLogin} id="loginForm">
                <div className="group">
                    <label htmlFor="user">이메일</label>
                    <input value={email} onChange={emailChange} id="user" type="text" name="email" placeholder="이메일 입력"
                           required/>
                </div>
                <div className="group">
                    <label htmlFor="pass">비밀번호</label>
                    <input value={pwd} onChange={pwdChange} id="pass" type="password" name="password"
                           placeholder="비밀번호 입력" required/>
                </div>
                <div className="group">
                    <input type="submit" className="button" value="로그인"/>
                </div>
            </form>
            <div className="link">
                <a href="/signup">회원가입 하러가기</a>
            </div>

            {/*<div className="find-password-wrapper">*/}
            {/*    <span className="findpassword">비밀번호를 잊으셨나요?</span>*/}
            {/*    <Link to="/FindPassword" className="find-password-link">*/}
            {/*        비밀번호 찾기*/}
            {/*    </Link>*/}
            {/*</div>*/}


        </div>
    )


}

export default Main;
