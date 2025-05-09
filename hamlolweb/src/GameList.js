import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

function GameList() {
    const navigate = useNavigate();
    const [matches, setMatches] = useState([]);
    const [page, setPage] = useState(0);
    const pageSize = 10;
    const [isLastPage, setIsLastPage] = useState(false);
    const [details, setDetails] = useState({});    // { [matchId]: detailData }
    const [expanded, setExpanded] = useState({});  // { [matchId]: boolean }
    const token = localStorage.getItem('accessToken');

    // 시간 차 계산
    const timeAgo = (ts) => {
        const diff = Date.now() - ts;
        return `${Math.floor(diff/(1000*60*60*24))}일 전`;
    };

    // 매치 리스트 불러오기
    const fetchMatches = async () => {
        if (isLastPage) return;
        try {
            const res = await fetch(`/api/bygameid?page=${page}&size=${pageSize}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                },
            });
            if (!res.ok) throw new Error(res.status);
            const data = await res.json();
            setMatches(prev => [...prev, ...data.content]);
            setIsLastPage(data.last);
            setPage(p => p + 1);
        } catch (err) {
            console.error('매치 로드 실패', err);
        }
    };

    useEffect(() => {
        fetchMatches();
    }, []);

    // 상세 정보 토글 및 fetch
    const toggleDetail = async (matchId) => {
        // 닫기
        if (expanded[matchId]) {
            setExpanded(prev => ({ ...prev, [matchId]: false }));
            return;
        }
        // 아직 fetch 안 한 경우
        if (!details[matchId]) {
            try {
                const res = await fetch(`/api/bymatchid?matchId=${matchId}`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': `Bearer ${token}`
                    },
                    body: JSON.stringify({ matchId }),
                });
                if (!res.ok) throw new Error(res.status);
                const detailData = await res.json();
                setDetails(prev => ({ ...prev, [matchId]: detailData }));
            } catch (err) {
                console.error('상세 불러오기 실패', err);
                return;
            }
        }
        setExpanded(prev => ({ ...prev, [matchId]: true }));
    };

    return (
        <>
            <button className="back-btn" onClick={() => navigate('/main')}>
                ← 메인으로 돌아가기
            </button>

            {matches.map(m => (
                <div
                    key={m.matchId}
                    className={`match-card ${m.win ? '' : 'lose'}`}
                >
                    <div className="match-header">
                        <div style={{ display: 'flex', gap: 12, flexWrap: 'wrap' }}>
                            <div className="champion">
                                <img
                                    src={`https://ddragon.leagueoflegends.com/cdn/15.7.1/img/champion/${m.championName}.png`}
                                    alt={m.championName}
                                    className="champion-image"
                                />
                                <div><strong>{m.riotIdGameName}#{m.riotIdTagline}</strong></div>
                            </div>
                            <div className="info">
                                <div>포지션: {m.teamPosition}</div>
                                <div className="kda">
                                    {m.kills}/{m.deaths}/{m.assists} (KDA: {m.kda.toFixed(2)})
                                </div>
                                <div>{timeAgo(m.gameCreation)}</div>
                                <div>시작: {new Date(m.gameCreation).toLocaleString()}</div>
                                <div>시간: {Math.floor(m.gameDuration/60)}분 {m.gameDuration%60}초</div>
                            </div>
                        </div>
                        <button
                            className={`toggle-btn${expanded[m.matchId] ? ' open' : ''}`}
                            onClick={() => toggleDetail(m.matchId)}
                        >
                            ▶
                        </button>
                    </div>

                    {expanded[m.matchId] && details[m.matchId] && (
                        <div className="match-detail">
                            <table>
                                <thead>
                                <tr>
                                    <th>챔피언</th>
                                    <th>닉네임</th>
                                    <th>K/D/A</th>
                                    <th>라인</th>
                                    <th>CS</th>
                                    <th>피해량</th>
                                </tr>
                                </thead>
                                <tbody>
                                {details[m.matchId].map(p => (
                                    <tr key={p.puuid} className={p.win ? 'blue-row' : 'red-row'}>
                                        <td>
                                            <img
                                                src={`https://ddragon.leagueoflegends.com/cdn/15.7.1/img/champion/${p.championName}.png`}
                                                className="champion-image"
                                                alt={p.championName}
                                            />
                                        </td>
                                        <td>{p.riotIdGameName}#{p.riotIdTagline}</td>
                                        <td>
                                            {p.kills}/{p.deaths}/{p.assists}
                                            <span className="kda-stats">({p.kda.toFixed(2)})</span>
                                        </td>
                                        <td>{p.teamPosition}</td>
                                        <td>{p.totalMinionsKilled}</td>
                                        <td data-value={p.totalDamageDealtToChampions}></td>
                                    </tr>
                                ))}
                                </tbody>
                            </table>
                        </div>
                    )}
                </div>
            ))}

            {!isLastPage && (
                <button id="more" className="btn btn-danger" onClick={fetchMatches}>
                    더보기
                </button>
            )}
        </>
    );
}

export default GameList;
