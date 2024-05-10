
plugins {
    id("micronaut-application")
}

group = "io.study"
version = "0.1"

dependencies {
    // security
    kapt("io.micronaut.security:micronaut-security-annotations")
    implementation("io.micronaut.security:micronaut-security-jwt")
    implementation("io.micronaut:micronaut-http-client")
    implementation("io.micronaut.reactor:micronaut-reactor")

    // r2dbc
    runtimeOnly("org.postgresql:r2dbc-postgresql")
    runtimeOnly("org.postgresql:postgresql")

    testImplementation("io.micronaut:micronaut-http-client")
}

application {
    mainClass.set("io.study.management.UserManagerApplicationKt")
}

micronaut {
    runtime("netty")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("io.study.*")
    }
}
