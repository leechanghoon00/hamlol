import React, { useState } from "react";
import "./FindPassword.css"; // ✅ CSS 연결

function FindPassword() {
    const [email, setEmail] = useState("");
    const [message, setMessage] = useState(null);
    const [error, setError] = useState(null);

    const handleSend = async (e) => {
        e.preventDefault();
        setMessage(null);
        setError(null);

        try {
            const res = await fetch("/api/send-reset-password", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ email }),
            });

            const data = await res.json(); // ✅ JSON 응답 파싱

            if (!res.ok) {
                //  에러 응답인 경우, data.error 존재
                throw new Error(data.error || "오류가 발생했습니다.");
            }

            // ✅ 성공 응답 처리
            if (data.uuid?.startsWith("메일 전송은 완료되었으나")) {
                setMessage("메일은 전송되었지만, 인증 링크 저장에 문제가 있을 수 있습니다.");
            } else {
                setMessage("📩 비밀번호 재설정 링크가 이메일로 전송되었습니다.");
            }
        } catch (err) {
            setError(err.message);
        }
    };

    return (
        <div className="find-password-container">
            <h2>비밀번호 재설정</h2>
            <form onSubmit={handleSend}>
                <label htmlFor="email">가입한 이메일 주소</label>
                <input
                    id="email"
                    type="email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    placeholder="이메일을 입력하세요"
                    required
                />
                <button type="submit">메일 전송</button>
            </form>
            {message && <p className="success">{message}</p>}
            {error && <p className="error">{error}</p>}
            <div className="link">
                <a href="/login">로그인 하러가기</a>
            </div>
        </div>
    );
}

export default FindPassword;
