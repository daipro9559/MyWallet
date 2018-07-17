package com.example.dai_pc.android_test.di

import java.lang.annotation.RetentionPolicy
import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.TYPE,AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
annotation class AppScope