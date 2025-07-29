// Footer.js
import React from 'react';
import './Footer.css'; // 스타일 별도 관리

function Footer() {
    return (
        <footer className="footer">
            <div>
                Copyright(c) hamlol.xyz |
                문의: hamlolservice@gmail.com |
            </div>
            <small>
                HAMLol은 Riot Games의 공식 서비스가 아니며,<br/>
                본 서비스는 Riot Games의 입장을 대변하지 않습니다.<br/>
                해미는 귀엽습니다.
            </small>
        </footer>
    );
}

export default Footer;
