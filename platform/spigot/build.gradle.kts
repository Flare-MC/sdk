repositories {
    mavenCentral()
    maven {
        name = "spigot"
        url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    }
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.21.4-R0.1-SNAPSHOT")

    api(project(":platform:common"))
    api("net.kyori:adventure-platform-bukkit:4.3.4")
}
