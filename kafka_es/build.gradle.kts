plugins {
    id("micronaut-application")
}

group = "io.study"
version = "0.1"

dependencies {
    // kafka
    implementation("io.micronaut.kafka:micronaut-kafka")

    // elasticsearch
    implementation("io.micronaut.elasticsearch:micronaut-elasticsearch")
    runtimeOnly("org.slf4j:log4j-over-slf4j:1.7.30")
}

application {
    mainClass.set("io.study.kafka.ApplicationKt")
}

micronaut {
    runtime("netty")
    testRuntime("kotest5")
    processing {
        incremental(true)
        annotations("nethru.signal.data.*")
    }
}
