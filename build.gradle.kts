plugins {
    kotlin("jvm") version "2.0.0"
    `maven-publish`
}

val projectVersion: String by project
val projectGroup: String by project

group = projectGroup
version = projectVersion

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(8)
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])

            pom {
                name.set("Flare SDK")
                description.set("Flare Flamework's SDK")
            }
        }
    }
    repositories {
        mavenLocal()
    }
}

tasks.named("build") {
    finalizedBy("publishToMavenLocal")
}