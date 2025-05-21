import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

plugins {
    kotlin("jvm") version PluginVersions.KOTLIN
    kotlin("plugin.spring") version PluginVersions.KOTLIN
    kotlin("plugin.jpa") version PluginVersions.KOTLIN
    kotlin("plugin.allopen") version PluginVersions.KOTLIN
    id("org.springframework.boot") version PluginVersions.SPRING_BOOT
    id("io.spring.dependency-management") version PluginVersions.SPRING_DEPENDENCY_MANAGEMENT

    id("com.google.cloud.tools.jib") version PluginVersions.JIB
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(JavaVersion.VERSION_21.majorVersion)
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

extra["springCloudVersion"] = "2024.0.1"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions:${DependencyVersions.REACTOR_KOTLIN_EXTENSIONS}")
    implementation("org.jetbrains.kotlin:kotlin-reflect:${DependencyVersions.KOTLIN}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:${DependencyVersions.KOTLINX_COROUTINES_REACTOR}")
    implementation("org.springframework.cloud:spring-cloud-starter-vault-config")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5:${DependencyVersions.KOTLIN}")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:${DependencyVersions.KOTLINX_COROUTINES_REACTOR}")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<KotlinJvmCompile>().configureEach(kotlinCompilerOptions())

fun kotlinCompilerOptions(): KotlinJvmCompile.() -> Unit = {
    compilerOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = JvmTarget.JVM_21
    }
}

jib {
    from {
        image = "openjdk:21-jdk"
    }
    to {
        image = "${project.group}/${project.name}"
        tags = setOf("${project.version}", "latest")
    }
    container {
        jvmFlags = listOf("-Xms256m", "-Xmx512m")
        ports = listOf("8080")
        environment = mapOf("SPRING_PROFILES_ACTIVE" to "dev")
    }
}
