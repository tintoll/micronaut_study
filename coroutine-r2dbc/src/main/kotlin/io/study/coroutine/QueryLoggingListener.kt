package io.study.coroutine

import io.github.oshai.kotlinlogging.KotlinLogging
import io.r2dbc.proxy.core.QueryExecutionInfo
import io.r2dbc.proxy.listener.ProxyExecutionListener
import io.r2dbc.proxy.support.QueryExecutionInfoFormatter


class QueryLoggingListener : ProxyExecutionListener {
    private val logger = KotlinLogging.logger { }

    override fun eachQueryResult(execInfo: QueryExecutionInfo) {
        val formatter =
            QueryExecutionInfoFormatter().addConsumer { info: QueryExecutionInfo, sb: java.lang.StringBuilder ->
                sb.append("Result Row : ")
                sb.append(info.currentMappedResult)
            }
        logger.info(formatter.format(execInfo))
    }

    override fun afterQuery(execInfo: QueryExecutionInfo) {
        val formatter = QueryExecutionInfoFormatter().addConsumer { info: QueryExecutionInfo, sb: StringBuilder ->
            sb.append("ConnectionId: ")
            sb.append(info.connectionInfo.connectionId)
        }
            .newLine()
            .showQuery()
            .newLine()
            .showBindings()
            .newLine()
            .addConsumer { info: QueryExecutionInfo, sb: StringBuilder ->
                sb.append("Result Count : ")
                sb.append(info.currentResultCount)
            }.newLine()
            .addConsumer { info: QueryExecutionInfo, sb: StringBuilder ->
                val executionTime = info.executeDuration.toMillis()
                sb.append("Execute Time : ")
                sb.append(executionTime)
            }
        logger.info(formatter.format(execInfo))
    }
}