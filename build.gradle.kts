import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "4.1.1"
	id("io.spring.dependency-management") version "1.1.7"
//	id("org.graalvm.buildtools.native") version "1.1.13"
	id("com.gorylenko.gradle-git-properties") version "4.0.1"
	id("com.google.cloud.tools.jib") version "3.5.4"

	kotlin("jvm") version "2.4.20"
	kotlin("plugin.spring") version "2.4.20"
	kotlin("plugin.jpa") version "2.4.20"

	idea
}

group = "info.ognibeni.club.score"
version = "0.0.1-SNAPSHOT"

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

jib.to.image = "ognibeni/score-service"

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
		mavenBom("org.testcontainers:testcontainers-bom:2.0.5")
	}
}

dependencies {
	// Spring Boot
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-webmvc")
	implementation("org.springframework.boot:spring-boot-starter-flyway")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

	// Kotlin
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

	// Database
	runtimeOnly("org.postgresql:postgresql")
	implementation("org.flywaydb:flyway-database-postgresql")

	// API documentation
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:3.1.1")

	// Unit testing
	testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")
	testImplementation("org.junit.jupiter:junit-jupiter-api")
	testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
	testImplementation("io.mockk:mockk:1.14.11")
	testImplementation("com.ninja-squad:springmockk:5.0.1")

	// Integration testing
	itImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")
	itImplementation("org.springframework.boot:spring-boot-starter-restclient-test")
	itImplementation("org.testcontainers:testcontainers-junit-jupiter")
	itImplementation("org.testcontainers:testcontainers-postgresql")
}

tasks.withType<KotlinCompile> {
	compilerOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = JvmTarget.JVM_25
	}
}

plugins.withType<JavaPlugin> {
	extensions.configure<JavaPluginExtension> {
		sourceCompatibility = JavaVersion.VERSION_25
		targetCompatibility = JavaVersion.VERSION_25
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
	testLogging { events("passed") }
}

val integrationTest = task<Test>("integrationTest") {
	description = "Runs integration tests."
	group = "verification"

	// set the configuration profile for the integration tests, enabling application-integrationTest.yml
	systemProperty("spring.profiles.active", "integrationTest")

	testClassesDirs = sourceSets["it"].output.classesDirs
	classpath = sourceSets["it"].runtimeClasspath

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
