package io.study.coroutine

import io.micronaut.context.annotation.Context
import io.micronaut.context.annotation.Factory
import io.r2dbc.pool.ConnectionPool
import io.r2dbc.pool.ConnectionPoolConfiguration
import io.r2dbc.spi.ConnectionFactories
import io.r2dbc.spi.ConnectionFactory
import io.r2dbc.spi.ConnectionFactoryOptions
import io.r2dbc.spi.Option


@Factory
class R2dbcConfigFactory {

    @Context
    fun connectionFactory(options: ConnectionFactoryOptions): ConnectionFactory {
        val connectionFactory = ConnectionFactories.get(options)
        val configuration = ConnectionPoolConfiguration.builder(connectionFactory)
            .initialSize(options.getValue(Option.valueOf<Int>("initialSize"))!!.toString().toInt())
            .maxSize(options.getValue(Option.valueOf<Int>("maxSize"))!!.toString().toInt())
            .validationQuery(options.getValue(Option.valueOf<String>("validationQuery"))!!.toString())
            .build()
        return ConnectionPool(configuration)
    }

}