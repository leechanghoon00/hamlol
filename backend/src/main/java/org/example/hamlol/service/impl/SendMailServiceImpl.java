package org.example.hamlol.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.hamlol.service.RedisService;
import org.example.hamlol.service.SendMailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.time.Duration;
import java.util.UUID;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SendMailServiceImpl implements SendMailService {

    private final JavaMailSender mailSender;
    private final RedisService redisService;

    // 메일 보내는 사람
    @Value("${spring.mail.username}")
    private String fromEmail;

    // 메일에 포함되는 링크
    @Value("${props.reset-password-url}")
    private String resetPwUrl;

    @Override
    @Transactional
    public String sendResetPasswordEmail(String email) {
        // 해당 링크를 통해 비밀번호 재설정 유도
        String uuid = UUID.randomUUID().toString();
        log.info("📩 비밀번호 재설정 요청 - email: {}, uuid: {}", email, uuid);

        // 이메일 제목
        String title = "요청하신 비밀번호 재설정입니다.";
        // 뒤에 UUID를 붙여 재설정 링크 완성
        String link = resetPwUrl + "/" + uuid;

        // 이메일 본문
        String content = ""
                + "<h3>비밀번호 재설정 안내</h3>"
                + "<p>아래 링크를 클릭하여 비밀번호를 재설정하세요.</p>"
                + "<a href=\"" + link + "\">" + link + "</a>"
                + "<p><small>이 링크는 24시간 동안만 유효합니다.</small></p>";

        // 메일 전송
        sendEmail(email, title, content);

        // Redis 저장
        Duration ttl = Duration.ofHours(24);
        redisService.setValuesWithTimeout(uuid, email, ttl);
        log.info("🧠 Redis에 UUID 저장 완료 - UUID: {}, Email: {}", uuid, email);

        return uuid;
    }
    // 메일 실제로 전송하는 기능
    private void sendEmail(String to, String subject, String content) {
        try {
            // 이메일 메시지 객체 생성
            MimeMessage message = mailSender.createMimeMessage();
            // 쉽게 다루도록 도와줌 true(멀티파트 지원),utf-8(한글이 안깨지게)
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");

            // 보내는사람이름 ㅣ Hamlol
            // 받는사람한테 이렇게 보임 Hamlol : hamlolservice@gmail.com
            helper.setFrom(new InternetAddress(fromEmail, " Hamlol "));
            // 받는사람 이메일 주소 설정
            helper.setTo(to);
            // 메일 제목 설정
            helper.setSubject(subject);
            // 메일 본문 내용 설정 content : HTML 형식 문자열 , true: 랜더링
            helper.setText(content, true); // HTML 형식

            // SMTP 서버를 통해 메일 전송 시도
            mailSender.send(message);
            log.info("✅ 이메일 전송 완료 - 받는 사람: {}", to);
        } catch (MessagingException | UnsupportedEncodingException e) {
            log.error("❌ 이메일 전송 실패: {}", e.getMessage(), e);
        }
    }
}