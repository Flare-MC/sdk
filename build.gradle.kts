plugins {
    kotlin("jvm") version "2.0.0"
    `maven-publish`
}

val projectVersion: String by project
val projectGroup: String by project

dependencies {
    testImplementation(kotlin("test"))
    implementation(project(":platform:common"))
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

allprojects {
    apply(plugin = "kotlin")

    group = "$projectGroup.sdk"
    version = projectVersion

    repositories {
        mavenCentral()
    }

    dependencies {
        implementation(kotlin("stdlib"))
    }

    kotlin {
        jvmToolchain(8)
    }

}

group = projectGroup
version = projectVersion

tasks.named("build") {
    dependsOn(subprojects.map { it.tasks.named("build") })
    finalizedBy("publishToMavenLocal")
}