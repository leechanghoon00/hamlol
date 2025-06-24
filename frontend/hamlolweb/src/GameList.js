// src/GameList.js
import React, { useState, useEffect, useRef } from 'react';
import { useNavigate } from 'react-router-dom';
export default function GameList() {
    const navigate = useNavigate();
    const [matches, setMatches] = useState([]);
    const [details, setDetails] = useState({});   // { matchId: detailData[] }
    const [expanded, setExpanded] = useState({}); // { matchId: boolean }
    const [keyToId, setKeyToId] = useState({});
    const [page, setPage] = useState(0);
    const [isLastPage, setIsLastPage] = useState(false);
    const pageSize = 10;
    const token = localStorage.getItem('accessToken');
    const didFetch = useRef(false);

    // 0) 정렬 기준: 라인 순서
    const order = ['TOP','JUNGLE','MIDDLE','BOTTOM','UTILITY'];

    // 1) 스펠 키→아이디 매핑 (한 번만)
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

    // 2) 첫 페이지 로드 (한 번만)
    useEffect(() => {
        if (!didFetch.current) {
            fetchMatches();
            didFetch.current = true;
        }
    }, []);

    // 3) 매치 리스트 불러오기

    const fetchMatches = useCallback(() => {
        if (isLastPage) return;
        fetch(`/api/bygameid?page=${page}&size=${pageSize}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            }
        })
            .then(res => res.json())
            .then(data => {
                setMatches(prev => [...prev, ...data.content]);
                setIsLastPage(data.last);
                setPage(prev => prev + 1);
            })
            .catch(console.error);
    }, [page, pageSize, isLastPage, token]);

    useEffect(() => {
        if (!didFetch.current) {
            fetchMatches();
            didFetch.current = true;
        }
    }, [fetchMatches]);


    // 4) 상세 토글 & 데이터 fetch
    const toggleDetail = matchId => {
        setExpanded(e => ({ ...e, [matchId]: !e[matchId] }));
        if (!expanded[matchId] && !details[matchId]) {
            fetch(`/api/bymatchid?matchId=${matchId}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                },
                body: JSON.stringify({ matchId })
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

            <div className="GameList-container">
                {matches.map((m, idx) => (
                    <React.Fragment key={`${m.matchId}-${idx}`}>
                        {/* ── 메인 한 줄 ── */}
                        <div className={`matchRow ${m.win ? 'win' : 'lose'}`}>
                            {/* 1) 요약 박스 */}
                            <div className="box summary">
                                <div className="inner column">
                                    <span className="text red title">
                                        {m.gamemode === 'CLASSIC'
                                            ? '소환사의 협곡'
                                            : m.gamemode === 'ARAM'
                                                ? '칼바람 나락'
                                                : m.gamemode}
                                    </span>
                                    <span className="text">{timeAgo(m.gameCreation)}</span>
                                </div>
                                <div className="bar" />
                                <div className="inner column">
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
                                            width="48" height="48" alt={m.championName}
                                        />
                                        <span className="level">{m.championLevel}</span>
                                    </div>
                                    <div className="spell">
                                        {[m.summoner1Id, m.summoner2Id].map((k,i) =>
                                            keyToId[k] ? (
                                                <span key={i} className="spell_img">
                                                    <img
                                                        src={`https://ddragon.leagueoflegends.com/cdn/15.7.1/img/spell/${keyToId[k]}.png`}
                                                        width="22" height="22" alt=""
                                                    />
                                                </span>
                                            ) : null
                                        )}
                                    </div>
                                    <div className="rune">{/* 룬 표시 */}</div>
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

                            {/* 3) 접기/펼치기 버튼 박스 */}
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

                        {/* ── 상세보기 테이블 ── */}
                        {expanded[m.matchId] && details[m.matchId] && (() => {
                            // 팀 분리 & 정렬
                            const loseTeam = details[m.matchId]
                                .filter(p => !p.win)
                                .sort((a,b) => order.indexOf(a.teamPosition) - order.indexOf(b.teamPosition));
                            const winTeam  = details[m.matchId]
                                .filter(p => p.win)
                                .sort((a,b) => order.indexOf(a.teamPosition) - order.indexOf(b.teamPosition));

                            // K/D/A 합계 계산
                            const sumStats = team => team.reduce((a,p) => ({
                                kills: a.kills + p.kills,
                                deaths: a.deaths + p.deaths,
                                assists: a.assists + p.assists
                            }), { kills:0, deaths:0, assists:0 });

                            const loseStats = sumStats(loseTeam);
                            const winStats  = sumStats(winTeam);

                            // 오브젝트 통계 라벨
                            const statsData = [
                                ['타워','towerKills'],
                                ['억제기','inhibitorKills'],
                                ['용','dragonKills'],
                                ['유충','hordeKills'],
                                ['전령','riftHeraldKills'],
                                ['바론','baronKills']
                            ];

                            return (
                                <div className="matchDetailRow">
                                    <table className="detail-table">
                                        <thead>
                                        <tr>
                                            <th>챔프</th><th>닉네임</th><th>스펠</th><th>룬</th>
                                            <th>K/D/A</th><th>라인</th><th>아이템</th><th>CS</th>
                                            <th>가한피해량</th><th>받은피해</th><th>힐량</th>
                                            <th>타워피해</th><th>시야점수</th>
                                        </tr>
                                        </thead>
                                        <tbody>


                                        {/* 2) 패배팀 멤버 */}
                                        {loseTeam.map(p => (
                                            <tr key={p.puuid} className="red-row">
                                                <td>
                                                    <img
                                                        src={`https://ddragon.leagueoflegends.com/cdn/15.7.1/img/champion/${p.championName}.png`}
                                                        width="24" height="24" alt={p.championName}
                                                    />
                                                </td>
                                                <td>{p.riotIdGameName}#{p.riotIdTagline}</td>
                                                <td>
                                                    {[p.summoner1Id, p.summoner2Id].map((k, i) => keyToId[k] ? (
                                                        <img key={i}
                                                             src={`https://ddragon.leagueoflegends.com/cdn/15.7.1/img/spell/${keyToId[k]}.png`}
                                                             width="20" height="20" alt="spell"/>
                                                    ) : null)}
                                                </td>
                                                <td>
                                                    {[p.primaryStyle1, p.primaryStyle2, p.primaryStyle3, p.subStyle1, p.subStyle2]
                                                        .map((u, i) => (
                                                            <img key={i}
                                                                 src={`https://ddragon.leagueoflegends.com/cdn/img/${u}`}
                                                                 width="20" height="20" alt="rune"/>
                                                        ))}
                                                </td>
                                                <td>{p.kills}/{p.deaths}/{p.assists}</td>
                                                <td>{p.teamPosition}</td>
                                                <td>
                                                    {[0, 1, 2, 3, 4, 5, 6].map(i => {
                                                        const c = p[`item${i}`];
                                                        const src = c && c !== '0'
                                                            ? `https://ddragon.leagueoflegends.com/cdn/15.7.1/img/item/${c}.png`
                                                            : `https://ddragon.leagueoflegends.com/cdn/15.7.1/img/profileicon/588.png`;
                                                        return (
                                                            <img
                                                                key={i}
                                                                src={src}
                                                                width="20"
                                                                height="20"
                                                                alt={c && c !== '0' ? `item-${c}` : 'empty-slot'}
                                                            />
                                                        );
                                                    })}
                                                </td>

                                                <td>{p.totalMinionsKilled}</td>
                                                <td>{p.totalDamageDealtToChampions}</td>
                                                <td>{p.totalDamageTaken}</td>
                                                <td>{p.totalHealsOnTeammates}</td>
                                                <td>{p.damageDealtToBuildings}</td>
                                                <td>{p.visionScore}</td>
                                            </tr>
                                        ))}
                                        {/* 1) 패배팀 레이블 */}
                                        <tr className="label-row">
                                            <td colSpan={13}>
                                                패배팀 ({loseStats.kills}/{loseStats.deaths}/{loseStats.assists})
                                                {/* 밴 챔프 */}
                                                {JSON.parse(loseTeam[0].bans).map((ban,i) => (
                                                    <img
                                                        key={i}
                                                        className="ban-img"
                                                        src={`https://ddragon.leagueoflegends.com/cdn/15.7.1/img/champion/${ban.championId}.png`}
                                                        alt="ban"
                                                    />
                                                ))}
                                                {/* 오브젝트 통계 */}
                                                {statsData.map(([label,key]) => (
                                                    <span key={key} className="stat-text">
                                                            {label}: {loseTeam[0][key]}
                                                        </span>
                                                ))}
                                            </td>
                                        </tr>
                                        {/* 3) 승리팀 레이블 */}
                                        <tr className="label-row">
                                            <td colSpan={13}>
                                                승리팀 ({winStats.kills}/{winStats.deaths}/{winStats.assists})
                                                {JSON.parse(winTeam[0].bans).map((ban, i) => (
                                                    <img
                                                        key={i}
                                                        className="ban-img"
                                                        src={`https://ddragon.leagueoflegends.com/cdn/15.7.1/img/champion/${ban.championId}.png`}
                                                        alt="ban"
                                                    />
                                                ))}
                                                {statsData.map(([label,key])=>(
                                                    <span key={key} className="stat-text">
                                                            {label}: {winTeam[0][key]}
                                                        </span>
                                                ))}
                                            </td>
                                        </tr>

                                        {/* 4) 승리팀 멤버 */}
                                        {winTeam.map(p=>(
                                            <tr key={p.puuid} className="blue-row">
                                                <td>
                                                    <img
                                                        src={`https://ddragon.leagueoflegends.com/cdn/15.7.1/img/champion/${p.championName}.png`}
                                                        width="24" height="24" alt={p.championName}
                                                    />
                                                </td>
                                                <td>{p.riotIdGameName}#{p.riotIdTagline}</td>
                                                <td>
                                                    {[p.summoner1Id, p.summoner2Id].map((k, i) => keyToId[k] ? (
                                                        <img key={i}
                                                             src={`https://ddragon.leagueoflegends.com/cdn/15.7.1/img/spell/${keyToId[k]}.png`}
                                                             width="20" height="20" alt="spell"/>
                                                    ) : null)}
                                                </td>
                                                <td>
                                                    {[p.primaryStyle1, p.primaryStyle2, p.primaryStyle3, p.subStyle1, p.subStyle2]
                                                        .map((u, i) => (
                                                            <img key={i}
                                                                 src={`https://ddragon.leagueoflegends.com/cdn/img/${u}`}
                                                                 width="20" height="20" alt="rune"/>
                                                        ))}
                                                </td>
                                                <td>{p.kills}/{p.deaths}/{p.assists}</td>
                                                <td>{p.teamPosition}</td>
                                                <td>
                                                    {[0, 1, 2, 3, 4, 5, 6].map(i => {
                                                        const c = p[`item${i}`];
                                                        const src = c && c !== '0'
                                                            ? `https://ddragon.leagueoflegends.com/cdn/15.7.1/img/item/${c}.png`
                                                            : `https://ddragon.leagueoflegends.com/cdn/15.7.1/img/profileicon/588.png`;
                                                        return (
                                                            <img
                                                                key={i}
                                                                src={src}
                                                                width="20"
                                                                height="20"
                                                                alt={c && c !== '0' ? `item-${c}` : 'empty-slot'}
                                                            />
                                                        );
                                                    })}
                                                </td>
                                                <td>{p.totalMinionsKilled}</td>
                                                <td>{p.totalDamageDealtToChampions}</td>
                                                <td>{p.totalDamageTaken}</td>
                                                <td>{p.totalHealsOnTeammates}</td>
                                                <td>{p.damageDealtToBuildings}</td>
                                                <td>{p.visionScore}</td>
                                            </tr>
                                        ))}
                                        </tbody>
                                    </table>
                                </div>
                            );
                        })()}
                    </React.Fragment>
                ))}
            </div>

            {!isLastPage && (
                <button id="more" onClick={fetchMatches}>더보기</button>
            )}
        </>
    );
}
