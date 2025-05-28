import { useState } from "react";
import { useNavigate } from "react-router-dom";


function SaveGame(){
    const navigate = useNavigate();
    const [gameCode, setGameCode] = useState("");
    console.log('gamecode',gameCode)

    const handleSave = async e => {
        e.preventDefault();
        const jwtToken = localStorage.getItem("accessToken");
        if (!jwtToken) {
            alert("로그인이 필요합니다.");
            navigate("/login");
            return;
        }
        const code = gameCode.trim();
        const matchId = code.startsWith("KR_") ? code : `KR_${code}`;
        try {
            const res = await fetch("/api/game/save", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": `Bearer ${jwtToken}`
                },
                body: JSON.stringify({ matchId })
            });
            if (!res.ok) throw new Error(await res.text() || res.status);
            alert(await res.text());
            navigate("/main");
        } catch (err) {
            alert(err.message);
        }
    };






    return (
        <div className="SaveGame-container">
            <h2>게임코드 등록</h2>
            <form onSubmit={handleSave} id="gameCodeForm">
                <div className="group">
                    <label htmlFor="gameCode">게임코드</label>
                    <input value={gameCode} onChange={e => setGameCode(e.target.value)}
                           id="gameCode" type="text" name="matchId" placeholder="게임코드 입력 (예: 7565284912)" required/>
                </div>
                <div className="group">
                    <input type="submit" className="button" value="등록"/>
                </div>
            </form>
            <div className="link">
                <button
                    type="button"
                    className="button"
                    style={{
                        background: "transparent",
                        textDecoration: "underline",
                        border: "none",
                        padding: 0,
                        cursor: "pointer"
                    }}
                    onClick={() => navigate("/main")}
                >
                    메인 페이지로 이동
                </button>            </div>
        </div>
    )
}
export default SaveGame;