package io.tintoll.indra

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import nethru.indra.querier.Querier
import nethru.indra.query.SelectorBuilder
import nethru.indra.schema.registry.SchemaRegistry
import nethru.indra.shard.ShardRegistry
import java.time.Instant
import java.util.concurrent.Executors
import kotlin.system.exitProcess


fun main() {
    val shardRegistry = ShardRegistry.open(tenantId, registryConfig)

    CoroutineScope(Dispatchers.IO).launch {
        shardRegistry.sync()
    }

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

    println("shard size : ${shards.size}")
    // shard를 찾았다고 해서 샤드를 내려받지는 않는다. registry만 내려받음.

    if (shards.isNotEmpty()) {
        val querierFactory = Querier.Factory(tenantId, Querier.Config(), Executors.newFixedThreadPool(1))
        val querier = querierFactory.wrap(shards) // 이 시점에 로컬에 내려받는다.

        // user stitching 할 데이터 검색
        val tableBuilder = SelectorBuilder(tableSchema)
        val `@userid` by tableBuilder.token
        val matchSet = querier.find {
            all {
                +`@userid` has "U1"
            }
        }

        val datareads = querier.fetch(matchSet)
        println("datareads size : ${datareads.count()}")
        exitProcess(0)
    }


}