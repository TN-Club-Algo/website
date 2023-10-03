plugins {
    id("java")
    kotlin("jvm") version "1.8.21"
    `maven-publish`
    id("org.springframework.boot") version "2.5.5"
}

buildscript {
    dependencies {
        classpath("org.springframework.boot:spring-boot-gradle-plugin:1.2.0.M2")
    }
}

group "org.algotn"
version "1.0-SNAPSHOT"

publishing {
    publications {
        create<MavenPublication>("website") {
            from(components["java"])
        }
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator:3.0.4")
    implementation("org.springframework:spring-messaging:6.0.9")

    implementation("org.redisson:redisson:3.21.3") // fix missing cross dependency

    //implementation ("org.webjars:webjars-locator-core")
    implementation ("org.webjars:sockjs-client:1.5.1")
    implementation ("org.webjars:stomp-websocket:2.3.4")
    implementation ("org.webjars:bootstrap:5.2.3")
    implementation ("org.webjars:jquery:3.6.4")
    implementation("org.springframework:spring-websocket:6.0.9")
    implementation("org.apache.commons:commons-lang3:3.12.0")
    implementation("org.bspfsystems:yamlconfiguration:1.3.3")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("org.springframework.boot:spring-boot-starter-web:3.0.4")
    implementation("org.springframework.boot:spring-boot-starter-freemarker:3.0.4")
    implementation("com.google.code.gson:gson:2.10.1")
    testImplementation("org.springframework.boot:spring-boot-starter-test:3.0.4")

    //implementation("org.springframework.boot:spring-boot-devtools:3.0.4")

    implementation("org.springframework.boot:spring-boot-starter-mail:3.0.4")

    implementation("org.springframework.boot:spring-boot-starter-security:3.1.0")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client:3.1.0")
    testImplementation("org.springframework.security:spring-security-test:6.1.0")

    testImplementation("org.testcontainers:testcontainers:1.17.6")
    testImplementation("org.testcontainers:junit-jupiter:1.17.6")
    testImplementation("com.redis.testcontainers:testcontainers-redis-junit-jupiter:1.4.6")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.0-RC")
}

/*tasks.getByName<Test>("test") {
    useJUnitPlatform()
}*/