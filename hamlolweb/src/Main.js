function Main(){

    return (
        <div className="container">
            <h2>로그인</h2>
            <form id="loginForm">
                <div className="group">
                    <label htmlFor="user">이메일</label>
                    <input id="user" type="text" name="email" placeholder="이메일 입력" required/>
                </div>
                <div className="group">
                    <label htmlFor="pass">비밀번호</label>
                    <input id="pass" type="password" name="password" placeholder="비밀번호 입력" required/>
                </div>
                <div className="group">
                    <input type="submit" className="button" value="로그인"/>
                </div>
            </form>
            <div className="link">
                <a href="signup.html">회원가입 하러가기</a>
            </div>
        </div>
    )
}

export default Main;
