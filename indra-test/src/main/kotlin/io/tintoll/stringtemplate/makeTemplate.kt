package io.tintoll.stringtemplate

import org.stringtemplate.v4.ST


fun main() {
    // data class 생성 template
    val template = ST(
        """
            package <packageName>
            
            data class <className>(<fields; separator=", ">) {
                fun printDetail() {
                    println(this.toString())
                }
            }
        """
    )

    // 변수값 설정
    template.add("packageName", "io.tintoll.stringtemplate")
    template.add("className", "User")
    template.add("fields", listOf("val id: Int", "val name: String", "val age: Int"))

    // 코드 생성 및 출력
    val generatedCode = template.render()
    println(generatedCode)

}