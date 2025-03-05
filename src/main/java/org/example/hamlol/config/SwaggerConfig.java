package org.example.hamlol.config;

import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi riotAccountGroupedOpenApi() {
        return GroupedOpenApi
                .builder()
                .group("riot-account")
                .pathsToMatch("/riot/account/v1/accounts/**")
                .addOpenApiCustomizer(
                        openApi ->
                                openApi
                                        .setInfo(
                                                new Info()
                                                        .title("Riot Account API") // API 제목
                                                        .description("Riot Games 계정을 조회하기 위한 API") // API 설명
                                                        .version("1.0.0") // API 버전
                                        )
                )
                .build();
    }


}