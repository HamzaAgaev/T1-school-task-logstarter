import org.gradle.api.publish.maven.MavenPublication

plugins {
    id("java")
    id("maven-publish")
}

group = "t1.school"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter:3.4.5")
    implementation("org.springframework.boot:spring-boot-starter-aop:3.4.5")
    implementation("org.aspectj:aspectjrt:1.9.24")
    implementation("org.slf4j:slf4j-api:2.0.17")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor:3.4.5")
}
