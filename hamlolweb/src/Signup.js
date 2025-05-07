function Signup(){
    return (
        <div className="container">
            <h2>회원가입</h2>
            <form id="signupForm">
                <div className="group">
                    <label htmlFor="signup-email">이메일</label>
                    <input id="signup-email" type="text" name="email" placeholder="이메일 입력" required/>
                    <div className="email-error error hide">올바른 이메일 형식이 아닙니다.</div>
                </div>
                <div className="group">
                    <label htmlFor="signup-name">이름</label>
                    <input id="signup-name" type="text" name="username" placeholder="이름 입력" required/>
                    <div className="name-error error hide">이름은 최소 2자 이상이어야 합니다.</div>
                </div>
                <div className="group">
                    <label htmlFor="signup-password">비밀번호</label>
                    <input id="signup-password" type="password" name="password" placeholder="비밀번호 입력" required/>
                    <div className="password-error error hide">
                        8~16자, 대/소문자·숫자·특수문자 모두 포함해야 합니다.
                    </div>
                </div>
                <div className="group">
                    <label htmlFor="signup-password-retype">비밀번호 확인</label>
                    <input id="signup-password-retype" type="password" placeholder="비밀번호 재입력" required/>
                    <div className="mismatch-message error hide">
                        비밀번호가 일치하지 않습니다.
                    </div>
                    <div className="success-message error hide">
                        비밀번호가 일치합니다.
                    </div>
                </div>

                <div className="group">
                    <label htmlFor="signup-phone">전화번호</label>
                    <input id="signup-phone" type="text" name="number" placeholder="전화번호 입력" required/>
                    <div className="phone-error hide">숫자만 입력하세요 (예: 01012345678)</div>

                </div>
                <div className="group">
                    <input type="submit" className="button" value="회원가입"/>
                </div>
            </form>
            <div className="link">
                <a href="/login">로그인 하러가기</a>
            </div>
        </div>
    )
}
export{Signup};