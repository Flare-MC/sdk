import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

buildscript {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
    dependencies {
        classpath("com.github.johnrengelman:shadow:8.1.1")
    }
}

subprojects {
    apply(plugin = "maven-publish")
    apply(plugin = "com.github.johnrengelman.shadow")

    extensions.configure<PublishingExtension>("publishing") {
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

    if (!name.contains("common")) {
        apply(plugin = "com.github.johnrengelman.shadow")

        tasks.withType<ShadowJar> {
            archiveClassifier.set("")
        }

        tasks.named("build") {
            dependsOn("shadowJar")
            finalizedBy("publishToMavenLocal")
        }

        dependencies {
            implementation(project(":"))
        }
    }
}