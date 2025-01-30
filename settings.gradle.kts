plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "sdk"

include(":platform:spigot", ":platform:bungee", ":platform:velocity", ":platform:common")