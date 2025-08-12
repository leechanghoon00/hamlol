import React, { useEffect, useRef } from "react";
import { useNavigate, useSearchParams } from "react-router-dom";
import "./Callback.css";

export default function Callback() {
    const navigate = useNavigate();
    const [searchParams] = useSearchParams();
    const fired = useRef(false); // StrictMode 이중 호출 방지

    useEffect(() => {
        if (fired.current) return;

        const code = searchParams.get("code");
        const state = searchParams.get("state");

        if (!code) {
            console.error("Authorization code not found");
            navigate("/login", { replace: true });
            return;
        }

        fired.current = true;
        (async () => {
            try {
                const response = await fetch("/api/auth/riot/callback", {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    credentials: "include", // 쿠키/세션 사용 시 필요
                    body: JSON.stringify({ code, state }),
                });

                if (!response.ok) throw new Error("Login failed");

                const data = await response.json().catch(() => ({}));
                if (data && data.accessToken) {
                    localStorage.setItem("accessToken", data.accessToken);
                }

                // 성공 후 뒤로가기로 callback 화면이 남지 않도록 replace
                navigate("/main", { replace: true });
            } catch (error) {
                console.error("Riot login error:", error);
                navigate("/login?error=callback", { replace: true });
            }
        })();
    }, [searchParams, navigate]);

    return (
        <div className="callback-container">
            <div className="callback-message">로그인 처리 중...</div>
            <div className="callback-spinner"></div>
        </div>
    );
}
