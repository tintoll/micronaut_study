package io.tintoll.indra

import nethru.indra.indexer.Context
import nethru.indra.range.InstantRange
import nethru.indra.schema.registry.SchemaRegistry
import nethru.indra.shard.ShardRegistry
import java.time.Instant
import java.util.concurrent.Executors
import kotlin.system.exitProcess

fun main() {
    val shardRegistry = ShardRegistry.open(tenantId, registryConfig)

    val tableSchemaRegistry = SchemaRegistry()
    val metadataSchemaRegistry = SchemaRegistry()
    tableSchemaRegistry.read(tableToml)
    metadataSchemaRegistry.read(metaToml)

    val metadataSchema = metadataSchemaRegistry["#$tableId"]!!
    val tableSchema = tableSchemaRegistry[tableId]!!

    val table = shardRegistry.table(tableId, metadataSchema, tableSchema)
    val context = Context.Factory(tenantId, Context.Config(), Executors.newFixedThreadPool(1)).newContext()

    val shard = context.newShard(table)
    val indexer = context.indexer(shard)


    eventLogs1().forEach {
        val rowBuffer = indexer.buffer()
        it.forEach { (k, v) ->
            when (v) {
                is String -> rowBuffer.token(k, v)
                is Int -> rowBuffer.i64(k, v.toLong())
                is Long -> rowBuffer.i64(k, v)
            }
        }
        indexer.add(rowBuffer)
    }

    // 메타데이터
    val mutableMetadata = shard.metadata.toMutable()
    val start = Instant.ofEpochMilli(1726034470000)
    val end = Instant.ofEpochMilli(17260362060000)
    mutableMetadata.dateTimeRange("period", InstantRange(start, end))
    indexer.bind(mutableMetadata)

    val newShard = indexer.shardRef()
    context.commit()
    indexer.close()

    checkDocs(registryConfig.shardPath, newShard)

    println("insert - END")
    exitProcess(0)
}
