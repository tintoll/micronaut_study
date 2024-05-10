package io.study.management

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get

@Controller
class TestController {

    @Get("/test")
    fun test(): String {
        return "Hello, World!"
    }
}