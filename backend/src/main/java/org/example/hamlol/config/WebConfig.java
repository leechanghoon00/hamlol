package org.example.hamlol.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// 업로드된 이미지 파일을 브라우저에서 접근 가능하게 설정해줌
@Configuration // Spring 설정 클래스입을 알려줌
public class WebConfig implements WebMvcConfigurer { // WebMvcConfigurer는 MVC설정을 커스터마이징 할수있게 해주는 인터페이스

    @Override
    public void  addResourceHandlers(ResourceHandlerRegistry registry){
        // registry: 정적 리소스 핸들러 경로(URL ↔ 실제 파일 위치)를 등록하는 객체
        // addResourceHandler(): 클라이언트가 접근할 URL 경로 패턴 지정
        // addResourceLocations(): 해당 URL 요청에 대해 실제로 접근할 서버 내부의 파일 경로 지정

        registry.addResourceHandler("/images/profile/**") // /images/profile/abc.jpg 요청이 들어오면
                .addResourceLocations("file:uploads/profile/"); //uploads/profile/abc.jpg 파일을 응답함

    }
}
