package io.study.coroutine.user

import io.micronaut.http.annotation.Controller

@Controller("/users")
class UserAPI(private val userService: UserService) {

}
