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

            if (!res.ok) throw new Error("메일 전송 실패. 이메일을 다시 확인해주세요.");

            setMessage("📩 비밀번호 재설정 링크가 이메일로 전송되었습니다.");
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
        </div>
    );
}

export default FindPassword;
