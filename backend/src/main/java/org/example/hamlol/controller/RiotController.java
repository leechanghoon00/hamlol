package org.example.hamlol.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@RestController
public class RiotController {

    @GetMapping(value = "/riot.txt", produces = "text/plain; charset=UTF-8")
    public ResponseEntity<String> getRiotFile() {
        try {
            Resource resource = new ClassPathResource("static/riot.txt");

            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }

            String content;
            try (InputStream is = resource.getInputStream();
                 BufferedReader reader = new BufferedReader(
                         new InputStreamReader(is, StandardCharsets.UTF_8))) {

                content = reader.readLine(); // 첫 줄만 읽기
            }

            if (content == null) {
                return ResponseEntity.internalServerError().build();
            }

            content = content.trim(); // 앞뒤 공백 제거 (끝줄 공백 포함)

            return ResponseEntity.ok()
                    .contentType(MediaType.TEXT_PLAIN)
                    .body(content);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}