plugins {
    `kotlin-dsl`
}


repositories {
    gradlePluginPortal()
}

dependencies {
    implementation(libs.kotlin.gradle.plugin)
    implementation(libs.kotlin.allopen.plugin)
    implementation(libs.micronaut.gradle.plugin)
    implementation(libs.ksp.gradle.plugin)
    implementation(libs.shadow.gradle.plugin)
}
