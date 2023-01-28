import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.0.2"
	id("io.spring.dependency-management") version "1.1.0"
	id("org.graalvm.buildtools.native") version "0.9.18"
	id("com.gorylenko.gradle-git-properties") version "2.4.1"

	kotlin("jvm") version "1.7.22"
	kotlin("plugin.spring") version "1.7.22"
	kotlin("plugin.jpa") version "1.7.22"

	idea
}

group = "info.ognibeni.club.score"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

// See https://docs.gradle.org/current/userguide/java_testing.html#sec:configuring_java_integration_tests
sourceSets {
	create("it") {
		compileClasspath += sourceSets.main.get().output
		runtimeClasspath += sourceSets.main.get().output
	}
}

val itImplementation: Configuration by configurations.getting {
	extendsFrom(configurations.implementation.get())
}

configurations["itRuntimeOnly"].extendsFrom(configurations.runtimeOnly.get())

dependencyManagement {
	imports {
		mavenBom("org.testcontainers:testcontainers-bom:1.17.6")
	}
}

dependencies {
	// Spring Boot
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

	// Kotlin
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

	// Database
	runtimeOnly("org.postgresql:postgresql")
	implementation("org.flywaydb:flyway-core")

	// Unit testing
	testImplementation("org.springframework.boot:spring-boot-starter-test")

	// Integration testing
	itImplementation("org.springframework.boot:spring-boot-starter-test")
	itImplementation("org.testcontainers:junit-jupiter")
	itImplementation("org.testcontainers:postgresql")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

val integrationTest = task<Test>("integrationTest") {
	description = "Runs integration tests."
	group = "verification"

	// set the configuration profile for the integration tests, enabling application-integrationTest.yml
	systemProperty("spring.profiles.active", "integrationTest")

	testClassesDirs = sourceSets["it"].output.classesDirs
	classpath = sourceSets["it"].runtimeClasspath

	testLogging {
		events("passed")
	}

	shouldRunAfter("test")
}

tasks.check {
	dependsOn(integrationTest)
}

// build information to be shown in actuator's info endpoint
springBoot {
	buildInfo()
}

idea {
	module {
		testSources.from(sourceSets["it"].kotlin.srcDirs)
	}
}
