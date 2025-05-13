// src/GameList.js
import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import './GameList.css';

export default function GameList() {
    const navigate = useNavigate();

    // ---- state 정의 ----
    const [matches, setMatches] = useState([]);
    const [details, setDetails] = useState({});      // { [matchId]: detailData }
    const [expanded, setExpanded] = useState({});    // { [matchId]: boolean }
    const [keyToId, setKeyToId] = useState({});      // summoner spell key→id 매핑
    const [page, setPage] = useState(0);
    const [isLastPage, setIsLastPage] = useState(false);
    const pageSize = 10;
    const token = localStorage.getItem('accessToken');

    // 1) 스펠 매핑 불러오기 (한 번만)
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

                {matches.map(m => (


                    <div key={m.matchId} className={`box ${m.win ? '' : 'lose'}`}>
                        {/* 1. 요약 */}
                        <div className="inner">
              <span className="text red title">
                {m.gamemode === 'CLASSIC' ? '소환사 협곡 ' : m.gamemode}
              </span>
                            <span className="text">{timeAgo(m.gameCreation)}</span>
                        </div>

                        {/* 2. 구분선 */}
                        <div className="bar" />

                        {/* 3. 승패 + 시간 */}
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
                                    width="48" height="48"
                                    alt={m.championName}
                                />
                                <span className="level">{m.championLevel}</span>
                            </div>

                            {/* spells */}
                            <div className="spell">
                                {[m.summoner1Id, m.summoner2Id].map((k, i) => (
                                    keyToId[k]
                                        ? <img
                                            key={i}
                                            src={`https://ddragon.leagueoflegends.com/cdn/15.7.1/img/spell/${keyToId[k]}.png`}
                                            width="22" height="22"
                                            alt=""
                                        />
                                        : null
                                ))}
                            </div>

                            {/* runes */}
                            <div className="rune">
                                {/* 메인 룬 경로 앞에만 prefix */}
                                <img
                                    src={`https://ddragon.leagueoflegends.com/cdn/img/${m.primaryStyle1}`}
                                    width="22"
                                    height="22"
                                    alt="primary rune"
                                />
                                {/* 서브 룬도 동일하게 */}
                                <img
                                    src={`https://ddragon.leagueoflegends.com/cdn/img/${m.subStyle1}`}
                                    width="22"
                                    height="22"
                                    alt="sub rune"
                                />

                            </div>


                            <div className="kda">
                <span>
                  {m.kills} / <span className="red">{m.deaths}</span> / {m.assists}
                </span>
                                <span>{m.kda.toFixed(2)} 평점</span>
                            </div>
                        </div>

                        {/* 5. 아이템 + 라벨 (여기 추가했습니다) */}
                        <div className="inner info">
                            <div className="item">
                                {[0, 1, 2, 3, 4, 5, 6].map(i => {
                                    const code = m[`item${i}`];
                                    const src =
                                        code && code !== '0'
                                            ? `https://ddragon.leagueoflegends.com/cdn/15.7.1/img/item/${code}.png`
                                            : `https://ddragon.leagueoflegends.com/cdn/15.7.1/img/profileicon/588.png`;
                                    return (
                                        <img
                                            key={i}
                                            src={src}
                                            width="22"
                                            height="22"
                                            alt={code !== '0' ? `Item ${code}` : '빈 슬롯'}
                                        />
                                    );
                                })}
                            </div>
                            <div className="label">
                                {m.kills >= 2 && <span className="background_red">더블킬</span>}
                                {m.win && <span className="background_purple">ACE</span>}
                            </div>
                        </div>

                        {/* 6. 접기/펼치기 버튼 */}
                        <div className="box button">
                            <div className="button_inner">
                                <button onClick={() => toggleDetail(m.matchId)}>
                                    {expanded[m.matchId] ? '접기' : '펼치기'}
                                </button>
                            </div>
                        </div>

                        {/* 7. 상세 테이블 */}
                        {expanded[m.matchId] && details[m.matchId] && (
                            <table className="detail-table">
                                <thead>
                                <tr>
                                    <th>챔프</th><th>닉네임</th>
                                    <th>스펠</th><th>룬</th><th>K/D/A</th>
                                </tr>
                                </thead>
                                <tbody>
                                {details[m.matchId].map(p => (
                                    <tr key={p.puuid} className={p.win ? 'blue-row' : 'red-row'}>
                                        <td>
                                            <img
                                                src={`https://ddragon.leagueoflegends.com/cdn/15.7.1/img/champion/${p.championName}.png`}
                                                width="24" height="24"
                                                alt={p.championName}
                                            />
                                        </td>
                                        <td>{p.riotIdGameName}#{p.riotIdTagline}</td>
                                        <td>
                                            {[p.summoner1Id, p.summoner2Id].map((k,i) =>
                                                keyToId[k]
                                                    ? <img
                                                        key={i}
                                                        src={`https://ddragon.leagueoflegends.com/cdn/15.7.1/img/spell/${keyToId[k]}.png`}
                                                        width="20" height="20"
                                                        alt=""
                                                    />
                                                    : null
                                            )}
                                        </td>
                                        <td>
                                            {[
                                                p.primaryStyle1, p.primaryStyle2, p.primaryStyle3,
                                                p.subStyle1, p.subStyle2
                                            ].map((u,i) =>
                                                <img
                                                    key={i}
                                                    src={`https://ddragon.leagueoflegends.com/cdn/img/${u}`}
                                                    width="20" height="20"
                                                    alt=""
                                                />
                                            )}
                                        </td>
                                        <td>{p.kills}/{p.deaths}/{p.assists}</td>
                                    </tr>
                                ))}
                                </tbody>
                            </table>
                        )}
                    </div>
                ))}
            </div>

            {!isLastPage && (
                <button id="more" onClick={fetchMatches}>더보기</button>
            )}
        </>
    );
}
