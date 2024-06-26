package io.study.coroutine.user

import io.micronaut.context.annotation.Parameter
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get

@Controller("/users")
class UserAPI(private val userService: UserService) {

    @Get
    suspend fun getUsersByOrgId(@Parameter orgId: Long) = userService.getUsersByOrgId(orgId)

    @Get("/ids")
    suspend fun getUsersByIds(@Parameter ids: List<Long>) = userService.getUsersByIds(ids)

    // 수정

    // 내가 나자신을 수정
    // 관리자가 수정도 있으려나?

    // 생성 하는 부분은 없네
}
