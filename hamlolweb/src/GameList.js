// src/GameList.js
import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import './GameList.css';

export default function GameList() {
    const navigate = useNavigate();
    const [matches, setMatches] = useState([]);
    const [details, setDetails] = useState({});   // { [matchId]: detailData }
    const [expanded, setExpanded] = useState({}); // { [matchId]: boolean }
    const [keyToId, setKeyToId] = useState({});   // summoner spell key→id 매핑
    const [page, setPage] = useState(0);
    const [isLastPage, setIsLastPage] = useState(false);
    const pageSize = 10;
    const token = localStorage.getItem('accessToken');

    // 1) 스펠 매핑 (한 번만)
    useEffect(() => {
        fetch('https://ddragon.leagueoflegends.com/cdn/15.7.1/data/en_US/summoner.json')
            .then(res => res.json())
            .then(data => {
                const map = {};
                Object.values(data.data).forEach(s => map[s.key] = s.id);
                setKeyToId(map);
            })
            .catch(console.error);
    }, []);

    // 2) 매치 리스트 불러오기 함수
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
                setPage(prev => prev + 1);
            })
            .catch(console.error);
    };

    // 3) 컴포넌트 마운트 시 첫 페이지 로드
    useEffect(fetchMatches, []);

    // 4) 상세 토글 & 상세 데이터 fetch
    const toggleDetail = matchId => {
        setExpanded(e => ({ ...e, [matchId]: !e[matchId] }));
        if (!details[matchId]) {
            fetch(`/api/bymatchid?matchId=${matchId}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`,
                },
                body: JSON.stringify({ matchId }),
            })
                .then(res => res.json())
                .then(d => setDetails(ds => ({ ...ds, [matchId]: d })))
                .catch(console.error);
        }
    };

    // 5) “n일 전” 표시 헬퍼
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
                {matches.map((m, idx) => (
                    <div className="matchRow" key={`${m.matchId}-${idx}`}>

                        {/* 1) 요약 박스 */}
                        <div className={`box summary ${m.win ? '' : 'lose'}`}>
                            <div className="inner">
                <span className="text red title">
                  {m.gamemode === 'CLASSIC'
                      ? '소환사 협곡'
                      : m.gamemode === 'ARAM'
                          ? '칼바람 나락'
                          : m.gamemode}
                </span>
                                <span className="text">{timeAgo(m.gameCreation)}</span>
                            </div>
                            <div className="bar"></div>
                            <div className="inner">
                <span className={`text title ${m.win ? '' : 'gray'}`}>
                  {m.win ? '승리' : '패배'}
                </span>
                                <span className="text">
                  {Math.floor(m.gameDuration / 60)}분 {m.gameDuration % 60}초
                </span>
                            </div>
                        </div>

                        {/* 2) 메인 정보 박스 */}
                        <div className="box main">
                            <div className="inner info">
                                <div className="champ_img">
                                    <img
                                        src={`https://ddragon.leagueoflegends.com/cdn/15.7.1/img/champion/${m.championName}.png`}
                                        width="48" height="48"
                                        alt={m.championName}
                                    />
                                    <span className="level">{m.championLevel}</span>
                                </div>
                                <div className="spell">
                                    {[m.summoner1Id, m.summoner2Id].map((k, i) =>
                                            keyToId[k] && (
                                                <span key={i} className="spell_img">
                        <img
                            src={`https://ddragon.leagueoflegends.com/cdn/15.7.1/img/spell/${keyToId[k]}.png`}
                            width="22" height="22"
                            alt=""
                        />
                      </span>
                                            )
                                    )}
                                </div>
                                <div className="rune">
                                    {/* primaryStyle1, subStyle1 등 필요 시 여기에 */}
                                </div>
                                <div className="kda">
                  <span>
                    {m.kills} / <span className="red">{m.deaths}</span> / {m.assists}
                  </span>
                                    <span>{m.kda.toFixed(2)} 평점</span>
                                </div>
                            </div>
                            <div className="inner info">
                                <div className="item">
                                    {[0,1,2,3,4,5,6].map(i => {
                                        const code = m[`item${i}`];
                                        const src = code && code !== '0'
                                            ? `https://ddragon.leagueoflegends.com/cdn/15.7.1/img/item/${code}.png`
                                            : `https://ddragon.leagueoflegends.com/cdn/15.7.1/img/profileicon/588.png`;
                                        return <img key={i} src={src} width="22" height="22" alt="" />;
                                    })}
                                </div>
                            </div>
                        </div>

                        {/* 3) 접기/펼치기 버튼 (아이콘만) */}
                        <div className="box toggle">
                            <div className="button_inner">
                                <button type="button" onClick={() => toggleDetail(m.matchId)}>
                                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24">
                                        <path fill="currentColor" d="M12 13.2 16.5 9l1.5 1.4-6 5.6-6-5.6L7.5 9z" />
                                    </svg>
                                </button>
                            </div>
                        </div>

                    </div>
                ))}
            </div>

            {/* 4) 더보기 버튼 */}
            {!isLastPage && (
                <button id="more" onClick={fetchMatches}>더보기</button>
            )}
        </>
    );
}
