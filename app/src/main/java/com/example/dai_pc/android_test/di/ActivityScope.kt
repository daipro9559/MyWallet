package com.example.dai_pc.android_test.di

import java.lang.annotation.ElementType
import java.lang.annotation.RetentionPolicy
import javax.inject.Scope

/**
 * Created by dai_pc on 6/15/2018.
 */

@Scope
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.TYPE,AnnotationTarget.FUNCTION)
annotation class ActivityScope