// src/GameList.js
import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import './GameList.css';

export default function GameList() {
    const navigate = useNavigate();

    // ---- state 정의 ----
    const [matches, setMatches]     = useState([]);
    const [details, setDetails]     = useState({});     // { [matchId]: participantArray }
    const [expanded, setExpanded]   = useState({});     // { [matchId]: boolean }
    const [keyToId, setKeyToId]     = useState({});     // spell key→id 매핑
    const [page, setPage]           = useState(0);
    const [isLastPage, setIsLastPage] = useState(false);
    const pageSize = 10;
    const token    = localStorage.getItem('accessToken');

    // 1) 스펠 매핑 한 번만 불러오기
    useEffect(() => {
        fetch('https://ddragon.leagueoflegends.com/cdn/15.7.1/data/en_US/summoner.json')
            .then(res => res.json())
            .then(data => {
                const map = {};
                Object.values(data.data).forEach(s => {
                    map[s.key] = s.id;
                });
                setKeyToId(map);
            })
            .catch(console.error);
    }, []);

    // 2) 매치 리스트 불러오기
    const fetchMatches = () => {
        if (isLastPage) return;
        fetch(`/api/bygameid?page=${page}&size=${pageSize}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`,
            }
        })
            .then(res => {
                if (!res.ok) throw new Error(res.status);
                return res.json();
            })
            .then(data => {
                setMatches(prev => [...prev, ...data.content]);
                setIsLastPage(data.last);
                setPage(p => p + 1);
            })
            .catch(console.error);
    };

    // 3) 상세 토글 & fetch
    const toggleDetail = matchId => {
        setExpanded(prev => ({ ...prev, [matchId]: !prev[matchId] }));
        if (!details[matchId]) {
            fetch(`/api/bymatchid?matchId=${matchId}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`,
                },
                body: JSON.stringify({ matchId }),
            })
                .then(res => {
                    if (!res.ok) throw new Error(res.status);
                    return res.json();
                })
                .then(d => {
                    setDetails(prev => ({ ...prev, [matchId]: d }));
                })
                .catch(console.error);
        }
    };

    // 4) 초기 로드
    useEffect(fetchMatches, []);

    // 5) 시간차 계산 헬퍼
    const timeAgo = ts => {
        const diff = Date.now() - ts;
        return `${Math.floor(diff / (1000 * 60 * 60 * 24))}일 전`;
    };

    return (
        <>
            <button className="back-btn" onClick={() => navigate('/main')}>
                ← 메인으로 돌아가기
            </button>

            <div className="container">
                {matches.map(m => {
                    // (1) 상세 데이터 배열
                    const participants = details[m.matchId] || [];
                    // (2) 이 카드의 챔피언과 일치하는 “내” 참가자
                    const me = participants.find(p => p.championName === m.championName);

                    return (
                        <div key={m.matchId} className={`box ${m.win ? '' : 'lose'}`}>
                            {/* 1. 요약 */}
                            <div className="inner">
                <span className="text red title">
                  {m.gamemode === 'CLASSIC' ? '개인/2인 랭크' : m.gamemode}
                </span>
                                <span className="text">{timeAgo(m.gameCreation)}</span>
                            </div>

                            {/* 2. 구분선 */}
                            <div className="bar" />

                            {/* 3. 승패 + 게임시간 */}
                            <div className="inner">
                <span className={`text title ${m.win ? '' : 'gray'}`}>
                  {m.win ? '승리' : '패배'}
                </span>
                                <span className="text">
                  {Math.floor(m.gameDuration / 60)}분 {m.gameDuration % 60}초
                </span>
                            </div>

                            {/* 4. 챔프/스펠/룬/KDA */}
                            <div className="inner info">
                                <div className="champ_img">
                                    <img
                                        src={`https://ddragon.leagueoflegends.com/cdn/15.7.1/img/champion/${m.championName}.png`}
                                        width="48"
                                        height="48"
                                        alt={m.championName}
                                    />
                                    <span className="level">{m.championLevel}</span>
                                </div>

                                {/* spells */}
                                <div className="spell">
                                    {[m.summoner1Id, m.summoner2Id].map((k, i) =>
                                        keyToId[k] ? (
                                            <img
                                                key={i}
                                                src={`https://ddragon.leagueoflegends.com/cdn/15.7.1/img/spell/${keyToId[k]}.png`}
                                                width="22"
                                                height="22"
                                                alt=""
                                            />
                                        ) : null
                                    )}
                                </div>

                                {/* runes */}
                                <div className="rune">
                                    {me ? (
                                        <>
                                            <img
                                                src={`https://ddragon.leagueoflegends.com/cdn/img/${me.primaryStyle1}`}
                                                width="22"
                                                height="22"
                                                alt="primary rune"
                                            />
                                            <img
                                                src={`https://ddragon.leagueoflegends.com/cdn/img/${me.subStyle1}`}
                                                width="22"
                                                height="22"
                                                alt="sub rune"
                                            />
                                        </>
                                    ) : (
                                        <span className="text">룬 로딩중…</span>
                                    )}
                                </div>

                                <div className="kda">
                  <span>
                    {m.kills} / <span className="red">{m.deaths}</span> / {m.assists}
                  </span>
                                    <span>{m.kda.toFixed(2)} 평점</span>
                                </div>
                            </div>

                            {/* 5. 접기/펼치기 버튼 */}
                            <div className="box button">
                                <div className="button_inner">
                                    <button onClick={() => toggleDetail(m.matchId)}>
                                        {expanded[m.matchId] ? '접기' : '펼치기'}
                                    </button>
                                </div>
                            </div>
                        </div>
                    );
                })}
            </div>

            {!isLastPage && (
                <button id="more" onClick={fetchMatches}>
                    더보기
                </button>
            )}
        </>
    );
}
