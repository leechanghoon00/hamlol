plugins {
    java
    id("org.springframework.boot") version "3.4.3"
    id("io.spring.dependency-management") version "1.1.7"
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

    implementation("org.springframework.boot:spring-boot-starter")
    compileOnly("org.projectlombok:lombok")
    runtimeOnly("com.h2database:h2")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    runtimeOnly ("org.postgresql:postgresql")
    implementation ("org.postgresql:postgresql:42.7.3" )
    implementation ("org.springframework.boot:spring-boot-starter-web")
    //스웨거 의존성
//    implementation ("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")
    implementation ("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")

}

tasks.withType<Test> {
    useJUnitPlatform()
}
