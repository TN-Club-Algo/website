import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("java")
    kotlin("jvm") version "1.8.20-RC"
    `maven-publish`
}

group "org.algotn"
version "1.0-SNAPSHOT"

publishing {
    publications {
        create<MavenPublication>("website") {
            from(components["java"])
        }
    }

    repositories {
        maven {
            url = uri("https://maven.pkg.jetbrains.space/algo-tn/p/main/maven")
            credentials {
                username = extra["spaceUsername"].toString()
                password = extra["spacePassword"].toString()
            }
        }
    }
}

repositories {
    mavenCentral()
    maven {
        url = uri("https://maven.pkg.jetbrains.space/algo-tn/p/main/maven")
        credentials {
            username = extra["spaceUsername"].toString()
            password = extra["spacePassword"].toString()
        }
    }
}

dependencies {
    implementation("org.algotn:api:1.0-SNAPSHOT")
    implementation("org.springframework.boot:spring-boot-starter-actuator:3.0.4")

    testImplementation ("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly ("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    implementation ("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("org.springframework.boot:spring-boot-starter-web:3.0.4")
    implementation ("org.springframework.boot:spring-boot-starter-freemarker:3.0.4")
    implementation ("com.google.code.gson:gson:2.10.1")
    testImplementation ("org.springframework.boot:spring-boot-starter-test:3.0.4")

    implementation("org.springframework.boot:spring-boot-devtools:3.0.4")

    implementation("org.springframework.boot:spring-boot-starter-security:3.0.4")
    testImplementation("org.springframework.security:spring-security-test:6.0.2")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "17"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "17"
}