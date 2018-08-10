package com.example.dai_pc.android_test.service

import rx.Observable
import java.util.*

interface AccountStellarService {
    fun createAccount(): Observable<String>
    fun singin(priveKey :String) : Observable<String>
    fun importAccount()
    fun exportAccount()
}