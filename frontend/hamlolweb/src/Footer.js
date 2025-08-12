// Footer.js
import React from 'react';
import './Footer.css'; // 스타일 별도 관리

function Footer() {
    return (
        <footer className="footer">
            <div>
                Copyright(c) hamlol.xyz |
                문의: hamlolservice@gmail.com |
                <a 
                    href="https://doc-hosting.flycricket.io/hamlol-privacy-policy/f29fc036-5b97-47bf-9091-55f6da275376/privacy" 
                    target="_blank" 
                    rel="noopener noreferrer"
                >
                    개인정보 보호정책
                </a> |
                <a 
                    href="https://doc-hosting.flycricket.io/hamlol-terms-of-use/ef56177b-5302-4635-bedd-62841f65b716/terms" 
                    target="_blank" 
                    rel="noopener noreferrer"
                >
                    서비스이용약관
                </a>
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
