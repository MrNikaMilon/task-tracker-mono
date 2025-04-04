plugins {
	java
	id("org.springframework.boot") version "3.4.4"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.nion"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
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
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-hateoas")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.liquibase:liquibase-core:4.29.2")
	implementation("org.mapstruct:mapstruct:1.6.3")
	testImplementation("org.testcontainers:junit-jupiter")
	testImplementation("org.springframework.boot:spring-boot-testcontainers")
	testImplementation("org.testcontainers:postgresql")
	compileOnly("org.projectlombok:lombok:1.18.38") // don't include in building
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	runtimeOnly("org.postgresql:postgresql")
	runtimeOnly("org.springframework.boot:spring-boot-docker-compose")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	annotationProcessor ("org.projectlombok:lombok:1.18.38") // firstly compile lombok
	annotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final") // mapstruct compile later
}

tasks.withType<Test> {
	useJUnitPlatform()
}
