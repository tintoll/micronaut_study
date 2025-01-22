plugins {
    kotlin("jvm") version "2.0.21"
}

group = "org.example"
version = "unspecified"

repositories {
    mavenCentral()
    maven {
        url = uri("https://jitpack.io")
    }
    maven {
        url = uri("https://maven.pkg.github.com/at-signal/indra")
        credentials {
            username = System.getenv("GITHUB_USERNAME")
            password = System.getenv("GITHUB_TOKEN")
        }
    }

}

dependencies {

    // indra
    implementation("nethru.indra:indexer:0.1.2")
    implementation("nethru.indra:shard-registry:0.1.3")
    implementation("nethru.indra:schema-registry:0.1.3")
    implementation("nethru.indra:querier:0.1.4")



    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}