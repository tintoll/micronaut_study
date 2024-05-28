package io.study.coroutine.test

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import kotlinx.coroutines.flow.toList

@Controller("/test")
class TestAPI(
    private val countryRepository: CountryRepository,
    private val countryRegionRepository: CountryRegionRepository
) {

    @Get
    suspend fun test(): List<Country> {
        return countryRepository.findAll().toList()
    }

    @Get("/region")
    suspend fun testRegion(): List<Country> {
        return countryRepository.findAllByCountryRegionId(2L)
    }
}