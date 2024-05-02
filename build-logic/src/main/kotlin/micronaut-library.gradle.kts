import gradle.kotlin.dsl.accessors._fa393d92bf243f86e14930a7cb20dbb9.implementation
import gradle.kotlin.dsl.accessors._fa393d92bf243f86e14930a7cb20dbb9.kapt
import gradle.kotlin.dsl.accessors._fa393d92bf243f86e14930a7cb20dbb9.runtimeOnly

plugins {
    id("micronaut-base")
    id("io.micronaut.library")
}

dependencies {
    // micronaut
    kapt("io.micronaut.serde:micronaut-serde-processor")
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
    implementation("io.micronaut.kotlin:micronaut-kotlin-extension-functions")
    implementation("io.micronaut.serde:micronaut-serde-jackson")
    runtimeOnly("ch.qos.logback:logback-classic")
    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")
}