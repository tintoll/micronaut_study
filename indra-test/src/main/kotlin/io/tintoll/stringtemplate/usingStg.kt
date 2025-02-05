package io.tintoll.stringtemplate

import org.stringtemplate.v4.ST
import org.stringtemplate.v4.STGroupFile

fun main() {
    // 1. STGroupFile을 통해 .stg 파일 로드
    val group = STGroupFile("UserTemplates.stg")

    // 2. 특정 템플릿 가져오기
    val template: ST = group.getInstanceOf("dataClass")

    // 3. 템플릿 변수 바인딩
    template.add("packageName", "com.example")
    template.add("className", "User")
    template.add("fields", listOf("val id: Int", "val name: String", "val age: Int"))

    // 4. 템플릿 렌더링
    val generatedCode = template.render()

    // 5. 결과 출력
    println(generatedCode)
}