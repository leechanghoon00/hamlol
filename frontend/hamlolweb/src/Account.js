import { useState } from "react";
import { useNavigate } from "react-router-dom";
import './Account.css';

function Account() {
    const navigate = useNavigate();
    const [gameName, setGameName] = useState("");
    const [tagLine, setTagLine] = useState("");

    const handleAccount = async (e) => {
        e.preventDefault();
        const jwtToken = localStorage.getItem("accessToken");
        if (!jwtToken) {
            alert("로그인이 필요합니다.");
            navigate("/login");
            return;
        }
        try {
            const res = await fetch("/api/account", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": `Bearer ${jwtToken}`,
                },
                body: JSON.stringify({ gameName, tagLine }),
            });
            if (!res.ok) {
                const text = await res.text();
                throw new Error(text || res.status);
            }
            alert("계정 연동 성공");
            navigate("/main");
        } catch (err) {
            alert("연동 실패: " + err.message);
        }
    };

    return (
        <div className="account-container">
            <div className="account-box">
                <h2>계정 연동</h2>
                <form onSubmit={handleAccount} id="accountLinkForm">
                    <div className="form-group">
                        <label htmlFor="gameName" className="label">
                            롤 아이디 (소환사명)
                        </label>
                        <input
                            id="gameName"
                            type="text"
                            className="input"
                            placeholder=" 대소문자 정확하게 입력"
                            value={gameName}
                            onChange={(e) => setGameName(e.target.value)}
                            required
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="tagLine" className="label">
                            태그라인
                        </label>
                        <input
                            id="tagLine"
                            type="text"
                            className="input"
                            placeholder="# 제외하고, 대소문자 정확하게 입력"
                            value={tagLine}
                            onChange={(e) => setTagLine(e.target.value)}
                            required
                        />
                    </div>
                    <div className="form-group">
                        <input type="submit" className="button" value="연동" />
                    </div>
                </form>
                <div className="hr" />
                <div className="foot-lnk">
                    <button
                        type="button"
                        onClick={() => navigate("/main")}
                        style={{
                            background: "none",
                            border: "none",
                            cursor: "pointer",
                            textDecoration: "underline",
                            padding: 0
                        }}
                    >
                        메인 페이지로 돌아가기
                    </button>

                </div>
            </div>
        </div>
    );
}

export default Account;
