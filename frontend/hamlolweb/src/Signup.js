import {useState} from "react";
import {useNavigate} from "react-router-dom";

function Signup(){
    const navigate = useNavigate();

    const [email, setEmail] = useState("");
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [password2, setPassword2] = useState("");
    const [phone, setPhone] = useState("");


    const [emailError, setEmailError] = useState(false);
    const [nameError, setNameError] = useState("");
    const [pwdError, setPwdError] = useState(false);
    const [pwd2Error, setPwd2Error] = useState(false);
    const [successMsg, setSuccessMsg] = useState(false);
    const [phoneError, setPhoneError] = useState(false);

    const emailId   = (value) => /^\S+@\S+\.\S+$/.test(value);
    const nameLong  = (value) => value.trim().length >= 2;
    const pwdHard   = (str)   => /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,16}$/.test(str);
    const pwdSame   = (p1, p2)=> p1 === p2;
    const phonNom   = (value) => /^\d{9,11}$/.test(value);

     const emailChange = (e) => {
           const v = e.target.value;
           setEmail(v);
           setEmailError(!emailId(v));
         };

    const usernameChange = (e) => {
        const v = e.target.value;
        setUsername(v);
        if (!v) {
            setNameError("");
        } else if (!nameLong(v)) {
            setNameError("이름은 최소 2자 이상이어야 합니다.");
        } else {
            setNameError("");
        }
    };

    const pwdChange = (e) => {
        const v = e.target.value;
        setPassword(v);
        setPwdError(v !== "" && !pwdHard(v));
        // 비밀번호 확인 검증 재실행
        setPwd2Error(password2 !== "" && !pwdSame(v, password2));
        setSuccessMsg(password2 !== "" && pwdSame(v, password2));
    };

    const pwd2Change = (e) => {
        const v = e.target.value;
        setPassword2(v);
        if (!v) {
            setPwd2Error(false);
            setSuccessMsg(false);
        } else if (pwdSame(password, v)) {
            setPwd2Error(false);
            setSuccessMsg(true);
        } else {
            setPwd2Error(true);
            setSuccessMsg(false);
        }
    };

    const phoneChange = (e) => {
        const v = e.target.value;
        setPhone(v);
        setPhoneError(!phonNom(v));
    };

    const handleSignup  = async (e) => {
        e.preventDefault();

        // 최종 검증
        if (!emailId(email))              return alert("이메일 형식을 확인하세요.");
        if (!nameLong(username))          return alert("이름은 최소 2자 이상이어야 합니다.");
        if (!pwdHard(password))           return alert("비밀번호 조건을 확인하세요.");
        if (!pwdSame(password, password2)) return alert("비밀번호가 일치하지 않습니다.");
        if (!phonNom(phone))              return alert("전화번호를 확인하세요.");

        const user = { email, username, password, number: phone ,user_type: "USER"};

        try {
            const res = await fetch("/api/adduser", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(user),
            });

            if (!res.ok) {
                const text = await res.text();
                throw new Error(text || res.status);
            }

            const result = await res.text();
            alert("회원가입 성공: " + result);
            navigate("/login");
        } catch (err) {
            alert("회원가입 실패: " + err.message);
        }
    };
    return (
        <div className="Signup-container">
            <h2>회원가입</h2>
            <form onSubmit={handleSignup} id="signupForm">
                <div className="group">
                    <label htmlFor="signup-email">이메일</label>
                    <input value={email} onChange={emailChange} id="signup-email" type="text" name="email"
                           placeholder="이메일 입력" required/>
                    {emailError && (
                        <div className="email-error">올바른 이메일 형식이 아닙니다.</div>
                    )}
                </div>
                <div className="group">
                    <label htmlFor="signup-name">이름</label>
                    <input value={username} onChange    ={usernameChange} id="signup-name" type="text" name="username" placeholder="이름 입력" required/>
                    {nameError && (
                        <div className="name-error">이름은 최소 2자 이상이어야 합니다.</div>
                    )}
                </div>
                <div className="group">
                    <label htmlFor="signup-password">비밀번호</label>
                    <input value={password} onChange={pwdChange} id="signup-password" type="password" name="password" placeholder="비밀번호 입력" required/>
                    {pwdError && (
                        <div className="pwd-error">8~16자, 대/소문자·숫자·특수문자 모두 포함해야 합니다.</div>
                    )}

                </div>
                <div className="group">
                    <label htmlFor="signup-password-retype">비밀번호 확인</label>
                    <input value={password2}  onChange={pwd2Change} id="signup-password-retype" type="password" placeholder="비밀번호 재입력" required/>
                    {pwd2Error && (
                        <div className="pwd2-error">비밀번호가 일치하지 않습니다.</div>
                    )}
                    {successMsg && (
                        <div className="success-message">비밀번호가 일치합니다.</div>
                    )}
                </div>

                <div className="group">
                    <label htmlFor="signup-phone">전화번호</label>
                    <input value={phone} onChange={phoneChange} id="signup-phone" type="text" name="number" placeholder="전화번호 입력" required/>
                    {phoneError && (
                        <div className="phone-error">숫자만 입력하세요 (예: 01012345678)</div>
                    )}
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