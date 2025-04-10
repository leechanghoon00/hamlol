plugins {
    java
    id("org.springframework.boot") version "3.4.3"
    id("io.spring.dependency-management") version "1.1.7"
    //queryDSL
    id("com.ewerk.gradle.plugins.querydsl") version "1.0.10"
}

group = "org.example"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // Spring Boot 스타터
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")           // 웹 기능 (DispatcherServlet, Jackson 등)
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")     // JPA
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")    // Thymeleaf
    implementation("org.springframework.boot:spring-boot-starter-security")     // Spring Security

    // Thymeleaf + Spring Security 연동
    implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity6")

    // JWT (JSON Web Token)
    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")

    // Swagger / OpenAPI 문서화
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")

    // DB (H2, PostgreSQL)
    runtimeOnly("com.h2database:h2")
    runtimeOnly("org.postgresql:postgresql")

    // Lombok
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    // 테스트
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // SLF4J
    implementation("org.springframework:spring-context")

    // QueryDSL
    implementation ("com.querydsl:querydsl-jpa:5.0.0:jakarta")
    annotationProcessor ("com.querydsl:querydsl-apt:5.0.0:jakarta")
    annotationProcessor ("jakarta.annotation:jakarta.annotation-api")
    annotationProcessor ("jakarta.persistence:jakarta.persistence-api")


}


tasks.withType<Test> {
    useJUnitPlatform()
}


