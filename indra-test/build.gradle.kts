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
    implementation(platform("nethru.indra:indra-bom:0.1.5-SNAPSHOT"))
    implementation("nethru.indra:indexer")
    implementation("nethru.indra:shard-registry")
    implementation("nethru.indra:schema-registry")
    implementation("nethru.indra:querier")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.1")

    implementation("org.antlr:ST4:4.3")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}