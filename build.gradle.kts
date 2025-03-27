import com.github.gradle.node.npm.task.NpmTask

val appVersion: String by project
val liquibaseVersion: String by project
val jwtVersion: String by project

plugins {
	kotlin("jvm")
	kotlin("plugin.spring")
	kotlin("plugin.jpa")
	id("org.springframework.boot") version "3.4.2"
	id("io.spring.dependency-management") version "1.1.7"
	id("com.github.node-gradle.node") version "7.1.0"
}

group = "ru.speedrun"
version = appVersion

node {
    download = true
    nodeProjectDir = file("${project.projectDir}/frontend")
}

tasks.register<NpmTask>("buildFrontend") {
	dependsOn("npmInstall")
    this.args.addAll("run", "build")
}

tasks.processResources {
    dependsOn("buildFrontend")

    from("${project.projectDir}/frontend/dist") { into("public") }
}

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-security")

	implementation("org.postgresql:postgresql")
    implementation("org.liquibase:liquibase-core:$liquibaseVersion")

	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

	implementation("io.jsonwebtoken:jjwt-api:$jwtVersion")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:$jwtVersion")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:$jwtVersion")

	compileOnly("jakarta.servlet:jakarta.servlet-api:6.1.0")

	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.5")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testImplementation("org.mockito.kotlin:mockito-kotlin:5.2.1")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
