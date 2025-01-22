package io.tintoll.indra

import nethru.indra.indexer.Context
import nethru.indra.schema.registry.SchemaRegistry
import nethru.indra.shard.ShardRegistry
import java.util.concurrent.Executors

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
}
