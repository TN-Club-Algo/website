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
    //mavenLocal()
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
    implementation("org.algotn:api:1.0.9-SNAPSHOT")
    implementation("org.springframework.boot:spring-boot-starter-actuator:3.0.4")
    implementation("org.springframework:spring-messaging:6.0.9")

    implementation("org.redisson:redisson:3.21.3") // fix missing cross dependency

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
    testImplementation("org.springframework.security:spring-security-test:6.1.0")

    testImplementation("org.testcontainers:testcontainers:1.17.6")
    testImplementation("org.testcontainers:junit-jupiter:1.17.6")
    testImplementation("com.redis.testcontainers:testcontainers-redis-junit-jupiter:1.4.6")
}

/*tasks.getByName<Test>("test") {
    useJUnitPlatform()
}*/

tasks {
    val fatJar = register<Jar>("fatJar") {
        dependsOn.addAll(
            listOf(
                "compileJava",
                "compileKotlin",
                "processResources"
            )
        ) // We need this for Gradle optimization to work
        archiveClassifier.set("") // Naming the jar
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        manifest {
            attributes["Main-Class"] = "org.algotn.website.WebApplicationKt"
        }
        val sourcesMain = sourceSets.main.get()
        val contents = configurations.runtimeClasspath.get()
            .map { if (it.isDirectory) it else zipTree(it) } +
                sourcesMain.output
        from(contents)
    }
    build {
        dependsOn(fatJar) // Trigger fat jar creation during build
    }
}