plugins {
    java
    id("org.springframework.boot") version "3.1.5"
    id("io.spring.dependency-management") version "1.1.3"
}

group = "dev.enginecode.inhouse"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web:3.1.5")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf:3.1.5")
    testImplementation("org.springframework.boot:spring-boot-starter-test:3.1.5")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
