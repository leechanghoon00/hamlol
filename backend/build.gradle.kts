import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    id("org.springframework.boot") version "3.4.3"
    id("io.spring.dependency-management") version "1.1.7"
    id("com.ewerk.gradle.plugins.querydsl") version "1.0.10"
}

java.sourceCompatibility = JavaVersion.VERSION_17
java.targetCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

val queryDslVersion = "5.0.0"

dependencies {
    // Spring Boot 스타터
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-security")

    // Thymeleaf + Spring Security 연동
    implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity6")

    // JWT (JSON Web Token)
    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")

    // Swagger / OpenAPI 문서화
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")

    // DB (H2, PostgreSQL, MySQL)
    runtimeOnly("com.h2database:h2")
    runtimeOnly("org.postgresql:postgresql")
    runtimeOnly("com.mysql:mysql-connector-j")

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
    implementation("com.querydsl:querydsl-jpa:$queryDslVersion:jakarta")
    annotationProcessor("com.querydsl:querydsl-apt:$queryDslVersion:jakarta")
    querydsl("com.querydsl:querydsl-apt:$queryDslVersion:jakarta")

    // Jakarta Persistence API
    implementation("jakarta.persistence:jakarta.persistence-api:3.1.0")
    annotationProcessor("jakarta.persistence:jakarta.persistence-api:3.1.0")

    // 개발 도구
    developmentOnly("org.springframework.boot:spring-boot-devtools")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<JavaCompile> {
    options.compilerArgs.removeAll { it == "--enable-preview" }
}

sourceSets {
    main {
        java {
            srcDir("$buildDir/generated/querydsl")
        }
    }
}

querydsl {
    jpa = true
    querydslSourcesDir = "$buildDir/generated/querydsl"
}

configurations {
    compileOnly {
        extendsFrom(annotationProcessor.get())
    }
}