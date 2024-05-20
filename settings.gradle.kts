pluginManagement {
    includeBuild("build-logic")
}

rootProject.name = "micronaut_study"

include("kafka_es")
include("user-manager")
include("coroutine-r2dbc")
