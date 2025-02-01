
repositories {
    mavenCentral()
    maven {
        name = "papermc"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
}

dependencies {
    compileOnly("com.velocitypowered:velocity-api:3.4.0-SNAPSHOT")
    annotationProcessor("com.velocitypowered:velocity-api:3.4.0-SNAPSHOT")

    api("org.bspfsystems:yamlconfiguration:3.0.2")

    api(project(":platform:common"))
}

kotlin {
    jvmToolchain(17)
}