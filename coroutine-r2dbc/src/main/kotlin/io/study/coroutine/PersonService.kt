package io.study.coroutine

import jakarta.inject.Singleton
import jakarta.transaction.Transactional
import kotlinx.coroutines.flow.toList

@Singleton
open class PersonService(private val personRepository: PersonRepository) {

    suspend fun findAll(): List<Person> {
        return personRepository.findAll().toList()
    }

    suspend fun findById(id: Long): Person? {
        return personRepository.findById(id)
    }

    @Transactional
    open suspend fun save(person: Person): Person {
        return personRepository.save(person)
    }

    suspend fun deleteById(id: Long) {
        personRepository.deleteById(id)
    }
}