package io.tintoll.indra

import nethru.indra.query.SelectorBuilder
import nethru.indra.schema.registry.SchemaRegistry
import nethru.indra.shard.ShardRegistry
import java.time.Instant
import kotlin.system.exitProcess


fun main() {
    val shardRegistry = ShardRegistry.open(tenantId, registryConfig)

//    shardRegistry.sync()

    val tableSchemaRegistry = SchemaRegistry()
    val metadataSchemaRegistry = SchemaRegistry()

    tableSchemaRegistry.read(tableToml)
    metadataSchemaRegistry.read(metaToml)

    val metadataSchema = metadataSchemaRegistry["#$tableId"]!!
    val tableSchema = tableSchemaRegistry[tableId]!!

    val table = shardRegistry.table(tableId, metadataSchema, tableSchema)

    val builder = SelectorBuilder(metadataSchema)
    val period by builder.dateTimeRange


    val shards = table.find {
        all {
            +period contains Instant.ofEpochMilli(17260362050000)
        }
    }
    // 여기서 대기해야되나?

    println("shard size : ${shards.size}")

    // shard를 찾았다고 해서 샤드를 내려받지는 않는다.


    exitProcess(0)
}