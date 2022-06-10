package com.kukeduo.kotlindemo

class Test {

    fun abc(){}
    fun getlength2(s: String){}
    fun foo() {
        listOf(1, 2, 3, 4, 5).forEach {
            if (it == 3) return@forEach // 非局部直接返回到 foo() 的调用者
            print(it)
        }
        println("this point is unreachable")
    }
}
val user_name = "123123"
val pass_word = "asdasd"
fun main() {

    println(getlength("eqwe"))
    Test().foo()


    val len = "asdasd".count()
    println(len)
    val len1 = "asdasd".count {
        it == 'a'
    }
    println(len1)

    val method :() -> String

    method = {
        "open"
    }

    println(method())

    val method2 = {v1:String, v2: Int, v3: Char->
        "Asd"
    }
    println(method2("qwe", 123, 'a'))

    val method3 = {
        123.5
    }
    println(method3())

    loginApi("123123", "asdasd", Response = {msg, code ->
        println("msg:$msg, code:$code")
    })
    loginApi("123123", "asdasd"){msg, code ->
        println("msg:$msg, code:$code")
    }
    loginApi("123123", "asdsad", ::loginResponse)

    val show = show("查收空当接龙")
    println(show("asdasd", 3))

    loginApi1("123123", "asdasd",{s, i ->
        println("登录成功")
    }){s, i ->
        println("登录")
    }

    val list = ArrayList<DemoBean?>()
    list.add(null)
    println(list[0]?.name)

}

data class DemoBean(var name:String, var age: Int){


}

fun loginResponse(msg: String, code: Int){

    println("函数引用msg:$msg, code:$code")
}


fun loginApi(userName: String, passWord: String, Response:(String, Int)-> Unit){
    if (userName == "" || passWord == ""){
        return
    }
    if (loginService(userName, passWord)){
        Response("success", 100)
    }else{
        Response("fail", 500)
    }
}

fun show(info:String):(String, Int)->Int{
    val a = 2 + 2
    println("打印了$info")
    return {s, i ->
        println("字符串$s, 数字$i")
        a + 3
    }
}


fun loginService(userName: String, passWord: String): Boolean{
    if (userName == user_name && passWord == pass_word){
        return true
    }
    return false
}

fun getlength(s: String) = s.length

fun loginApi1(userName: String, passWord: String, Success:(String, Int)-> Unit, Fail:(String, Int) -> Unit){
    if (userName == "" || passWord == ""){
        return
    }
    if (loginService(userName, passWord)){
        Success("success", 100)
    }else{
        Fail("fail", 500)
    }
}