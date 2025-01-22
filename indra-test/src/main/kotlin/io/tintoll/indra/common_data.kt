package io.tintoll.indra

import nethru.indra.shard.config.Config
import nethru.indra.shard.config.MetadataStoreConfig
import nethru.indra.shard.config.StorageConfig

val rootPath = "./s3"
val tenantId = "blue_project1"
val registryConfig = Config(
    metadataCachePath = "$rootPath/registry",
    shardPath = "$rootPath/shards",
    metadataStoreConfig = MetadataStoreConfig(listOf("localhost:9091, localhost:9092, localhost:9093")),
    deepStorageConfig = StorageConfig(
        endPointUrl = System.getenv("STORAGE_ENDPOINTS"),
        regionName = System.getenv("STORAGE_REGION"),
        accessKey = System.getenv("STORAGE_ACCESS_KEY"),
        secretKey = System.getenv("STORAGE_ACCESS_KEY"),
        bucketName = System.getenv("STORAGE_BUCKET"),
    )
)

val tableId = "events"
val tableToml = """
        [-]
        id = "events"
        sort = [ "@time"]

        ["@event"]
        type = "token"
        size = 1000
        
        ["@userid"]
        type = "token"
        size = 1000
        scan = false

        ["@projectid"]
        type = "i64"

        ["@deviceid"]
        type = "token"
        size = 1000

        ["@time"]
        type = "i64"

        ["@city"]
        type = "token"
        size = 1000

        ["@region"]
        type = "token"
        size = 1000

        ["@country"]
        type = "token"
        size = 1000

        ["@ip"]
        type = "token"
        size = 1000

        ["@eid"]
        type = "i64"
        
        ["@insertid"]
        type = "token"
        size = 1000
       
        ["@maxidle"]
        type = "i64"
        
    """.trimIndent()
val metaToml = """
        [-]
        id = "#events"

        [period]
        type = "date-time-range"
    """.trimIndent()

fun eventLogs1() = listOf(
    mapOf(
        "@projectid" to 1,
        "@event" to "page_view",
        "@insertid" to "INSERTID-01",
        "@time" to 1726036257000,
        "@deviceid" to "D1",
        "@ip" to "183.100.38.217",
        "@city" to "Seongnam-si (Buljeong-ro)",
        "@region" to "Gyeonggi-do",
        "@country" to "South Korea",
        "@eid" to 1
    ),
    mapOf(
        "@projectid" to 1,
        "@event" to "page_view",
        "@deviceid" to "D1",
        "@time" to 1726034461000,
        "@insertid" to "INSERTID-02",
        "@userid" to "U1",
        "@eid" to 1
    ),
    mapOf(
        "@projectid" to 1,
        "@event" to "page_view",
        "@deviceid" to "D1",
        "@time" to 1726034465000,
        "@insertid" to "INSERTID-03",
        "@userid" to "U1",
        "@eid" to 1
    ),
    mapOf(
        "@projectid" to 1,
        "@event" to "page_view",
        "@deviceid" to "D1",
        "@time" to 1726034468000,
        "@insertid" to "INSERTID-04",
        "@userid" to "U1",
        "@eid" to 1
    ),
    mapOf(
        "@insertid" to "INSERTID-05",
        "@event" to "page_view",
        "@projectid" to 1,
        "@deviceid" to "D1",
        "@eid" to 1,
        "@time" to 1726034470000,
        "@userid" to "",
    ),
    mapOf(
        "@insertid" to "INSERTID-06",
        "@event" to "page_view",
        "@projectid" to 1,
        "@deviceid" to "D1",
        "@eid" to 1,
        "@time" to 1726034478000,
        "@userid" to "",
    ),
    mapOf(
        "@insertid" to "INSERTID-07",
        "@event" to "page_view",
        "@projectid" to 1,
        "@deviceid" to "D1",
        "@eid" to 1,
        "@time" to 1726036200000,
        "@userid" to "",
    ),
    mapOf(
        "@insertid" to "INSERTID-08",
        "@event" to "page_view",
        "@projectid" to 1,
        "@deviceid" to "D1",
        "@eid" to 1,
        "@time" to 1726036205000,
        "@userid" to "",
    ),
    mapOf(
        "@insertid" to "INSERTID-09",
        "@event" to "page_view",
        "@projectid" to 1,
        "@deviceid" to "D1",
        "@eid" to 1,
        "@time" to 1726036206000,
        "@userid" to "",
    ),
)
fun eventLogs2() = listOf(
    // user stitching event
    mapOf(
        "@event" to "@UserStitchingEvent",
        "@projectid" to 1,
        "@deviceid" to "D1",
        "@eid" to 1,
        "@baseeventtime" to 1726036210000,
        "@copyeid" to 2,
    ),

    mapOf(
        "@projectid" to 1,
        "@event" to "page_view",
        "@deviceid" to "D1",
        "@time" to 1726036210000,
        "@insertid" to "INSERTID-10",
        "@userid" to "U2",
        "@eid" to 2
    ),
    mapOf(
        "@insertid" to "INSERTID-11",
        "@event" to "page_view",
        "@projectid" to 1,
        "@deviceid" to "D1",
        "@eid" to 2,
        "@time" to 1726036213000,
        "@userid" to "",
    ),
)
