package io.study.io.study.coroutine

import jakarta.inject.Singleton
import kotlinx.coroutines.flow.toList

@Singleton
class PersonService(private val personRepository: PersonRepository) {

    suspend fun findAll(): List<Person> {
        return personRepository.findAll().toList()
    }

    suspend fun findById(id: Long): Person? {
        return personRepository.findById(id)
    }

    suspend fun save(person: Person): Person {
        return personRepository.save(person)
    }

    suspend fun deleteById(id: Long) {
        personRepository.deleteById(id)
    }
}