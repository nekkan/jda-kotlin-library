package com.github.nekkan.jdakotlinlibrary.collections.utils

import com.github.nekkan.jdakotlinlibrary.extensions.thread
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class AnyMap<T>(var content: T) {

    fun filter(boolean: Boolean) = if(boolean) AnyFilter(content, boolean) else null
    fun filter(boolean: (T) -> Boolean) = if(boolean(content)) AnyFilter(content, boolean(content)) else null

    fun map(a: () -> T) = AnyMap(a())

    fun subscribe(async: Boolean = false, execute: (T) -> Unit) {
        if(!async) execute(content)
        else {
            val thread = thread()
            runBlocking(thread) { async(thread) { }.await() }
        }
    }

}