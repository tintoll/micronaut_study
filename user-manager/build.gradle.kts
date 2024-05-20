
plugins {
    id("micronaut-application")
}

group = "io.study"
version = "0.1"

dependencies {
    // security
//    kapt("io.micronaut.security:micronaut-security-annotations")
//    implementation("io.micronaut.security:micronaut-security-jwt")
//    implementation("io.micronaut:micronaut-http-client")
//    implementation("io.micronaut.reactor:micronaut-reactor")

    // r2dbc
    kapt("io.micronaut:micronaut-http-validation")
    kapt("io.micronaut.data:micronaut-data-processor")
    implementation("io.micronaut.data:micronaut-data-r2dbc")
    implementation("io.micronaut.sql:micronaut-jdbc-hikari")
    compileOnly("io.micronaut:micronaut-http-client")
    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")

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
