import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { jwtDecode } from "jwt-decode";

function Main() {
    const [userName, setUserName] = useState("");
    const [isLinked, setIsLinked] = useState(false);
    const [profileImageUrl, setProfileImageUrl] = useState("");

    useEffect(() => {
        const token = localStorage.getItem("accessToken");
        if (!token) return;

        try {
            const decoded = jwtDecode(token);
            const { gameName, tagLine } = decoded;

            // 계정 연동 여부 판단
            if (gameName === "계정연동안됨" || tagLine === "계정연동안됨") {
                setIsLinked(false);
                setUserName("");
            } else {
                setIsLinked(true);
                setUserName(`${gameName}#${tagLine}`);
            }

            // 프로필 이미지 경로 요청
            fetch("/api/user/me", {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            })
                .then((res) => {
                    if (!res.ok) throw new Error("사용자 정보 불러오기 실패");
                    return res.json();
                })
                .then((data) => {
                    setProfileImageUrl(`${data.profileImageUrl}?t=${Date.now()}`); // 캐시 방지용
                })
                .catch((err) => {
                    console.error("❌ 프로필 이미지 요청 실패:", err);
                });
        } catch (err) {
            console.error("❌ JWT 디코딩 오류:", err);
        }
    }, []);

    const handleImageChange = async (e) => {
        const file = e.target.files[0];
        if (!file) return;

        // 파일 크기 체크 (10MB = 10 * 1024 * 1024 bytes)
        const maxSize = 10 * 1024 * 1024; // 10MB
        if (file.size > maxSize) {
            alert("파일 크기가 너무 큽니다. 10MB 이하로 업로드해주세요.");
            return;
        }

        if (!["image/jpeg", "image/png", "image/jpg"].includes(file.type)) {
            alert("JPG, JPEG, PNG 형식만 업로드할 수 있습니다.");
            return;
        }

        const formData = new FormData();
        formData.append("file", file);
        const token = localStorage.getItem("accessToken");

        try {
            const res = await fetch("/api/profile/upload", {
                method: "POST",
                headers: {
                    Authorization: `Bearer ${token}`,
                },
                body: formData,
            });

            if (!res.ok) {
                const errMsg = await res.text();
                throw new Error(errMsg || "업로드 실패");
            }

            const newUrl = await res.text();
            setProfileImageUrl(`${newUrl}?t=${Date.now()}`); // 캐시 방지
        } catch (err) {
            console.error("❌ 이미지 업로드 실패:", err);
            alert("이미지 업로드 실패: " + err.message);
        }
    };

    return (
        <div className="main-container">
            {profileImageUrl && (
                <img
                    src={profileImageUrl}
                    alt="프로필"
                    style={{
                        width: 100,
                        height: 100,
                        borderRadius: "50%",
                        objectFit: "cover",
                        marginBottom: 12,
                    }}
                />
            )}

            <h1>
                {userName ? (
                    <>
                        {userName} 님<br />환영합니다!
                    </>
                ) : (
                    "환영합니다!"
                )}
            </h1>

            {/* 프로필 업로드 버튼 (계정 연동된 경우에만) */}
            {isLinked && (
                <>
                    <label htmlFor="uploadProfile" className="upload-button">
                        프로필 업로드
                    </label>
                    <input
                        id="uploadProfile"
                        type="file"
                        accept="image/*"
                        style={{ display: "none" }}
                        onChange={handleImageChange}
                    />
                </>
            )}

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
