// backend/build.gradle.kts

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.10"
    java
    id("org.springframework.boot") version "3.4.3"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "org.example.hamlol"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17
java.targetCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

val queryDslVersion = "5.0.0"

dependencies {
    // 1) QueryDSL JPA (runtime + compile)
    implementation("com.querydsl:querydsl-jpa:$queryDslVersion:jakarta")

    // 2) Jakarta Annotation API (annotation processing 시에만)
    compileOnly("jakarta.annotation:jakarta.annotation-api:2.1.1")
    annotationProcessor("jakarta.annotation:jakarta.annotation-api:2.1.1")

    // 3) Jakarta Persistence API
    //    – 컴파일/런타임 시에 필요
    implementation("jakarta.persistence:jakarta.persistence-api:3.1.0")
    //    – 메타모델 생성 시에도 필요하므로 annotationProcessor에도 추가
    annotationProcessor("jakarta.persistence:jakarta.persistence-api:3.1.0")

    // 4) QueryDSL APT (코드 생성)
    annotationProcessor("com.querydsl:querydsl-apt:$queryDslVersion:jakarta")

    // --- 나머지 Spring Boot / Lombok / 테스트 등 ---
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity6")

    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")

    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")
    runtimeOnly("com.h2database:h2")
    runtimeOnly("org.postgresql:postgresql")
    runtimeOnly("com.mysql:mysql-connector-j")
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    developmentOnly("org.springframework.boot:spring-boot-devtools")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

springBoot {
    mainClass.set("org.example.hamlol.HamlolApplication")
}

// Q-타입 생성 위치를 Gradle 표준 annotationProcessor 경로로 지정
sourceSets {
    main {
        java.srcDir("$buildDir/generated/sources/annotationProcessor/java/main")
    }
}

// annotationProcessor 결과물을 컴파일에 포함
tasks.named<JavaCompile>("compileJava") {
    options.annotationProcessorGeneratedSourcesDirectory =
        file("$buildDir/generated/sources/annotationProcessor/java/main")
}
