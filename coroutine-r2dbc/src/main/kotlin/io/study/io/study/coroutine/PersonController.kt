package io.study.io.study.coroutine

import io.micronaut.http.annotation.*

@Controller("/persons")
class PersonController(private val personService: PersonService) {

    @Get("/")
    suspend fun getAll(): List<Person> {
        return personService.findAll()
    }

    @Get("/{id}")
    suspend fun getById(@PathVariable id: Long): Person? {
        return personService.findById(id)
    }

    @Post("/")
    suspend fun save(@Body person: Person): Person {
        return personService.save(person)
    }

    @Delete("/{id}")
    suspend fun deleteById(@PathVariable id: Long) {
        personService.deleteById(id)
    }
}