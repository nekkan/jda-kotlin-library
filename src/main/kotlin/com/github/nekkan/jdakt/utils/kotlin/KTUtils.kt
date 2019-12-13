@file:JvmName("KTUtils")
package com.github.nekkan.jdakt.utils.kotlin

import kotlinx.coroutines.*

fun List<String>.containsIgnoreCase(text: String): Boolean {
    val exists = filter { it.equals(text, true) }
    return exists.isNotEmpty()
}

fun repeatingTask(amount: Int, delayInMillis: Long, block: CoroutineScope.() -> Unit) = runBlocking {
    launch {
        repeat(amount) {
            block(this)
            delay(delayInMillis)
        }
    }
}

fun sleep(millis: Long) = GlobalScope.launch { delay(millis) }