package com.example.dai_pc.android_test.entity

/**
 * class keep data need load end description
 */
data class Resource<T> (val t:T?,val messError:String?,val status: Status){
   public enum class Status {
       LOADING,
       SUCCESS,
       ERROR
    }
}

fun <T> success(t:T) : Resource<T> = Resource(t,null,Resource.Status.SUCCESS)
fun <T> loading() : Resource<T> = Resource(null,null,Resource.Status.LOADING)
fun <T> error(messError: String?) :Resource<T> = Resource(null,messError,Resource.Status.ERROR)