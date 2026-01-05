plugins {
    id("org.springframework.boot") version "3.5.7"
    id("io.spring.dependency-management") version "1.1.7"
    java
}

group = "org.siri_hate"
version = "1.0.0-SNAPSHOT"
description = "UserService"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

repositories {
    mavenCentral()
}

val awsSdkVersion = "2.37.3"
val jjwtVersion = "0.11.5"
val mapstructVersion = "1.5.5.Final"
val modelMapperVersion = "3.2.0"
val gsonVersion = "2.10.1"
val logstashEncoderVersion = "8.1"
val logbackVersion = "1.5.20"

dependencies {

    // --- AWS ---
    implementation("software.amazon.awssdk:s3:$awsSdkVersion")

    // --- Spring Boot ---
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.kafka:spring-kafka")

    // --- Metrics ---
    implementation("io.micrometer:micrometer-registry-prometheus")

    // --- Database ---
    runtimeOnly("org.postgresql:postgresql")

    // --- JWT ---
    implementation("io.jsonwebtoken:jjwt-api:$jjwtVersion")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:$jjwtVersion")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:$jjwtVersion")

    // --- Mapping ---
    implementation("org.modelmapper:modelmapper:$modelMapperVersion")
    implementation("org.mapstruct:mapstruct:$mapstructVersion")
    annotationProcessor("org.mapstruct:mapstruct-processor:$mapstructVersion")

    // --- JSON ---
    implementation("com.google.code.gson:gson:$gsonVersion")

    // --- Logging ---
    implementation("net.logstash.logback:logstash-logback-encoder:$logstashEncoderVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("ch.qos.logback:logback-access:$logbackVersion")
    implementation("ch.qos.logback:logback-core:$logbackVersion")

    // --- Tests ---
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
}

tasks.withType<Test> {
    useJUnitPlatform()
}