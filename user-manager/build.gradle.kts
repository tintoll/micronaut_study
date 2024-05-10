plugins {
    id("micronaut-application")
}

group = "io.study"
version = "0.1"

dependencies {
    // security
    annotationProcessor("io.micronaut.security:micronaut-security-annotations")
    implementation("io.micronaut.security:micronaut-security-jwt")

    // r2dbc
    runtimeOnly("org.postgresql:r2dbc-postgresql")
    runtimeOnly("org.postgresql:postgresql")
}

application {
    mainClass.set("io.study.management.UserManagerApplicationKt")
}

micronaut {
    runtime("netty")
    testRuntime("kotest5")
    processing {
        incremental(true)
        annotations("io.study.*")
    }
}
