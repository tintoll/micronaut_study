pluginManagement {
    includeBuild("build-logic")
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

rootProject.name = "micronaut_study"

include("kafka_es")
include("coroutine-r2dbc")
include("indra-test")
