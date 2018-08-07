package com.example.dai_pc.android_test.di

import javax.inject.Scope


@Scope
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.TYPE,AnnotationTarget.FUNCTION)
annotation class FragmentScope