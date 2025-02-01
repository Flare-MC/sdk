subprojects {
    apply(plugin = "maven-publish")

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

    tasks.named("build") {
        finalizedBy("publishToMavenLocal")
    }

    dependencies {
        if (!name.contains("common")) {
            api(project(":"))
        }

        if (!name.contains("velocity")) {
            api("net.kyori:adventure-api:4.18.0")
        }
    }

}