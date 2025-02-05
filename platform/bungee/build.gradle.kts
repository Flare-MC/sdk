
repositories {
    mavenCentral()
    maven {
        name = "sonatype"
        url = uri("https://oss.sonatype.org/content/repositories/snapshots")
    }
}

dependencies {
    compileOnly("net.md-5:bungeecord-api:1.19-R0.1-SNAPSHOT")

    api(project(":platform:common"))
    api("net.kyori:adventure-platform-bungeecord:4.3.4")
}
