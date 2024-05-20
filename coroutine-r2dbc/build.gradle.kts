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
    runtimeOnly("ch.qos.logback:logback-classic")

}

application {
    mainClass.set("io.study.io.study.coroutine.ApplicationKt")
}

micronaut {
    runtime("netty")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("io.study.*")
    }
}