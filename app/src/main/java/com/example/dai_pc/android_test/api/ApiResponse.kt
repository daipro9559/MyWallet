package com.example.dai_pc.android_test.api

class ApiResponse<T> {
    var data: T?
    val code: Int
    val errorMess: String?

    //    response success
    constructor(data: T?, code: Int) {
        this.data = data
        this.code = code
        this.errorMess = null
    }

    //response fail
    constructor(code: Int,error:String?){
        this.data = null
        this.code = code
        this.errorMess = error
    }
}