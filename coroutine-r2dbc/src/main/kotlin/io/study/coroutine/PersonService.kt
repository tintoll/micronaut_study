package io.study.coroutine

import jakarta.inject.Singleton
import jakarta.transaction.Transactional
import jakarta.validation.constraints.NotNull
import kotlinx.coroutines.flow.toList

@Singleton
class PersonService(private val personRepository: PersonRepository) {

    suspend fun findAll(): List<Person> {
        return personRepository.findAll().toList()
    }

    suspend fun findById(id: Long): Person? {
        return personRepository.findById(id)
    }

//    @Transactional(Transactional.TxType.MANDATORY)
    suspend fun save(@NotNull person: Person): Person {
        return personRepository.save(person)
    }

    suspend fun deleteById(id: Long) {
        personRepository.deleteById(id)
    }
}