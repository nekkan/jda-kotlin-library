package com.github.nekkan.jdakotlinlibrary.collections.utils

import com.github.nekkan.jdakotlinlibrary.extensions.thread
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class AnyFilter<T>(var content: T, var boolean: Boolean) {

    fun filter(boolean: Boolean) = if(this.boolean) AnyFilter(content, boolean) else null
    fun filter(boolean: (T) -> Boolean) = if(this.boolean) AnyFilter(content, boolean(content)) else null

    fun <A> map(content: A) = if(boolean) AnyMap(content) else null
    fun <A> map(content: (T) -> A) = if(boolean) AnyMap(content(this.content)) else null

    fun subscribe(async: Boolean = false, execute: (T) -> Unit) {
        if(boolean) {
            if (!async) execute(content)
            else {
                val thread = thread()
                runBlocking(thread) { async(thread) { }.await() }
            }
        }
    }

}