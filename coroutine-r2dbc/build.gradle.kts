plugins {
    id("micronaut-application")
}

group = "io.study.coroutine"
version = "0.1.0"

repositories {
    mavenCentral()
}

dependencies {

    kapt("io.micronaut:micronaut-http-validation")

    implementation("io.micronaut.data:micronaut-data-r2dbc")
    runtimeOnly("org.postgresql:r2dbc-postgresql")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactive")
    runtimeOnly("ch.qos.logback:logback-classic")

    implementation("io.r2dbc:r2dbc-pool:1.0.1.RELEASE")
    implementation("io.r2dbc:r2dbc-proxy:1.1.5.RELEASE")

}

application {
    mainClass.set("io.study.coroutine.ApplicationKt")
}

micronaut {
    runtime("netty")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("io.study.*")
    }
}